package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import mu.KotlinLogging
import net.hoz.gamecore.api.event.builder.team.GameTeamModifiedEvent
import net.hoz.gamecore.api.event.builder.team.GameTeamRemovedEvent
import net.hoz.gamecore.api.game.frame.builder.BuilderTeams
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.frame.builder.base.BuilderBaseImpl
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.core.game.team.GameTeamBuilderImpl
import org.screamingsandals.lib.kotlin.fire

private val log = KotlinLogging.logger { }

class BuilderTeamsImpl(
    private val gameBuilder: GameBuilder
) : BuilderBaseImpl<GameTeamBuilder, GameTeam, String>(), BuilderTeams {
    override fun provideBuilder(id: String): GameTeamBuilder = GameTeamBuilderImpl(id)

    override fun onModify(builder: GameTeamBuilder) {
        GameTeamModifiedEvent(builder, gameBuilder).fire()
    }

    override fun onRemove(builder: GameTeamBuilder) {
        GameTeamRemovedEvent(builder, gameBuilder).fire()
    }

    override fun build(): Map<String, DataResultable<GameTeam>> = builders.map {
        val id = it.key
        val result = it.value.build()

        log.debug { "Building of team [${id}] resulted - $result" }
        Pair(id, result)
    }.toMap()
}