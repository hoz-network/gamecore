package net.hoz.gamecore.api.event.player.teleport

import net.hoz.gamecore.api.event.player.GamePlayerEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.world.LocationHolder

data class GamePlayerTeleportEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    val currentLocation: LocationHolder,
    val newLocation: LocationHolder
) : GamePlayerEvent(player, frame)