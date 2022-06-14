package net.hoz.gamecore.api.service

import net.hoz.gamecore.api.game.cycle.CyclePhase

interface PhaseManager {

    fun all(): List<CyclePhase>

    fun register(phase: CyclePhase)
}