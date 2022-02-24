package net.hoz.gamecore.api.event.player.block

import net.hoz.gamecore.api.event.player.GamePlayerCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.block.BlockHolder
import org.screamingsandals.lib.block.state.BlockStateHolder
import org.screamingsandals.lib.item.Item
import org.screamingsandals.lib.player.PlayerWrapper


data class GamePlayerBlockPlaceEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    val playerHand: PlayerWrapper.Hand,
    val block: BlockHolder,
    val replacedBlockState: BlockStateHolder,
    val itemInHand: Item,
    val replacedBlockStates: Collection<BlockStateHolder>
) : GamePlayerCancellableEvent(player, frame) {

}