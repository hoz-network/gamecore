package net.hoz.gamecore.api.command.argument.exception

import cloud.commandframework.captions.Caption
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.ParserException
import kotlin.reflect.KClass

class GameBuilderNotFoundException(
    argumentParser: KClass<*>,
    context: CommandContext<*>
) : ParserException(argumentParser.java, context, Caption.of("GameBuilder not found.")) {
}