package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import mu.KotlinLogging
import net.hoz.api.data.game.ProtoWorldData.WorldType
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.event.builder.world.GameWorldSpawnSetEvent
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.kotlin.fire
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper

private val log = KotlinLogging.logger {}

class SetSpawnAction(
    parentAction: AbstractAction,
    private val worldType: WorldType,
) : AbstractBuilderSubAction(parentAction) {

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("spawn", ArgumentDescription.of("Sets a spawn for $worldType"))
                .senderType(PlayerWrapper::class.java)
                .builderHandler { sender, gameBuilder, _ ->
                    val location = sender.location
                    val world = when (worldType) {
                        WorldType.ARENA -> gameBuilder.world.arenaWorld
                        WorldType.LOBBY -> gameBuilder.world.lobbyWorld
                        else -> throw UnsupportedOperationException("World type not supported.")
                    }

                    val checkResult = world.isInBorder(location)
                    if (checkResult.isFail) {
                        //TODO: message
                        log.debug { "Cannot set border for world $worldType - ${checkResult.message}" }
                        return@builderHandler
                    }

                    world.spawn = location

                    GameWorldSpawnSetEvent(world, gameBuilder, location).fire()

                    //TODO: lang
                    Message.of(CommandLang.SUCCESS_BUILDER_SPAWN_SET)
                        .resolvePrefix()
                        .send(sender)
                }
            )
    }
}