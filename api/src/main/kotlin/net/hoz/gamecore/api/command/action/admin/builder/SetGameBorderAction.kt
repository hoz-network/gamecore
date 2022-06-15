package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.EnumArgument
import mu.KotlinLogging
import net.hoz.api.data.game.ProtoWorldData.BorderType
import net.hoz.api.data.game.ProtoWorldData.WorldType
import net.hoz.gamecore.api.command.GContext.COMMAND_BORDER_TYPE
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.event.builder.world.GameWorldBorderSetEvent
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.kotlin.fire
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper

private val log = KotlinLogging.logger {}

class SetGameBorderAction(
    parentAction: AbstractAction,
    private val worldType: WorldType
) : AbstractBuilderSubAction(parentAction) {

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("border", ArgumentDescription.of("Sets a border for " + worldType.name))
                .argument(
                    EnumArgument.newBuilder<CommandSenderWrapper?, BorderType?>(
                        BorderType::class.java,
                        COMMAND_BORDER_TYPE.name
                    )
                        .withSuggestionsProvider { _, input -> suggestBorder(input) }
                        .asRequired()
                        .build()
                )
                .builderHandler { sender, gameBuilder, context ->
                    val location = sender.location
                    val borderType = context[COMMAND_BORDER_TYPE]
                    log.debug { "Border[${worldType.name}/${borderType.name}] will be at location: $location" }

                    val world = when (worldType) {
                        WorldType.LOBBY -> gameBuilder.world.lobbyWorld
                        WorldType.ARENA -> gameBuilder.world.arenaWorld
                        else -> throw UnsupportedOperationException("World not found.")
                    }

                    when (borderType) {
                        BorderType.FIRST -> world.border1 = location
                        BorderType.SECOND -> world.border2 = location
                        else -> log.warn { "Cannot set UNRECOGNIZED border type." }
                    }

                    log.debug { "Created new ${worldType.name} world builder: $world" }

                    GameWorldBorderSetEvent(world, gameBuilder, location, borderType).fire()

                    //TODO: lang
                    Message.of(CommandLang.SUCCESS_BUILDER_SPAWNER_ADDED)
                        .resolvePrefix()
                        .send(sender)
                }
            )
    }

    private fun suggestBorder(input: String): MutableList<String> {
        val available = mutableListOf(BorderType.FIRST.name, BorderType.SECOND.name)
        val first = available.firstOrNull { it.startsWith(input) }
            ?: return available

        return mutableListOf(first)
    }

}
