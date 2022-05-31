package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.Countable
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import net.kyori.adventure.audience.ForwardingAudience
import java.util.*

/**
 * Manages players in given [GameFrame].
 */
interface FramePlayers : ForwardingAudience, Countable {
    /**
     * Joins given player to this frame.
     *
     *
     * If the player is already in this frame, do nothing.
     * If the player is in other frame, leave it and join him here.
     *
     * @param player player to join
     * @return [Resultable] result of this operation.
     */
    fun join(player: GamePlayer): Resultable

    /**
     * Leaves given player from this frame.
     *
     * @param player player to leave.
     * @return [Resultable] result of this operation.
     */
    fun leave(player: GamePlayer): Resultable

    fun joinTeam(player: GamePlayer, team: GameTeam): Resultable

    fun leaveTeam(player: GamePlayer, team: GameTeam): Resultable

    /**
     * Leaves all players from this frame.
     */
    fun leaveAll() {
        all().forEach { leave(it) }
    }

    /**
     * Check if this frame has given player.
     *
     * @param uuid player to check.
     * @return true if the player is in this frame.
     */
    fun has(uuid: UUID): Boolean

    /**
     * Check if this frame has given player.
     *
     * @param player player to check.
     * @return true if the player is in this frame.
     */
    fun has(player: GamePlayer): Boolean {
        return has(player.uuid)
    }

    /**
     * Checks if this frame has enough players to start.
     *
     * @return true if the frame has enough players to start.
     */
    fun hasEnough(): Boolean

    /**
     * Tries to find player by given UUID.
     *
     * @param uuid uuid to find.
     * @return [GamePlayer] if present.
     */
    fun find(uuid: UUID): GamePlayer?

    /**
     * Tries to find player by name.
     *
     * @param name name to find
     * @return [GamePlayer] if present.
     */
    fun find(name: String): GamePlayer?

    /**
     * Gets all players in this frame.
     *
     *
     * NOTE: this is an immutable copy, no changes will be applied to actual frame.
     *
     * @return all players joined.
     */
    fun all(): List<GamePlayer>

    /**
     * Changes player from any [GamePlayer.State] to [GamePlayer.State.ALIVE].
     *
     *
     * NOTE: result of this operation is dependent on the implementation of the [GameFrame].
     *
     * @param player player to change the state
     * @return [Resultable] result of this operation.
     */
    fun toAlive(player: GamePlayer): Resultable

    /**
     * Changes player from any [GamePlayer.State] to [GamePlayer.State.VIEWING].
     *
     *
     * NOTE: result of this operation is dependent on the implementation of the [GameFrame].
     *
     * @param player player to change the state
     * @return [Resultable] result of this operation.
     */
    fun toViewer(player: GamePlayer): Resultable

    /**
     * Changes player from any [GamePlayer.State] to [GamePlayer.State.SPECTATOR].
     *
     *
     * NOTE: result of this operation is dependent on the implementation of the [GameFrame].
     *
     * @param player player to change the state
     * @return [Resultable] result of this operation.
     */
    fun toSpectator(player: GamePlayer): Resultable

    /**
     * Changes player from any [GamePlayer.State] to [GamePlayer.State.LOBBY].
     *
     *
     * NOTE: result of this operation is dependent on the implementation of the [GameFrame].
     *
     * @param player player to change the state
     * @return [Resultable] result of this operation.
     */
    fun toLobby(player: GamePlayer): Resultable

    override fun count(): Int = all().size
}