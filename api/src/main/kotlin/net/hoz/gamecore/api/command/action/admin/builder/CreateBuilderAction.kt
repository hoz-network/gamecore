package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.EnumArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.kotlin.extension.senderType
import mu.KotlinLogging
import net.hoz.api.data.GameType
import net.hoz.gamecore.api.command.GContext.COMMAND_ARENA_NAME_FIELD
import net.hoz.gamecore.api.command.GContext.COMMAND_CONFIG_FIELD
import net.hoz.gamecore.api.command.GContext.COMMAND_GAME_TYPE
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.lang.CommandLang
import net.hoz.gamecore.api.util.GUtil
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper

private val log = KotlinLogging.logger {}

class CreateBuilderAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {
    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        //TODO: description lang
        commandManager
            .command(builder.literal("create", ArgumentDescription.of("Creates new builder"))
                .senderType(PlayerWrapper::class)
                .argument(
                    StringArgument.newBuilder<CommandSenderWrapper>(COMMAND_CONFIG_FIELD.name)
                        .withSuggestionsProvider { _, input -> suggestConfig(input) }
                        .single()
                        .asRequired()
                        .build()
                )
                .argument(StringArgument.of(COMMAND_ARENA_NAME_FIELD.name))
                .argument(EnumArgument.of(GameType::class.java, ""))
                .handler {
                    val sender = it.sender
                    val arenaName = it[COMMAND_ARENA_NAME_FIELD]
                    val configName = it[COMMAND_CONFIG_FIELD]
                    val gameType = it[COMMAND_GAME_TYPE]

                    if (gameManager.frames.has(arenaName)
                        || gameManager.builders.has(arenaName)
                    ) {
                        Message.of(CommandLang.ERROR_BUILDER_ALREADY_EXISTS)
                            .resolvePrefix()
                            .send(sender)
                        return@handler
                    }

                    val config = gameManager.backend.oneConfig(configName)
                    if (config.isFail) {
                        Message.of(CommandLang.ERROR_BUILDER_FAILED_CREATING)
                            .placeholder("error", "Invalid config identifier $configName")
                            .placeholder("name", arenaName)
                            .resolvePrefix()
                            .send(sender)
                        return@handler
                    }


                    val gameBuilder = gameManager.builders.create(arenaName, config.data(), gameType)
                    if (gameBuilder.isFail) {
                        Message.of(CommandLang.ERROR_BUILDER_FAILED_CREATING)
                            .placeholder("error", gameBuilder.message())
                            .placeholder("name", arenaName)
                            .resolvePrefix()
                            .send(sender)
                        return@handler
                    }

                    Message.of(CommandLang.SUCCESS_BUILDER_CREATED)
                        .placeholder("name", arenaName)
                        .resolvePrefix()
                        .send(sender)
                }
                .build()
            )
    }

    private fun suggestConfig(input: String): List<String> {
        if (input.isEmpty()) return listOf()
        val available = gameManager.backend
            .allConfigs()
            .map { it.name }

        return GUtil.findMatchingOrAvailable(input, available)
    }
}