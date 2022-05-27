package net.hoz.gamecore.core.listener.block

import net.hoz.gamecore.api.event.SEventHandlerFactory
import net.hoz.gamecore.api.event.player.block.GamePlayerBlockBreakEvent
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.world.WorldRegenerator
import net.hoz.gamecore.api.util.GConfig
import org.screamingsandals.lib.event.EventPriority
import org.screamingsandals.lib.event.player.SPlayerBlockBreakEvent
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.utils.ObjectLink

class SPlayerBlockBreakEventListener
    : SEventHandlerFactory<GamePlayerBlockBreakEvent, SPlayerBlockBreakEvent>(
    GamePlayerBlockBreakEvent::class.java,
    SPlayerBlockBreakEvent::class.java
) {

    override fun wrapEvent(event: SPlayerBlockBreakEvent, priority: EventPriority): GamePlayerBlockBreakEvent? {
        val gamePlayer = event.player().unwrap(GamePlayer::class)
        val frame = gamePlayer.frame ?: return null
        if (!frame.manage.isRunning()) {
            return null
        }

        val world = frame.world().arenaWorld
        val playerLocation = gamePlayer.location
        if (world.isInBorder(playerLocation).isFail) {
            event.cancelled(true)
            return null
        }

        val block = event.block()
        if (block.isEmpty) {
            return null
        }

        return GamePlayerBlockBreakEvent(
            gamePlayer,
            frame,
            block,
            ObjectLink.of({ event.dropItems() }, { event.dropItems(it) })
        )
    }

    override fun postProcess(wrappedEvent: GamePlayerBlockBreakEvent, event: SPlayerBlockBreakEvent) {
        val frame = wrappedEvent.frame
        val world = frame.world().arenaWorld
        val block = event.block()
        val blockLocation = block.location

        val regenerator = world.regenerator
        if (regenerator.wasBlockAddedDuringGame(blockLocation)) {
            regenerator.removeBlock(WorldRegenerator.Type.BUILT, blockLocation)
            return
        }

        val destroyableBlocks = GConfig.GET_DESTROYABLE_BLOCKS(frame)
        if (destroyableBlocks.contains(block.type)) {
            regenerator.addBlock(WorldRegenerator.Type.DESTROYED, block)
            return
        }

        event.cancelled(true)
    }
}