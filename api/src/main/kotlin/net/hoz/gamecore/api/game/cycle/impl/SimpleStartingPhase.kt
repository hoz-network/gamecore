package net.hoz.gamecore.api.game.cycle.impl

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle

abstract class SimpleStartingPhase(cycle: GameCycle) :
    AbstractCyclePhase(cycle, GamePhase.STARTING, GamePhase.IN_GAME) {

    override fun doPreTick(): Boolean {
        if (!frame.players.hasEnough()) {
            //TODO: it should not tick, update visuals here
            //that we dont have enough players
            return false
        }

        return super.doPreTick()
    }
}