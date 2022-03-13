package net.hoz.gamecore.core.listener.death

import net.hoz.gamecore.api.event.SEventHandlerFactory
import net.hoz.gamecore.api.event.player.death.GamePlayerDeathEvent
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.EventPriority
import org.screamingsandals.lib.event.player.SPlayerDeathEvent
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.utils.ObjectLink

class SPlayerDeathEventListener : SEventHandlerFactory<GamePlayerDeathEvent, SPlayerDeathEvent>(
    GamePlayerDeathEvent::class.java,
    SPlayerDeathEvent::class.java
) {
    override fun wrapEvent(event: SPlayerDeathEvent, priority: EventPriority): GamePlayerDeathEvent? {
        val gamePlayer = event.player().unwrap(GamePlayer::class)
        val frame = gamePlayer.frame ?: return null

        if (frame.manage.isWaiting()) {
            val world = frame.world().lobbyWorld
            gamePlayer.teleport(world.spawn)
        }

        val killer = event.killer()
        val gameKiller = killer?.unwrap(GamePlayer::class)

        return GamePlayerDeathEvent(
            gamePlayer,
            frame,
            ObjectLink.of({ event.deathMessage() }, { event.deathMessage(it) }),
            event.drops(),
            ObjectLink.of({ event.keepInventory() }, { event.keepInventory(it) }),
            ObjectLink.of({ event.shouldDropExperience() }, { event.shouldDropExperience(it) }),
            ObjectLink.of({ event.keepLevel() }, { event.keepLevel(it) }),
            ObjectLink.of({ event.newLevel() }, { event.newLevel(it) }),
            ObjectLink.of({ event.newTotalExp() }, { event.newTotalExp(it) }),
            ObjectLink.of({ event.newExp }, { event.newExp(it) }),
            ObjectLink.of({ event.droppedExp() }, { event.droppedExp(it) }),
            gameKiller
        )
    }
}