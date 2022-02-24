package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.event.SEvent

data class GameDisabledEvent(val frame: GameFrame) : SEvent