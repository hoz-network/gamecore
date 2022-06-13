package net.hoz.gamecore.api.event.player.resource

import net.hoz.gamecore.api.event.player.GamePlayerCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.entity.EntityItem
import org.screamingsandals.lib.spectator.Component
import org.screamingsandals.lib.utils.ObjectLink

data class GamePlayerResourcePickEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    val item: EntityItem,
    private val displayName: ObjectLink<Component>
) : GamePlayerCancellableEvent(player, frame) {
    fun getDisplayName(): Component = displayName.get()
}