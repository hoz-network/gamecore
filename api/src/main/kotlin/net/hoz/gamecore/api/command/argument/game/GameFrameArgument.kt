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
import java.util.*
import java.util.function.BiFunction

class GameFrameArgument<C>(
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
    class Builder<C>(
        name: String,
        private val gameManager: GameManager
    ) : CommandArgument.Builder<C, GameFrame>(GameFrame::class.java, name) {

        override fun build(): CommandArgument<C, GameFrame> {
            return GameFrameArgument(
                this.isRequired,
                this.name,
                this.defaultValue,
                this.suggestionsProvider,
                gameManager
            )
        }
    }

    class GameFrameParser<C>(private val gameManager: GameManager) : ArgumentParser<C, GameFrame> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<GameFrame> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(GameFrameParser::class.java, context))

            val availableFrames = gameManager.frames().all()
            if (availableFrames.isEmpty()) {
                return ArgumentParseResult.failure(
                    GameFrameParseException(context, Caption.of("No types are available in backend!"))
                )
            }

            val toReturn = availableFrames.firstOrNull { it.name() == input }
                ?: return ArgumentParseResult.failure(
                    GameFrameParseException(context, Caption.of("Frame not found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(toReturn)
        }

        override fun suggestions(commandContext: CommandContext<C>, input: String): MutableList<String> {
            return gameManager.frames()
                .all()
                .map { it.name() }
                .ifEmpty { mutableListOf("No game found.") }
                .toMutableList()
        }
    }

    class GameFrameParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(GameFrameArgument::class.java, context, errorCaption, *captionVariables)

    companion object {
        fun <C> newBuilder(name: String, gameManager: GameManager): CommandArgument.Builder<C, GameFrame> {
            return Builder(name, gameManager)
        }

        fun <C : Any> of(name: String, gameManager: GameManager): CommandArgument<C, GameFrame> {
            return newBuilder<C>(name, gameManager).asRequired().build()
        }

        fun <C : Any> optional(name: String, gameManager: GameManager): CommandArgument<C, GameFrame> {
            return newBuilder<C>(name, gameManager).asOptional().build()
        }
    }
}