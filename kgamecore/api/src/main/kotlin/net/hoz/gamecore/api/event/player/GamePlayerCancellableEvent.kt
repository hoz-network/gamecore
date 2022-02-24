package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.Cancellable

open class GamePlayerCancellableEvent(
    player: GamePlayer,
    frame: GameFrame
) : GamePlayerEvent(player, frame), Cancellable {
    private var cancel = false

    override fun cancelled(): Boolean {
        return cancel
    }

    override fun cancelled(cancel: Boolean) {
        this.cancel = cancel
    }
}