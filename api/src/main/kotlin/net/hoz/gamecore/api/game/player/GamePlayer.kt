package net.hoz.gamecore.api.game.player

import net.hoz.api.data.NetPlayer
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.team.GameTeam
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.player.PlayerWrapper

interface GamePlayer : PlayerWrapper {
    /**
     * Gets the underlying data for given player.
     * These data contains internal data for hoz.
     *
     * @return [NetPlayer] data.
     */
    val data: NetPlayer

    /**
     * Gets current state of this player.
     *
     * @return [State] current state.
     */
    val state: State

    /**
     * Gets the active [GameTeam] of this player.
     *
     * @return team if present.
     */
    val team: GameTeam?

    /**
     * Gets the active [GameFrame] of this player.
     *
     * @return frame if present.
     */
    val frame: GameFrame?

    @ApiStatus.Internal
    fun unsafe(): Unsafe

    /**
     * An unsafe operations of the player.
     * This is here for internal purposes only.
     */
    @ApiStatus.Internal
    interface Unsafe {
        /**
         * Sets new NetPlayer data.
         * NOTE: you should not replace the data by yourself.
         * NOTE: this method is here for internal purposes mainly.
         *
         * @param data new data
         */
        fun data(data: NetPlayer)

        /**
         * Sets new [State] state of the player.
         * NOTE: you should not replace the data by yourself.
         * NOTE: this method is here for internal purposes mainly.
         *
         * @param state new state
         */
        fun state(state: State)

        /**
         * Sets new active team.
         * NOTE: you should not replace the data by yourself.
         * NOTE: this method is here for internal purposes mainly.
         *
         * @param team new team
         */
        fun team(team: GameTeam?)

        /**
         * Sets new active frame.
         * NOTE: you should not replace the data by yourself.
         * NOTE: this method is here for internal purposes mainly.
         *
         * @param frame new frame
         */
        fun frame(frame: GameFrame?)
    }

    /**
     * Current state of the player
     */
    enum class State {
        /**
         * Player that is playing a game and is alive.
         */
        ALIVE,

        /**
         * Represents a player that is alive, but is temporary viewing.
         */
        VIEWING,

        /**
         * Player that is not playing, just spectating.
         */
        SPECTATOR,

        /**
         * Player is in lobby, waiting for game to start.
         */
        LOBBY,

        /**
         * Probably freshly joined player.
         */
        NOT_TRACKED;

        fun untracked(): Boolean {
            return this == NOT_TRACKED
        }
    }
}