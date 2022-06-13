package net.hoz.gamecore.api.command.argument

import cloud.commandframework.arguments.CommandArgument
import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.captions.Caption
import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import cloud.commandframework.exceptions.parsing.ParserException
import net.hoz.gamecore.api.util.GUtil
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.spectator.Component
import java.util.*
import java.util.function.BiFunction

class ColorArgument<C: Any>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?
) : CommandArgument<C, Color>(
    required,
    name,
    ColorParser(),
    defaultValue,
    Color::class.java,
    suggestionsProvider
) {
    companion object {
        fun <C : Any> newBuilder(name: String): CommandArgument.Builder<C, Color> = Builder(name)

        fun <C : Any> of(name: String): CommandArgument<C, Color> = newBuilder<C>(name).asRequired().build()

        fun <C : Any> optional(name: String): CommandArgument<C, Color> = newBuilder<C>(name).asOptional().build()
    }

    class Builder<C : Any>(name: String) : CommandArgument.Builder<C, Color>(Color::class.java, name) {
        override fun build(): CommandArgument<C, Color> = ColorArgument(
            this.isRequired,
            this.name,
            this.defaultValue,
            this.suggestionsProvider
        )
    }

    class ColorParser<C : Any> : ArgumentParser<C, Color> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<Color> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(ColorParser::class.java, context))

            val color = Color.NAMED_VALUES[input]
                ?: return ArgumentParseResult.failure(ColorParseException(context, Caption.of("Color not found.")))

            inputQueue.remove()
            return ArgumentParseResult.success(color)
        }

        override fun suggestions(context: CommandContext<C>, input: String): MutableList<String> {
            val available = Color.NAMED_VALUES
                .map { Component.text(it.key).withColor(it.value).toLegacy() }
                .toMutableList()

            return GUtil.findMatchingOrAvailable(input, available, "No color found!")
        }
    }

    class ColorParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(ColorArgument::class.java, context, errorCaption, *captionVariables)
}