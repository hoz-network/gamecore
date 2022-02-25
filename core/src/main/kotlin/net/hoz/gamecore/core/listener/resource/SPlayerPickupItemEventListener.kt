package net.hoz.gamecore.core.listener.resource

import mu.KotlinLogging
import net.hoz.gamecore.api.event.SEventHandlerFactory
import net.hoz.gamecore.api.event.player.resource.GamePlayerResourcePickEvent
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.util.GLang
import org.screamingsandals.lib.event.EventPriority
import org.screamingsandals.lib.event.player.SPlayerPickupItemEvent
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.utils.ObjectLink

class SPlayerPickupItemEventListener : SEventHandlerFactory<GamePlayerResourcePickEvent, SPlayerPickupItemEvent>(
    GamePlayerResourcePickEvent::class.java,
    SPlayerPickupItemEvent::class.java
) {
    private val log = KotlinLogging.logger { }

    override fun wrapEvent(event: SPlayerPickupItemEvent, priority: EventPriority): GamePlayerResourcePickEvent? {
        val gamePlayer = event.player().unwrap(GamePlayer::class)
        val frame = gamePlayer.frame() ?: return null

        if (!frame.manage().isRunning()) {
            event.cancelled(true)
            return null
        }

        val pickedItem = event.item()
        val spawner = frame.spawners().findByItem(pickedItem)
        if (spawner == null) {
            log.trace("Spawner not found for given item, doing nothing in event ${event.javaClass.simpleName}")
            return null
        }

        return GamePlayerResourcePickEvent(
            gamePlayer,
            frame,
            pickedItem,
            ObjectLink.of({ GLang.SPAWNER_TYPE(gamePlayer, spawner) }, { pickedItem.customName = it })
        )
    }

}