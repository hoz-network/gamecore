package net.hoz.gamecore.api.game.store

import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameStore
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.utils.Nameable
import org.screamingsandals.lib.world.LocationHolder

interface GameStore : Nameable, ProtoWrapper<ProtoGameStore>, ForwardingAudience {
    /**
     * Location of the store on the map
     */
    fun location(): LocationHolder

    /**
     * Current store data available
     */
    fun holder(): StoreHolder

    /**
     * Entity type that this stored has
     */
    fun entityType(): EntityTypeHolder

    /**
     * Team for this store
     */
    fun team(): GameTeam?

    /**
     * Frame of this tore
     */
    fun frame(): GameFrame?

    /**
     * Gets all players that joined this store.
     *
     *
     * NOTE: this is an immutable copy,
     * changing this will take no effect on actual players in the store.
     *
     * @return [List] of players.
     */
    fun players(): List<GamePlayer>

    /**
     * Checks if this store has given player.
     *
     * @param player player to check
     * @return true if this store has given player
     */
    fun hasPlayer(player: GamePlayer): Boolean

    fun storeInventory(player: GamePlayer): StoreInventory

    fun open(player: GamePlayer)

    /**
     * Access point for unsafe API.
     * Internal use ONLY.
     *
     * @return [Unsafe]
     */
    @ApiStatus.Internal
    fun unsafe(): Unsafe

    override fun audiences(): Iterable<Audience> {
        return players()
    }

    interface Unsafe {
        /**
         * Adds player to this team.
         * NOTE: this is an internal API, use this at your own risk.
         *
         * @param player player to add
         */
        fun addPlayer(player: GamePlayer)

        /**
         * Removes player from this team.
         * NOTE: this is an internal API, use this at your own risk.
         *
         * @param player player to add
         */
        fun removePlayer(player: GamePlayer)

        /**
         * Updates the internal store data to new one.
         *
         * @param data new data.
         */
        fun update(data: StoreHolder)

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