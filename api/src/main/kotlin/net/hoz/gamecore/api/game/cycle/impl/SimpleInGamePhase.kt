package net.hoz.gamecore.api.game.cycle.impl

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.util.GConfig

abstract class SimpleInGamePhase(cycle: GameCycle) :
    AbstractCyclePhase(cycle, GamePhase.IN_GAME, GamePhase.DEATH_MATCH) {

    override fun doPreTick(): Boolean {
        if (GConfig.ARE_TEAMS_ENABLED(frame)) {
            frame.teams
                .all()
                .forEach { team ->
                    if (team.countPlayers() < 1) {
                        //todo fire event
                        team.unsafe().alive(false)
                    }
                }
        }

        return super.doPreTick()
    }
}