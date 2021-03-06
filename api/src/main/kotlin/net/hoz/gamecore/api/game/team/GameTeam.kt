package net.hoz.gamecore.api.game.team

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameTeam
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.utils.Nameable
import org.screamingsandals.lib.world.LocationHolder

/**
 * A team of players.
 */
interface GameTeam : Nameable, Buildable<GameTeam, GameTeamBuilder>, ProtoWrapper<ProtoGameTeam>, ForwardingAudience {
    /**
     * Gets the color of this team.
     *
     * @return [NamedTextColor] color.
     */
    val color: NamedTextColor

    /**
     * Gets spawn point of this team
     *
     * @return [LocationHolder] if present.
     */
    val spawn: LocationHolder

    /**
     * Gets target point of this team
     * Target == something to destroy by other teams.
     *
     * @return [LocationHolder] if present.
     */
    val target: LocationHolder?

    /**
     * Gets max players that this team can have.
     *
     * @return max players of this team
     */
    val maxPlayers: Int

    /**
     * Gets all players that joined this team.
     *
     * NOTE: this is an immutable copy,
     * changing this will take no effect on actual players in the team.
     *
     * @return [List] of players.
     */
    val players: List<GamePlayer>

    /**
     * Gets colored [GameTeam.name].
     *
     * @return colored name.
     */
    fun coloredName(): Component

    /**
     * Counts all players in this team.
     *
     * @return count of the players in this team.
     */
    fun countPlayers(): Int

    /**
     * Checks if this team has given player.
     *
     * @param player player to check
     * @return true if this team has given player
     */
    fun hasPlayer(player: GamePlayer): Boolean

    /**
     * Checks if this team is alive
     *
     * @return true if team is alive
     */
    fun alive(): Boolean

    /**
     * Checks if the team is full
     *
     * @return true if the team is full
     */
    fun isFull(): Boolean = countPlayers() >= maxPlayers

    /**
     * Checks if the team is empty
     *
     * @return true if the team is empty
     */
    fun isEmpty(): Boolean = players.isEmpty()

    /**
     * Gets the active [GameFrame] of this team.
     *
     * @return frame if present.
     */
    fun frame(): GameFrame?

    /**
     * Access point for unsafe API.
     * Internal use ONLY.
     *
     * @return [Unsafe]
     */
    @ApiStatus.Internal
    fun unsafe(): Unsafe

    override fun audiences(): Iterable<Audience> = players

    /**
     * An unsafe operations of the team.
     * This is here for internal purposes only.
     */
    @ApiStatus.Internal
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
         * Sets new alive state for this team.
         * NOTE: this is an internal API, use this at your own risk.
         *
         * @param alive new state
         */
        fun alive(alive: Boolean)

        /**
         * Sets new active frame.
         * NOTE: this is an internal API, use this at your own risk.
         *
         * @param frame new frame
         */
        fun frame(frame: GameFrame?)
    }
}