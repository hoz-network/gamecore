package net.hoz.gamecore.api.game.store

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameStore
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.spectator.audience.Audience
import org.screamingsandals.lib.utils.Nameable
import org.screamingsandals.lib.world.LocationHolder

interface GameStore : Nameable, ProtoWrapper<ProtoGameStore>, Audience.ForwardingToMulti {
    /**
     * Location of the store on the map
     */
    val location: LocationHolder

    /**
     * Current store data available
     */
    val holder: StoreHolder

    /**
     * Entity type that this stored has
     */
    val entityType: EntityTypeHolder

    /**
     * Team for this store
     */
    val team: GameTeam?

    /**
     * Gets all players that joined this store.
     *
     *
     * NOTE: this is an immutable copy,
     * changing this will take no effect on actual players in the store.
     *
     * @return [List] of players.
     */
    val players: List<GamePlayer>

    /**
     * Checks if this store has given player.
     *
     * @param player player to check
     * @return true if this store has given player
     */
    fun hasPlayer(player: GamePlayer): Boolean

    fun storeInventory(player: GamePlayer): DataResultable<StoreInventory>

    fun open(player: GamePlayer): Resultable

    /**
     * Access point for unsafe API.
     * Internal use ONLY.
     *
     * @return [Unsafe]
     */
    @ApiStatus.Internal
    fun unsafe(): Unsafe

    override fun audiences(): Iterable<Audience> = players

    interface Unsafe {
        /**
         * Adds player to this team.
         * NOTE: this is an internal API, use this at your own risk.
         *
         * @param player player to builder
         */
        fun addPlayer(player: GamePlayer)

        /**
         * Removes player from this team.
         * NOTE: this is an internal API, use this at your own risk.
         *
         * @param player player to builder
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