package net.hoz.gamecore.core.listener.move

import net.hoz.gamecore.api.event.SEventHandlerFactory
import net.hoz.gamecore.api.event.player.move.GamePlayerMoveEvent
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.EventPriority
import org.screamingsandals.lib.event.player.SPlayerMoveEvent
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.utils.ObjectLink

class SPlayerMoveEventListener : SEventHandlerFactory<GamePlayerMoveEvent, SPlayerMoveEvent>(
    GamePlayerMoveEvent::class.java,
    SPlayerMoveEvent::class.java
) {
    override fun wrapEvent(event: SPlayerMoveEvent, priority: EventPriority): GamePlayerMoveEvent? {
        val gamePlayer = event.player().unwrap(GamePlayer::class)
        val frame = gamePlayer.frame() ?: return null

        if (frame.manage().isWaiting()) {
            val lobby = frame.world().lobbyWorld
            if (lobby.isInBorder(event.newLocation()).isFail) {
                event.cancelled(true)
                return null
            }
        }

        if (frame.manage().isRunning()) {
            val arena = frame.world().arenaWorld
            if (arena.isInBorder(event.newLocation()).isFail) {
                event.cancelled(true)
                return null
            }
        }

        return GamePlayerMoveEvent(
            gamePlayer,
            frame,
            event.currentLocation(),
            ObjectLink.of({ event.newLocation() }, { event.newLocation(it) })
        )
    }
}