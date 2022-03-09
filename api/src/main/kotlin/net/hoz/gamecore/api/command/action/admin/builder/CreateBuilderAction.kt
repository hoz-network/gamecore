package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.context.CommandContext
import cloud.commandframework.kotlin.extension.senderType
import net.hoz.gamecore.api.command.COMMAND_CONFIG_FIELD
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.findArenaName
import net.hoz.gamecore.api.command.getOrNull
import net.hoz.gamecore.api.util.GLangKeys
import net.hoz.gamecore.api.util.GUtil
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper

class CreateBuilderAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        //TODO: description lang
        commandManager.command(
            builder.literal("create", ArgumentDescription.of("Creates new builder"))
                .senderType(PlayerWrapper::class)
                .argument(
                    StringArgument.newBuilder<CommandSenderWrapper>(COMMAND_CONFIG_FIELD.name)
                        .withSuggestionsProvider { _, input -> suggestConfig(input) }
                        .single()
                        .asRequired()
                        .build()
                )
                .handler { doHandle(it) }
                .build()
        )
    }

    private fun suggestConfig(input: String): List<String> {
        if (input.isEmpty()) return listOf()
        val available = gameManager.backend()
            .allConfigs()
            .map { it.name }

        return GUtil.findMatchingOrAvailable(input, available)
    }

    private fun doHandle(context: CommandContext<CommandSenderWrapper>) {
        val sender = context.sender
        val arenaName = context.findArenaName()
        val configName = context.getOrNull(COMMAND_CONFIG_FIELD)

        if (gameManager.frames().has(arenaName) || gameManager.builders().has(arenaName)) {
            Message.of(GLangKeys.CORE_COMMANDS_ERROR_BUILDER_ALREADY_EXISTS)
                .resolvePrefix()
                .send(sender)
            return
        }

        if (configName == null || gameManager.backend().oneConfig(configName).isFail) {
            Message.of(GLangKeys.CORE_COMMANDS_ERROR_BUILDER_FAILED_CREATING)
                .placeholder("error", "Invalid config identifier $configName")
                .placeholder("name", arenaName)
                .resolvePrefix()
                .send(sender)
            return
        }

        val gameBuilder = gameManager.builders().create(arenaName, configName)
        if (gameBuilder.isFail) {
            Message.of(GLangKeys.CORE_COMMANDS_ERROR_BUILDER_FAILED_CREATING)
                .placeholder("error", gameBuilder.message())
                .placeholder("name", arenaName)
                .resolvePrefix()
                .send(sender)
            return
        }

        Message.of(GLangKeys.CORE_COMMANDS_SUCCESS_BUILDER_CREATED)
            .placeholder("name", arenaName)
            .resolvePrefix()
            .send(sender)
    }
}