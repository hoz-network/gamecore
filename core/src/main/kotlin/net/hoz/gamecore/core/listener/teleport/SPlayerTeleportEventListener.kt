package net.hoz.gamecore.core.listener.teleport

import net.hoz.gamecore.api.event.SEventHandlerFactory
import net.hoz.gamecore.api.event.player.teleport.GamePlayerTeleportEvent
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.EventPriority
import org.screamingsandals.lib.event.player.SPlayerTeleportEvent
import org.screamingsandals.lib.kotlin.unwrap

class SPlayerTeleportEventListener : SEventHandlerFactory<GamePlayerTeleportEvent, SPlayerTeleportEvent>(
    GamePlayerTeleportEvent::class.java,
    SPlayerTeleportEvent::class.java
) {

    override fun wrapEvent(event: SPlayerTeleportEvent, priority: EventPriority): GamePlayerTeleportEvent? {
        val gamePlayer = event.player().unwrap(GamePlayer::class)
        val frame = gamePlayer.frame() ?: return null

        return GamePlayerTeleportEvent(
            gamePlayer,
            frame,
            event.currentLocation(),
            event.newLocation()
        )
    }
}