package net.hoz.gamecore.core.game.cycle.phase

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.core.game.cycle.CyclePhaseImpl

abstract class SimpleStartingPhase(
    cycle: GameCycle,
    nextPhase: GamePhase
) : CyclePhaseImpl(cycle, GamePhase.STARTING, nextPhase) {

    override fun shouldTick(): Boolean {
        if (!frame.players().hasEnough()) {
            cycle.switchPhase(GamePhase.WAITING)
            return false
        }

        return true
    }
}