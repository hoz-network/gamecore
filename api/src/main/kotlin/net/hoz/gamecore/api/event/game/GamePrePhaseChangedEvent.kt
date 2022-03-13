package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.kotlin.SCancellableEventKt

data class GamePrePhaseChangedEvent(
    val frame: GameFrame,
    val currentPhase: CyclePhase,
    val nextPhase: CyclePhase,
    override var cancelled: Boolean = false
) : SCancellableEventKt