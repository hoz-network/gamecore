package net.hoz.gamecore.api.game.team

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameTeam
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
interface GameTeam : Nameable, ProtoWrapper<ProtoGameTeam>, ForwardingAudience {
    /**
     * Gets the color of this team.
     *
     * @return [NamedTextColor] color.
     */
    fun color(): NamedTextColor

    /**
     * Gets colored [GameTeam.name].
     *
     * @return colored name.
     */
    fun coloredName(): Component

    /**
     * Gets spawn point of this team
     *
     * @return [LocationHolder] if present.
     */
    fun spawn(): LocationHolder

    /**
     * Gets target point of this team
     * Target == something to destroy by other teams.
     *
     * @return [LocationHolder] if present.
     */
    fun target(): LocationHolder?

    /**
     * Gets all players that joined this team.
     *
     *
     * NOTE: this is an immutable copy,
     * changing this will take no effect on actual players in the team.
     *
     * @return [List] of players.
     */
    fun players(): List<GamePlayer>

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
     * Gets max players that this team can have.
     *
     * @return max players of this team
     */
    fun maxPlayers(): Int

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
    fun isFull(): Boolean = players().size >= maxPlayers()

    /**
     * Checks if the team is empty
     *
     * @return true if the team is empty
     */
    fun isEmpty(): Boolean = players().isEmpty()


    /**
     * Gets the active [GameFrame] of this team.
     *
     * @return frame if present.
     */
    fun frame(): GameFrame?

    /**
     * Converts this team to builder
     */
    fun toBuilder(): DataResultable<Builder>

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

    /**
     * Builder of the team
     */
    interface Builder : Nameable {
        /**
         * Sets new name for this team
         *
         * @param name new name
         */
        fun name(name: String): Builder

        /**
         * Gets the color of this team.
         *
         * @return [NamedTextColor] color.
         */
        fun color(): NamedTextColor?

        /**
         * Colored name of this team
         *
         * @return [Component] name [Builder.name] with [Builder.color]
         */
        fun coloredName(): Component

        /**
         * Sets new color for this team.
         *
         * @param color new color
         */
        fun color(color: NamedTextColor): Builder

        /**
         * Gets spawn point of this team
         *
         * @return [LocationHolder] if present.
         */
        fun spawn(): LocationHolder?

        /**
         * Sets new spawn point of this team
         *
         * @param spawn new spawn point
         */
        fun spawn(spawn: LocationHolder): Builder

        /**
         * Gets target point of this team
         * Target == something to destroy by other teams.
         *
         * @return [LocationHolder] if present.
         */
        fun target(): LocationHolder?

        /**
         * Sets new target point of this team
         *
         * @param target new target point
         */
        fun target(target: LocationHolder): Builder

        /**
         * Gets max players that this team can have.
         *
         * @return max players of this team
         */
        fun maxPlayers(): Int?

        /**
         * Sets new max players that this team can have.
         *
         * @param maxPlayers new max players
         */
        fun maxPlayers(maxPlayers: Int): Builder

        /**
         * Creates new [GameTeam] from this builder.
         *
         *
         * NOTE: the team NEEDS to have all properties set, otherwise it is not possible to save the team.
         * NOTE: this method WILL be called by the GameBuilder, you don't need to do it.
         *
         * @return [DataResultable] result of this operation.
         */
        fun build(): DataResultable<GameTeam>
    }
}