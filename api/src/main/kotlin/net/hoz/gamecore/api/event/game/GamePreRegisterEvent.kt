package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.kotlin.SCancellableEventKt

data class GamePreRegisterEvent(
    val frame: GameFrame,
    override var cancelled: Boolean = false
) : SCancellableEventKt