package net.hoz.gamecore.api.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.GroupedResultable
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.service.GameManager
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.utils.Nameable
import reactor.core.publisher.Mono
import java.util.*

/**
 * Entry point for building [GameFrame]s.
 */
interface GameBuilder : Nameable {
    /**
     * [UUID] of this builder.
     *
     *
     * NOTE: this [UUID] will be also used for [GameFrame].
     *
     * @return [UUID] of this builder.
     */
    fun id(): UUID

    fun gameManager(): GameManager

    /**
     * Teams building.
     *
     * @return [BuilderTeams]
     */
    fun teams(): BuilderTeams

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

        /**
         * Saves this builder to the backend and creates a [GameFrame] from it.
         *
         * @return [Mono] with [DataResultable] of this operation.
         */
        fun save(): Mono<DataResultable<GameFrame>>
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