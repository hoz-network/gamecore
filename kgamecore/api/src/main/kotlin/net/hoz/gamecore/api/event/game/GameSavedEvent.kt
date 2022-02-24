package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.event.GCancellableAsyncEvent
import net.hoz.gamecore.api.game.frame.GameFrame

data class GameSavedEvent(val frame: GameFrame) : GCancellableAsyncEvent()