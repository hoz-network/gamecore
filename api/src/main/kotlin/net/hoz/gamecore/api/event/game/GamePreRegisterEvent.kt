package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.event.GCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame

data class GamePreRegisterEvent(val frame: GameFrame) : GCancellableEvent()