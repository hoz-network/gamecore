package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import mu.KotlinLogging
import net.hoz.gamecore.api.game.frame.builder.BuilderBaseImpl
import net.hoz.gamecore.api.game.frame.builder.BuilderTeams
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.core.game.team.GameTeamBuilderImpl

class BuilderTeamsImpl : BuilderBaseImpl<GameTeamBuilder, GameTeam, String>(), BuilderTeams {
    private val log = KotlinLogging.logger { }

    override fun add(id: String, builder: GameTeamBuilder.() -> Unit): GameTeamBuilder {
        val teamBuilder = GameTeamBuilderImpl(id)
        builder.invoke(teamBuilder)

        builders[id] = teamBuilder
        return teamBuilder
    }

    override fun build(): Map<String, DataResultable<GameTeam>> {
        return builders
            .map {
                val id = it.key
                val result = it.value.build()

                log.debug { "Building of team [${id}] resulted - $result" }
                Pair(id, result)
            }
            .toMap()
    }
}