package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import mu.KotlinLogging
import net.hoz.gamecore.api.command.GContext
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.team.TeamBuilderArgument
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.event.builder.team.GameTeamSpawnSetEvent
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.kotlin.fire
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper

private val log = KotlinLogging.logger {}

class SetTeamSpawnAction(
    parentAction: AbstractAction
) : AbstractBuilderSubAction(parentAction) {

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("spawn", ArgumentDescription.of("Sets a spawn for given team."))
                .senderType(PlayerWrapper::class.java)
                .argument(TeamBuilderArgument.of(GContext.COMMAND_TEAM_BUILDER_FIELD))
                .builderHandler { sender, gameBuilder, context ->
                    val team = context[GContext.COMMAND_TEAM_BUILDER_FIELD]
                    val location = sender.location
                    val world = gameBuilder.world.arenaWorld
                    val checkResult = world.isInBorder(location)
                    if (checkResult.isFail) {
                        //TODO: message
                        log.debug { "Cannot set team spawn - ${checkResult.message}" }
                        return@builderHandler
                    }

                    team.spawn = location
                    GameTeamSpawnSetEvent(team, gameBuilder, location).fire()

                    //TODO: lang
                    Message.of(CommandLang.SUCCESS_BUILDER_TEAM_SPAWN_SET)
                        .placeholder("team", team.coloredName())
                        .resolvePrefix()
                        .send(sender)
                }
            )
    }
}