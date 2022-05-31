package net.hoz.gamecore.api.event.game.cycle

import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.event.SEvent

data class GamePhaseChangedEvent(
    val frame: GameFrame,
    val currentPhase: CyclePhase?,
    val previousPhase: CyclePhase?
) : SEvent