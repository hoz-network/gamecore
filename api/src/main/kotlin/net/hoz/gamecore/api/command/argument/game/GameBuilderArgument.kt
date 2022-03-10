package net.hoz.gamecore.api.command.argument.game

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.captions.Caption
import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.exceptions.parsing.ParserException
import cloud.commandframework.keys.CloudKey
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.api.util.findMatchingOrAvailable
import java.util.*
import java.util.function.BiFunction

class GameBuilderArgument<C>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?,
    gameManager: GameManager
) : CommandArgument<C, GameBuilder>(
    required,
    name,
    GameFrameParser(gameManager),
    defaultValue,
    GameBuilder::class.java,
    suggestionsProvider
) {
    companion object {
        fun <C : Any, T : Any> newBuilder(
            key: CloudKey<T>,
            gameManager: GameManager
        ): Builder<C> = Builder(key.name, gameManager)

        fun <C : Any, T : Any> of(key: CloudKey<T>, gameManager: GameManager): CommandArgument<C, GameBuilder> =
            newBuilder<C, T>(key, gameManager).asRequired().build()

        fun <C : Any, T : Any> optional(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument<C, GameBuilder> = newBuilder<C, T>(key, gameManager).asOptional().build()
    }

    class Builder<C>(
        name: String,
        private val gameManager: GameManager
    ) : CommandArgument.Builder<C, GameBuilder>(GameBuilder::class.java, name) {
        override fun build(): CommandArgument<C, GameBuilder> = GameBuilderArgument(
            this.isRequired,
            this.name,
            this.defaultValue,
            this.suggestionsProvider,
            gameManager
        )
    }

    class GameFrameParser<C>(private val gameManager: GameManager) : ArgumentParser<C, GameBuilder> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<GameBuilder> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(GameFrameParser::class.java, context))

            val available = gameManager.builders().all()
            if (available.isEmpty()) {
                return ArgumentParseResult.failure(
                    GameBuilderParseException(context, Caption.of("No builder is registered!"))
                )
            }

            val toReturn = available.firstOrNull { it.name() == input }
                ?: return ArgumentParseResult.failure(
                    GameBuilderParseException(context, Caption.of("Builder not found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(toReturn)
        }

        override fun suggestions(context: CommandContext<C>, input: String): MutableList<String> {
            val available = gameManager.builders()
                .all()
                .map { it.name() }

            return findMatchingOrAvailable(input, available, "Create builder first!")
        }
    }

    class GameBuilderParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(GameBuilderArgument::class.java, context, errorCaption, *captionVariables)
}