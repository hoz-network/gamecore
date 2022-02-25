package net.hoz.gamecore.api.event.player.move

import net.hoz.gamecore.api.event.player.GamePlayerCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.utils.ObjectLink
import org.screamingsandals.lib.world.LocationHolder


data class GamePlayerMoveEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    val currentLocation: LocationHolder,
    private val newLocation: ObjectLink<LocationHolder>
) : GamePlayerCancellableEvent(player, frame) {


    fun getNewLocation(): LocationHolder {
        return newLocation.get()
    }

    fun setNewLocation(newLocation: LocationHolder) {
        this.newLocation.set(newLocation)
    }
}