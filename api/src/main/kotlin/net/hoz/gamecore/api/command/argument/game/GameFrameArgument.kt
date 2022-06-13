package net.hoz.gamecore.api.command.argument.game

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.captions.Caption
import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.exceptions.parsing.ParserException
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.api.util.GUtil
import java.util.*
import java.util.function.BiFunction

class GameFrameArgument<C : Any>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?,
    gameManager: GameManager
) : CommandArgument<C, GameFrame>(
    required,
    name,
    GameFrameParser(gameManager),
    defaultValue,
    GameFrame::class.java,
    suggestionsProvider
) {
    companion object {
        fun <C : Any> newBuilder(name: String, gameManager: GameManager): CommandArgument.Builder<C, GameFrame> = Builder(name, gameManager)

        fun <C : Any> of(name: String, gameManager: GameManager): CommandArgument<C, GameFrame> =
            newBuilder<C>(name, gameManager).asRequired().build()

        fun <C : Any> optional(name: String, gameManager: GameManager): CommandArgument<C, GameFrame> =
            newBuilder<C>(name, gameManager).asOptional().build()
    }

    class Builder<C : Any>(
        name: String,
        private val gameManager: GameManager
    ) : CommandArgument.Builder<C, GameFrame>(GameFrame::class.java, name) {
        override fun build(): CommandArgument<C, GameFrame> = GameFrameArgument(
            this.isRequired,
            this.name,
            this.defaultValue,
            this.suggestionsProvider,
            gameManager
        )
    }

    class GameFrameParser<C>(private val gameManager: GameManager) : ArgumentParser<C, GameFrame> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<GameFrame> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(GameFrameParser::class.java, context))

            val available = gameManager.frames().all()
            if (available.isEmpty()) {
                return ArgumentParseResult.failure(
                    GameFrameParseException(context, Caption.of("No Frame is registered!"))
                )
            }

            val toReturn = available.firstOrNull { it.name() == input }
                ?: return ArgumentParseResult.failure(
                    GameFrameParseException(context, Caption.of("Frame not found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(toReturn)
        }

        override fun suggestions(commandContext: CommandContext<C>, input: String): MutableList<String> {
            val available = gameManager.frames()
                .all()
                .map { it.name() }

            return GUtil.findMatchingOrAvailable(input, available, "No frame found!")
        }
    }

    class GameFrameParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(GameFrameArgument::class.java, context, errorCaption, *captionVariables)
}