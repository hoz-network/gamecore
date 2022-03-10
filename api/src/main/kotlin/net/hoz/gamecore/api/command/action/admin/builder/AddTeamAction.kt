package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.IntegerArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import io.leangen.geantyref.TypeToken
import mu.KotlinLogging
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.ColorArgument
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.lang.CommandLang
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper

class AddTeamAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {
    private val log = KotlinLogging.logger {}
    private val TEAM_NAME: CloudKey<String> =
        SimpleCloudKey.of("game-core-team-name", TypeToken.get(String::class.java))
    private val TEAM_COLOR: CloudKey<NamedTextColor> =
        SimpleCloudKey.of("game-core-team-color", TypeToken.get(NamedTextColor::class.java))
    private val TEAM_MAX_PLAYERS: CloudKey<Int> =
        SimpleCloudKey.of("game-core-team-max-players", TypeToken.get(Int::class.java))

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("team", ArgumentDescription.of("Adds new team to the fame."))
                .argument(StringArgument.of(TEAM_NAME.name))
                .argument(ColorArgument.of(TEAM_COLOR.name))
                .argument(
                    IntegerArgument.newBuilder<CommandSenderWrapper>(TEAM_MAX_PLAYERS.name)
                        .withMin(1)
                        .withMax(512)
                        .asRequired()
                        .build()
                )
                .builderHandler { sender, gameBuilder, context ->
                    val teamName = context[TEAM_NAME]
                    val color = context[TEAM_COLOR]
                    val maxPlayers = context[TEAM_MAX_PLAYERS]

                    log.debug { "Trying to create new team builder.." }
                    if (gameBuilder.teams().has(teamName)) {
                        Message.of(CommandLang.ERROR_BUILDER_TEAM_ALREADY_EXISTS)
                            .placeholder("name", teamName)
                            .resolvePrefix()
                            .send(sender)
                        return@builderHandler
                    }

                    val team = gameBuilder.teams()
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