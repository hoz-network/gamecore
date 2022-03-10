package net.hoz.gamecore.api.game.world

import org.screamingsandals.lib.block.BlockHolder
import org.screamingsandals.lib.world.LocationHolder

/**
 * Reverts the [WorldData] to it's state before the game started.
 */
interface WorldRegenerator {
    fun regenerate()
    fun addBlock(type: Type, block: BlockHolder)
    fun removeBlock(type: Type, block: BlockHolder)
    fun removeBlock(type: Type, location: LocationHolder)
    fun wasBlockAddedDuringGame(holder: LocationHolder): Boolean

    enum class Type {
        BUILT, DESTROYED, INTERACTED
    }

    companion object {
        fun regenerator(): WorldRegenerator = WorldRegeneratorImpl()
    }
}