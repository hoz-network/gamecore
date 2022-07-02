package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.gamecore.api.game.frame.FrameTeams
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.team.GameTeam

private val log = KotlinLogging.logger { }

internal class FrameTeamsImpl(
    private val frame: GameFrame
) : FrameTeams {
    private val teams: MutableMap<String, GameTeam> = mutableMapOf()

    override fun add(team: GameTeam): Resultable {
        if (has(team.name())) {
            return Resultable.fail("Team already exists.")
        }

        teams[team.name()] = team
        return Resultable.ok()
    }

    override fun remove(name: String): Resultable {
        teams.remove(name)
        return Resultable.ok()
    }

    override fun add(teams: Collection<GameTeam>) = teams.forEach { add(it) }
    override fun has(name: String): Boolean = teams.containsKey(name)
    override fun find(name: String): GameTeam? = teams[name]
    override fun all(): List<GameTeam> = teams.values.toList()
}