package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.context.CommandContext
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.action.AbstractSubAction
import net.hoz.gamecore.api.command.gameBuilder
import net.hoz.gamecore.api.game.frame.builder.GameBuilder

abstract class AbstractBuilderSubAction(
    parentAction: AbstractAction
) : AbstractSubAction(parentAction) {

    protected fun builderSuggestion(
        context: CommandContext<Any>,
        suggestionBuilder: GameBuilder.() -> List<String>
    ): List<String> {
        val builder = context.gameBuilder(gameManager)
        if (builder == null) {
            //TODO: language
            return listOf("Builder not found!")
        }

        return suggestionBuilder.invoke(builder)
    }
}