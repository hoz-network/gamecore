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
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.utils.AdventureHelper
import java.util.*
import java.util.function.BiFunction

class ColorArgument<C>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?
) : CommandArgument<C, NamedTextColor>(
    required,
    name,
    ColorParser(),
    defaultValue,
    NamedTextColor::class.java,
    suggestionsProvider
) {
    class Builder<C>(name: String) : CommandArgument.Builder<C, NamedTextColor>(NamedTextColor::class.java, name) {

        override fun build(): CommandArgument<C, NamedTextColor> {
            return ColorArgument(
                this.isRequired,
                this.name,
                this.defaultValue,
                this.suggestionsProvider
            )
        }
    }

    class ColorParser<C> : ArgumentParser<C, NamedTextColor> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<NamedTextColor> {
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(ColorParser::class.java, context))

            val color = NamedTextColor.NAMES.value(input)
                ?: return ArgumentParseResult.failure(ColorParseException(context, Caption.of("Color not found.")))

            inputQueue.remove()
            return ArgumentParseResult.success(color)
        }

        override fun suggestions(context: CommandContext<C>, input: String): MutableList<String> {
            val available = NamedTextColor.NAMES.values()
                .map { AdventureHelper.toLegacy(Component.text(it.toString()).color(it)) }
                .toMutableList()

            return GUtil.findMatchingOrAvailable(input, available, "No color found!")
        }
    }

    class ColorParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(ColorArgument::class.java, context, errorCaption, *captionVariables)

    companion object {
        fun <C> newBuilder(name: String): CommandArgument.Builder<C, NamedTextColor> {
            return Builder(name)
        }

        fun <C : Any> of(name: String): CommandArgument<C, NamedTextColor> {
            return newBuilder<C>(name).asRequired().build()
        }

        fun <C : Any> optional(name: String): CommandArgument<C, NamedTextColor> {
            return newBuilder<C>(name).asOptional().build()
        }
    }
}