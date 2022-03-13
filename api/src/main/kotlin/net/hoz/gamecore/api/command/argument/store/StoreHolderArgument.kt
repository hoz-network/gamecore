package net.hoz.gamecore.api.command.argument.store

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.captions.Caption
import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.exceptions.parsing.ParserException
import cloud.commandframework.keys.CloudKey
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.api.util.GUtil
import java.util.*
import java.util.function.BiFunction

class StoreHolderArgument<C>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?,
    gameManager: GameManager
) : CommandArgument<C, StoreHolder>(
    required,
    name,
    StoreHolderParser(gameManager),
    defaultValue,
    StoreHolder::class.java,
    suggestionsProvider
) {
    companion object {
        fun <C : Any, T : Any> newBuilder(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument.Builder<C, StoreHolder> = Builder(key.name, gameManager)

        fun <C : Any, T : Any> of(key: CloudKey<T>, gameManager: GameManager): CommandArgument<C, StoreHolder> =
            newBuilder<C, T>(key, gameManager).asRequired().build()

        fun <C : Any, T : Any> optional(
            key: CloudKey<T>,
            gameManager: GameManager
        ): CommandArgument<C, StoreHolder> = newBuilder<C, T>(key, gameManager).asOptional().build()
    }

    class Builder<C>(
        name: String,
        private val gameManager: GameManager
    ) : CommandArgument.Builder<C, StoreHolder>(StoreHolder::class.java, name) {
        override fun build(): CommandArgument<C, StoreHolder> = StoreHolderArgument(
            this.isRequired,
            this.name,
            this.defaultValue,
            this.suggestionsProvider,
            gameManager
        )
    }

    class StoreHolderParser<C>(private val gameManager: GameManager) : ArgumentParser<C, StoreHolder> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<StoreHolder> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(StoreHolderParser::class.java, context))

            val availableTypes = gameManager.backend().allStores()
            if (availableTypes.isEmpty()) {
                return ArgumentParseResult.failure(
                    StoreHolderParseException(context, Caption.of("No holders are available in backend!"))
                )
            }

            val toReturn = availableTypes.firstOrNull { it.name == input }
                ?: return ArgumentParseResult.failure(
                    StoreHolderParseException(context, Caption.of("No holder found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(toReturn)
        }

        override fun suggestions(commandContext: CommandContext<C>, input: String): MutableList<String> {
            val types = gameManager.backend()
                .allStores()
                .map { it.name }

            return GUtil.findMatchingOrAvailable(input, types, "No StoreHolder found!")
        }
    }

    class StoreHolderParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(StoreHolderParser::class.java, context, errorCaption, *captionVariables)
}