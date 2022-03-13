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
import net.hoz.gamecore.api.command.COMMAND_GAME_BUILDER_FIELD
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.util.GUtil
import java.util.*
import java.util.function.BiFunction

class TeamBuilderArgument<C>(
    required: Boolean,
    name: String,
    defaultValue: String,
    suggestionsProvider: BiFunction<CommandContext<C>, String, MutableList<String>>?
) : CommandArgument<C, GameTeamBuilder>(
    required,
    name,
    TeamBuilderParser(),
    defaultValue,
    GameTeamBuilder::class.java,
    suggestionsProvider
) {
    companion object {
        fun <C : Any, T : Any> newBuilder(
            key: CloudKey<T>
        ): CommandArgument.Builder<C, GameTeamBuilder> = Builder(key.name)

        fun <C : Any, T : Any> of(key: CloudKey<T>): CommandArgument<C, GameTeamBuilder> =
            newBuilder<C, T>(key).asRequired().build()

        fun <C : Any, T : Any> optional(key: CloudKey<T>): CommandArgument<C, GameTeamBuilder> =
            newBuilder<C, T>(key).asOptional().build()
    }

    class Builder<C>(
        name: String
    ) : CommandArgument.Builder<C, GameTeamBuilder>(GameTeamBuilder::class.java, name) {
        override fun build(): CommandArgument<C, GameTeamBuilder> = TeamBuilderArgument(
            this.isRequired,
            this.name,
            this.defaultValue,
            this.suggestionsProvider
        )
    }

    class TeamBuilderParser<C>() : ArgumentParser<C, GameTeamBuilder> {
        override fun parse(
            context: CommandContext<C>,
            inputQueue: Queue<String>
        ): ArgumentParseResult<GameTeamBuilder> {
            if (context.contains(COMMAND_GAME_BUILDER_FIELD)) {
                return ArgumentParseResult.failure(
                    TeamBuilderParseException(
                        context,
                        Caption.of("TeamBuilder not found!")
                    )
                )
            }
            val builder = context[COMMAND_GAME_BUILDER_FIELD]
            val input = inputQueue.peek()
                ?: return ArgumentParseResult.failure(NoInputProvidedException(TeamBuilderParser::class.java, context))

            val available = builder.teams().all()
            if (available.isEmpty()) {
                return ArgumentParseResult.failure(
                    TeamBuilderParseException(context, Caption.of("No team is registered!"))
                )
            }

            val toReturn = available[input]
                ?: return ArgumentParseResult.failure(
                    TeamBuilderParseException(context, Caption.of("Team not found."))
                )

            inputQueue.remove()
            return ArgumentParseResult.success(toReturn)
        }

        override fun suggestions(context: CommandContext<C>, input: String): MutableList<String> {
            if (context.contains(COMMAND_GAME_BUILDER_FIELD)) {
                return mutableListOf("Builder not found!")
            }

            val builder = context[COMMAND_GAME_BUILDER_FIELD]
            val available = builder.teams()
                .all()
                .keys
                .toList()

            return GUtil.findMatchingOrAvailable(input, available, "No team found!")
        }
    }

    class TeamBuilderParseException(
        context: CommandContext<*>,
        errorCaption: Caption,
        vararg captionVariables: CaptionVariable
    ) : ParserException(TeamBuilderArgument::class.java, context, errorCaption, *captionVariables)
}