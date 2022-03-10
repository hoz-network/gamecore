package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.kotlin.SCancellableKt
import org.screamingsandals.lib.lang.Translation

open class GamePlayerReasonedEvent(
    player: GamePlayer,
    frame: GameFrame,
    var reason: Translation? = null
) : GamePlayerCancellableEvent(player, frame), SCancellableKt