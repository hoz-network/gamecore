package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.IntegerArgument
import cloud.commandframework.arguments.standard.StringArgument
import mu.KotlinLogging
import net.hoz.gamecore.api.command.GContext.COMMAND_TEAM_COLOR
import net.hoz.gamecore.api.command.GContext.COMMAND_TEAM_MAX_PLAYERS
import net.hoz.gamecore.api.command.GContext.COMMAND_TEAM_NAME
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.ColorArgument
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper

private val log = KotlinLogging.logger {}

class AddTeamAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {
    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("team", ArgumentDescription.of("Adds new team to the fame."))
                .argument(StringArgument.of(COMMAND_TEAM_NAME.name))
                .argument(ColorArgument.of(COMMAND_TEAM_COLOR.name))
                .argument(
                    IntegerArgument.newBuilder<CommandSenderWrapper>(COMMAND_TEAM_MAX_PLAYERS.name)
                        .withMin(1)
                        .withMax(512)
                        .asRequired()
                        .build()
                )
                .builderHandler { sender, gameBuilder, context ->
                    val teamName = context[COMMAND_TEAM_NAME]
                    val color = context[COMMAND_TEAM_COLOR]
                    val maxPlayers = context[COMMAND_TEAM_MAX_PLAYERS]

                    log.debug { "Trying to create new team builder.." }
                    if (gameBuilder.teams.has(teamName)) {
                        Message.of(CommandLang.ERROR_BUILDER_TEAM_ALREADY_EXISTS)
                            .placeholder("name", teamName)
                            .resolvePrefix()
                            .send(sender)
                        return@builderHandler
                    }

                    if (maxPlayers < 1) {
                        Message.of(CommandLang.ERROR_BUILDER_TEAM_NEEDS_MORE_PLAYERS)
                            .placeholder("name", teamName)
                            .placeholder("players", maxPlayers.toString())
                            .resolvePrefix()
                            .send(sender)
                        return@builderHandler;
                    }

                    val team = gameBuilder.teams
                        .add(teamName) {
                            this.color = color
                            this.maxPlayers = maxPlayers
                        }

                    log.debug { "Created new team builder: $team" }

                    //TODO: lang
                    Message.of(CommandLang.SUCCESS_BUILDER_TEAM_ADDED)
                        .placeholder("name", team.name)
                        .resolvePrefix()
                        .send(sender)
                }
            )
    }
}