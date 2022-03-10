package net.hoz.gamecore.api.event.player.block

import net.hoz.gamecore.api.event.player.GamePlayerCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.block.BlockHolder
import org.screamingsandals.lib.utils.ObjectLink

data class GamePlayerBlockBreakEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    val block: BlockHolder,
    val dropItems: ObjectLink<Boolean>
) : GamePlayerCancellableEvent(player, frame)