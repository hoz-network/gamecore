package net.hoz.gamecore.api.game.cycle.impl

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle

abstract class SimpleWaitingPhase(cycle: GameCycle) :
    AbstractCyclePhase(cycle, GamePhase.WAITING, GamePhase.STARTING) {

    override fun doPreTick(): Boolean {
        return if (frame.players.count() > 0) {
            super.doPreTick()
        } else {
            false
        }
    }

    override fun doTick() {
        if (isLastTick() && frame.players.hasEnough()) {
            cycle.switchPhase(GamePhase.STARTING)
        }
    }
}