package net.hoz.gamecore.core.game.cycle.phase

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.core.game.cycle.CyclePhaseImpl
import net.hoz.gamecore.core.util.GConfig

abstract class SimpleInGamePhase(
    cycle: GameCycle,
    nextPhase: GamePhase
) : CyclePhaseImpl(cycle, GamePhase.IN_GAME, nextPhase) {

    override fun shouldTick(): Boolean {
        if (GConfig.ARE_TEAMS_ENABLED(frame)) {
            frame.teams()
                .all()
                .values
                .forEach { team ->
                    if (team.countPlayers() < 1) {
                        //todo fire event
                        team.unsafe().alive(false)
                    }
                }
        }

        return true
    }
}