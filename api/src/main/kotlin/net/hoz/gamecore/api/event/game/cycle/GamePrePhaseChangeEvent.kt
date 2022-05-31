package net.hoz.gamecore.api.event.game.cycle

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.kotlin.SCancellableEventKt

data class GamePrePhaseChangeEvent(
    val frame: GameFrame,
    val currentPhase: CyclePhase?,
    val nextPhase: GamePhase,
    override var cancelled: Boolean = false
) : SCancellableEventKt