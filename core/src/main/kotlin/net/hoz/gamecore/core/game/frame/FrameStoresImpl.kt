package net.hoz.gamecore.core.game.frame

import net.hoz.gamecore.api.game.frame.FrameStores
import net.hoz.gamecore.api.game.frame.GameFrame

open class FrameStoresImpl(
    protected val frame: GameFrame
) : FrameStores {

    override fun count(): Int {
        TODO("Not yet implemented")
    }
}