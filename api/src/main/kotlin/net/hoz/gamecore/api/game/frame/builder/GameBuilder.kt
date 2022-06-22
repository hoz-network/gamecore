package net.hoz.gamecore.api.game.frame.builder

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.game.world.GameWorldBuilder
import net.hoz.gamecore.api.service.GameManager
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.utils.Nameable
import java.util.*

/**
 * Builder for the [GameFrame].
 */
interface GameBuilder : Nameable, Buildable.Builder<GameFrame> {
    /**
     * Returns [UUID] of this builder.
     * This UUID is final and is also used for the GameFrame.
     */
    val uuid: UUID

    /**
     * instance of the [GameManager].
     */
    val gameManager: GameManager

    /**
     * Entrypoint for team builder.
     */
    val teams: BuilderTeams

    /**
     * Entrypoint for spawner builder.
     */
    val spawners: BuilderSpawners

    /**
     * Entrypoint for stores builder.
     */
    val stores: BuilderStores

    /**
     * Entrypoint for world builder.
     */
    var world: GameWorldBuilder

    /**
     * Management of this builder
     *
     * @return [Manage]
     */
    val manage: Manage

    /**
     * Internal unsafe operations.
     *
     * @return [Unsafe]
     */
    @ApiStatus.Internal
    fun unsafe(): Unsafe

    interface Manage {
        suspend fun save(): Resultable

        /**
         * Discards this builder.
         */
        fun destroy()

        /**
         * Checks if this builder can be saved.
         *
         * @return [GroupedResultable].
         */
        fun checkIntegrity(): GroupedResultable
    }

    @ApiStatus.Internal
    interface Unsafe {
        /**
         * Construct a hologram for given GameTeam.
         *
         *
         * - create spawn point hologram
         * - creates target hologram
         *
         * @param builder input for the hologram
         */
        fun teamHologram(builder: GameTeamBuilder)
    }
}