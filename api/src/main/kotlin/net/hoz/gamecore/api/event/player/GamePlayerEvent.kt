package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.player.SPlayerEvent
import org.screamingsandals.lib.player.PlayerWrapper

open class GamePlayerEvent(
    open val player: GamePlayer,
    open val frame: GameFrame
) : SPlayerEvent {


    override fun player(): PlayerWrapper {
        return player
    }
}