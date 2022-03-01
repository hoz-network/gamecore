package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.context.CommandContext
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.action.AbstractSubAction
import net.hoz.gamecore.api.command.findArenaName
import net.hoz.gamecore.api.command.findGameBuilder
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.util.GLangKeys
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper

abstract class AbstractBuilderSubAction(
    parentAction: AbstractAction
) : AbstractSubAction(parentAction) {

    protected fun builderSuggestion(
        context: CommandContext<CommandSenderWrapper>,
        suggestionBuilder: GameBuilder.() -> List<String>
    ): List<String> {
        val builder = context.findGameBuilder(gameManager)
        val sender = context.sender
        if (builder == null) {
            //TODO: language
            return listOf("Builder not found!")
        }

        return suggestionBuilder.invoke(builder)
    }

    protected fun handle(context: CommandContext<CommandSenderWrapper>, handler: (PlayerWrapper, GameBuilder) -> Unit) {
        val sender = context.sender.unwrap(PlayerWrapper::class)
        val arenaName = context.findArenaName()
        val builder = gameManager.builders().find(arenaName)
        if (builder == null) {
            Message.of(GLangKeys.CORE_COMMANDS_ERROR_BUILDER_NOT_FOUND)
                .placeholder("name", arenaName)
                .resolvePrefix()
                .send(sender)
            return
        }

        handler.invoke(sender, builder)
    }
}