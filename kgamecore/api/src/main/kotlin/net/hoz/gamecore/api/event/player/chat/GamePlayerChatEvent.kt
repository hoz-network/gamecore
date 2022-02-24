package net.hoz.gamecore.api.event.player.chat

import net.hoz.gamecore.api.event.player.GamePlayerCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.utils.ObjectLink


data class GamePlayerChatEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    var message: ObjectLink<String>,
    var format: ObjectLink<String>,
    val recipients: MutableCollection<PlayerWrapper>
) : GamePlayerCancellableEvent(player, frame) {

}