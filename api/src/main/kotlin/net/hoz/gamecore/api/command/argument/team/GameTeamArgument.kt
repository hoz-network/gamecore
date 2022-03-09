package net.hoz.gamecore.api.command.argument.team

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.captions.Caption
import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.exceptions.parsing.ParserException
import cloud.commandframework.keys.CloudKey
import net.hoz.gamecore.api.command.argument.game.GameFrameArgument
import net.hoz.gamecore.api.command.findGameBuilder
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.api.util.GUtil
import java.util.*
import java.util.function.BiFunction

class GameTeamArgument<C>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?,
    gameManager: GameManager
) : CommandArgument<C, GameTeamBuilder>(
    required,
    name,
    GameFrameParser(gameManager),
    defaultValue,
    GameTeamBuilder::class.java,
    suggestionsProvider
) {
    class Builder<C>(
        name: String,
        private val gameManager: GameManager
    ) : CommandArgument.Builder<C, GameTeamBuilder>(GameTeamBuilder::class.java, name) {

        override fun build(): CommandArgument<C, GameTeamBuilder> {
            return GameTeamArgument(
                this.isRequired,
                this.name,
                this.defaultValue,
                this.suggestionsProvider,
                gameManager
            )
        }
    }

    class GameFrameParser<C>(private val gameManager: GameManager) : ArgumentParser<C, GameTeamBuilder> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<GameTeamBuilder> {
            val builder = context.findGameBuilder(gameManager)
                ?: return ArgumentParseResult.failure(
                    GameFrameArgument.GameFrameParseException(
                        context,
                        Caption.of("Builder not found!")
                    )
                )
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(GameFrameParser::class.java, context))

            val available = builder.teams().all()
            if (available.isEmpty()) {
                return ArgumentParseResult.failure(
                    GameTeamParseException(context, Caption.of("No Frame is registered!"))
                )
            }

            val toReturn = available[input]
                ?: return ArgumentParseResult.failure(
                    GameTeamParseException(context, Caption.of("Frame not found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(toReturn)
        }

        override fun suggestions(context: CommandContext<C>, input: String): MutableList<String> {
            val builder = context.findGameBuilder(gameManager)
                ?: return mutableListOf("Builder not found!")

            val available = builder.teams()
                .all()
                .keys
                .toList()

            return GUtil.findMatchingOrAvailable(input, available, "No team found!")
        }
    }

    class GameTeamParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(GameTeamArgument::class.java, context, errorCaption, *captionVariables)

    companion object {
        fun <C : Any, T : Any> newBuilder(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument.Builder<C, GameTeamBuilder> {
            return Builder(key.name, gameManager)
        }

        fun <C : Any, T : Any> of(key: CloudKey<T>, gameManager: GameManager): CommandArgument<C, GameTeamBuilder> {
            return newBuilder<C, T>(key, gameManager).asRequired().build()
        }

        fun <C : Any, T : Any> optional(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument<C, GameTeamBuilder> {
            return newBuilder<C, T>(key, gameManager).asOptional().build()
        }
    }
}