package net.hoz.gamecore.api.game.frame.builder

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.game.world.GameWorldBuilder
import net.hoz.gamecore.api.service.GameManager
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.utils.Nameable
import java.util.*

/**
 * Entry point for building [GameFrame]s.
 */
interface GameBuilder : Nameable, Buildable.Builder<GameFrame> {
    /**
     * [UUID] of this builder.
     *
     *
     * NOTE: this [UUID] will be also used for [GameFrame].
     *
     * @return [UUID] of this builder.
     */
    val id: UUID

    val gameManager: GameManager

    /**
     * Teams building.
     *
     * @return [BuilderTeams]
     */
    fun teams(): BuilderTeams

    fun spawners(): BuilderSpawners

    fun stores(): BuilderStores

    fun world(): GameWorldBuilder

    /**
     * Management of this builder
     *
     * @return [Manage]
     */
    fun manage(): Manage

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