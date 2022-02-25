package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.Countable
import net.hoz.gamecore.api.game.team.GameTeam

/**
 * Manages teams
 */
interface FrameTeams : Countable {
    /**
     * Registers new [GameTeam].
     * Given team's [GameFrame] is set to this instance.
     *
     * @param team new team to be added
     * @return Result of this operation.
     */
    fun add(team: GameTeam): Resultable

    /**
     * Registers new [GameTeam]s.
     * Given team's [GameFrame] is set to this instance.
     *
     * @param teams teams to be added
     */
    fun add(teams: Collection<GameTeam>)

    /**
     * Removes team from the frame by name.
     *
     * @param name name of the team to remove
     * @return Result of this operation.
     */
    fun remove(name: String): Resultable

    /**
     * Removes team from the frame.
     *
     * @param team team to remove.
     * @return Result of this operation.
     */
    fun remove(team: GameTeam): Resultable {
        return remove(team.name())
    }

    /**
     * Checks if game has this team.
     *
     * @param name name of the team to check
     * @return true if the [GameTeam] is in this [GameFrame].
     */
    fun has(name: String): Boolean

    /**
     * Checks if game has this team.
     *
     * @param team team to check
     * @return true if the [GameTeam] is in this [GameFrame].
     */
    fun has(team: GameTeam): Boolean {
        return has(team.name())
    }

    /**
     * Finds a team by name.
     *
     * @param name name to search for
     * @return [GameTeam] that has given name.
     */
    fun find(name: String): GameTeam?

    /**
     * Gets all teams in this frame.
     *
     * @return all teams available.
     */
    fun all(): Map<String, GameTeam>

    /**
     * Gets all teams that are alive in this frame.
     *
     * @return all teams alive.
     */
    fun allAlive(): List<GameTeam> {
        return all()
            .values
            .filter { it.alive() }
    }

    /**
     * Gets all teams that are dead in this frame.
     *
     * @return all teams dead.
     */
    fun allDead(): List<GameTeam> {
        return all()
            .values
            .filter { !it.alive() }
    }

    override fun count(): Int {
        return all().size
    }
}