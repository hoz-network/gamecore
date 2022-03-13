package net.hoz.gamecore.core.listener.chat

import net.hoz.gamecore.api.event.SEventHandlerFactory
import net.hoz.gamecore.api.event.player.chat.GamePlayerChatEvent
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.EventPriority
import org.screamingsandals.lib.event.player.SPlayerChatEvent
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.utils.ObjectLink

class SPlayerChatEventListener : SEventHandlerFactory<GamePlayerChatEvent, SPlayerChatEvent>(
    GamePlayerChatEvent::class.java,
    SPlayerChatEvent::class.java
) {
    override fun wrapEvent(event: SPlayerChatEvent, priority: EventPriority): GamePlayerChatEvent? {
        val gamePlayer = event.player().unwrap(GamePlayer::class)
        val frame = gamePlayer.frame ?: return null

        return GamePlayerChatEvent(
            gamePlayer,
            frame,
            ObjectLink.of({ event.message() }, { event.message(it) }),
            ObjectLink.of({ event.format() }, { event.message(it) }),
            event.recipients()
        )
    }
}
