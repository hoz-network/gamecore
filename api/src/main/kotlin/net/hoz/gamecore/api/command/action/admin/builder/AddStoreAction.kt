package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import mu.KotlinLogging
import net.hoz.gamecore.api.command.GContext.COMMAND_GAME_BUILDER_FIELD
import net.hoz.gamecore.api.command.GContext.COMMAND_STORE_HOLDER
import net.hoz.gamecore.api.command.GContext.COMMAND_STORE_NAME
import net.hoz.gamecore.api.command.GContext.COMMAND_TEAM_BUILDER_FIELD
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.store.StoreHolderArgument
import net.hoz.gamecore.api.command.argument.team.TeamBuilderArgument
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.command.getOrNull
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper
import java.util.*

private val log = KotlinLogging.logger {}

class AddStoreAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {
    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(
                builder.literal("store", ArgumentDescription.of("Adds new team to the fame."))
                    .argument(
                        StringArgument.newBuilder<CommandSenderWrapper>(COMMAND_STORE_NAME.name)
                            .withParser(StoreNameParser())
                            .asRequired()
                            .build()
                    )
                    .argument(StoreHolderArgument.of(COMMAND_STORE_HOLDER, gameManager))
                    .argument(TeamBuilderArgument.of(COMMAND_TEAM_BUILDER_FIELD))
                    .builderHandler { sender, gameBuilder, context ->
                        val storeName = context[COMMAND_STORE_NAME]
                        val storeHolder = context[COMMAND_STORE_HOLDER]
                        val team = context.getOrNull(COMMAND_TEAM_BUILDER_FIELD)

                        log.debug { "Trying to create new team builder.." }
                        if (gameBuilder.stores.has(storeName)) {
                            Message.of(CommandLang.ERROR_BUILDER_STORE_ALREADY_EXISTS)
                                .placeholder("name", storeName)
                                .resolvePrefix()
                                .send(sender)
                            return@builderHandler
                        }

                        val store = gameBuilder.stores
                            .builder(storeName) {
                                this.holder = storeHolder
                                this.team = team?.name
                            }

                        log.debug { "Created new store builder: $store" }

                        //TODO: lang
                        Message.of(CommandLang.SUCCESS_BUILDER_TEAM_ADDED)
                            .placeholder("name", store.name)
                            .resolvePrefix()
                            .send(sender)
                    }
            )
    }

    private class StoreNameParser : ArgumentParser<CommandSenderWrapper, String> {
        override fun parse(
            commandContext: CommandContext<CommandSenderWrapper>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<String> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(
                    NoInputProvidedException(
                        StringArgument.StringParser::class.java,
                        commandContext
                    )
                )

            val builder = commandContext[COMMAND_GAME_BUILDER_FIELD]
            if (builder.stores.has(input)) {
                inputQueue.remove()
                return ArgumentParseResult.failure(UnsupportedOperationException("Store already exists."))
            }
            return ArgumentParseResult.success(input)
        }
    }
}
