package net.hoz.gamecore.api.game.cycle.impl

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle

abstract class SimpleStartingPhase(cycle: GameCycle) :
    AbstractCyclePhase(cycle, GamePhase.STARTING, GamePhase.IN_GAME) {

    override fun doPreTick(): Boolean {
        if (!frame.players.hasEnough()) {
            cycle.switchPhase(GamePhase.WAITING)
            return false
        }

        return super.doPreTick()
    }
}