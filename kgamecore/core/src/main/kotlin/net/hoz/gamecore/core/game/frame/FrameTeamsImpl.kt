package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.frame.FrameTeams
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.team.GameTeam

open class FrameTeamsImpl(
    protected val frame: GameFrame
) : FrameTeams {
    protected val teams: MutableMap<String, GameTeam> = mutableMapOf()

    override fun add(team: GameTeam): Resultable {
        if (has(team.name())) {
            return Resultable.fail("Team already exists.")
        }

        teams[team.name()] = team
        return Resultable.ok()
    }

    override fun add(teams: Collection<GameTeam>) {
        teams.forEach { add(it) }
    }

    override fun remove(name: String): Resultable {
        teams.remove(name)
        return Resultable.ok()
    }

    override fun has(name: String): Boolean {
        return teams.containsKey(name)
    }

    override fun find(name: String): GameTeam? {
        return teams[name]
    }

    override fun all(): Map<String, GameTeam> {
        TODO("Not yet implemented")
    }
}