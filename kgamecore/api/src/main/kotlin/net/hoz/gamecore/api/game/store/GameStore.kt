package net.hoz.gamecore.api.game.store

import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.utils.Nameable
import org.screamingsandals.lib.world.LocationHolder

interface GameStore : Nameable {
    fun holder(): StoreHolder
    fun team(): GameTeam?
    fun location(): LocationHolder
    fun entityType(): EntityTypeHolder
    fun open(player: GamePlayer)
    fun add(player: GamePlayer)
    fun remove(player: GamePlayer)
    fun storeInventory(player: GamePlayer): StoreInventory
    fun players(): List<GamePlayer>

    fun unsafe(): Unsafe

    interface Unsafe {
        /**
         * Updates the internal store data to new one.
         *
         * @param data new data.
         */
        fun update(data: StoreHolder?)

        /**
         * Creates the store entity, hologram and starts the store.
         */
        fun create()

        /**
         * Repaints the data if the inventory is currently opened.
         */
        fun repaint()

        /**
         * Closes the inventories for all players and removes entity and holograms.
         */
        fun destroy()
    }
}