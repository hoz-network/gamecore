package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.event.GCancellableEvent
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.GameFrame


data class GameTickEvent(
    val frame: GameFrame,
    val cycle: GameCycle,
    val currentPhase: CyclePhase
) : GCancellableEvent()