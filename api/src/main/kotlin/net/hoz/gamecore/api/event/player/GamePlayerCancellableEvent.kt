package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.kotlin.SCancellableKt

open class GamePlayerCancellableEvent(
    player: GamePlayer,
    frame: GameFrame,
    override var cancelled: Boolean = false
) : GamePlayerEvent(player, frame), SCancellableKt