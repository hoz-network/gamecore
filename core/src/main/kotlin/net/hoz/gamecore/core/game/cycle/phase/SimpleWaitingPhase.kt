package net.hoz.gamecore.core.game.cycle.phase

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.core.game.cycle.CyclePhaseImpl

abstract class SimpleWaitingPhase(
    cycle: GameCycle,
    nextPhase: GamePhase
) : CyclePhaseImpl(cycle, GamePhase.WAITING, nextPhase) {

    override fun doTick() {
        if (isLastTick() && frame.players.hasEnough()) {
            cycle.switchPhase(GamePhase.STARTING)
        }
    }
}