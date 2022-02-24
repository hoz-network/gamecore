package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.event.GCancellableEvent
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.frame.GameFrame

data class GamePrePhaseChangeEvent(
    val frame: GameFrame,
    val currentPhase: CyclePhase?,
    val nextPhase: CyclePhase
) : GCancellableEvent()