package net.hoz.gamecore.api.command.argument.spawner

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.captions.Caption
import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.exceptions.parsing.ParserException
import cloud.commandframework.keys.CloudKey
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.api.util.GUtil
import java.util.*
import java.util.function.BiFunction

class GameSpawnerTypeArgument<C>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?,
    gameManager: GameManager
) : CommandArgument<C, GameSpawnerType>(
    required,
    name,
    SpawnerTypeParser(gameManager),
    defaultValue,
    GameSpawnerType::class.java,
    suggestionsProvider
) {
    class Builder<C>(
        name: String,
        private val gameManager: GameManager
    ) : CommandArgument.Builder<C, GameSpawnerType>(GameSpawnerType::class.java, name) {

        override fun build(): CommandArgument<C, GameSpawnerType> {
            return GameSpawnerTypeArgument(
                this.isRequired,
                this.name,
                this.defaultValue,
                this.suggestionsProvider,
                gameManager
            )
        }
    }

    class SpawnerTypeParser<C>(private val gameManager: GameManager) : ArgumentParser<C, GameSpawnerType> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<GameSpawnerType> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(SpawnerTypeParser::class.java, context))

            val availableTypes = gameManager.backend().allSpawners()
            if (availableTypes.isEmpty()) {
                return ArgumentParseResult.failure(
                    SpawnerTypeParseException(context, Caption.of("No types are available in backend!"))
                )
            }

            val toReturn = availableTypes.firstOrNull { it.name == input }
                ?: return ArgumentParseResult.failure(
                    SpawnerTypeParseException(context, Caption.of("No type found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(GameSpawnerType.of(toReturn))
        }

        override fun suggestions(commandContext: CommandContext<C>, input: String): MutableList<String> {
            val types = gameManager.backend()
                .allSpawners()
                .map { it.name }

            return GUtil.findMatchingOrAvailable(input, types, "No spawner type found!")
        }
    }

    class SpawnerTypeParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(SpawnerTypeParser::class.java, context, errorCaption, *captionVariables)

    companion object {
        fun <C : Any, T : Any> newBuilder(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument.Builder<C, GameSpawnerType> {
            return Builder(key.name, gameManager)
        }

        fun <C : Any, T : Any> of(key: CloudKey<T>, gameManager: GameManager): CommandArgument<C, GameSpawnerType> {
            return newBuilder<C, T>(key, gameManager).asRequired().build()
        }

        fun <C : Any, T : Any> optional(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument<C, GameSpawnerType> {
            return newBuilder<C, T>(key, gameManager).asOptional().build()
        }
    }
}