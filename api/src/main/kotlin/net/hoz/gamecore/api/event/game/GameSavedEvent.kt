package net.hoz.gamecore.api.event.game

import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.event.SAsyncEvent

data class GameSavedEvent(
    val frame: GameFrame,
) : SAsyncEvent