package net.hoz.gamecore.core.game.frame

import net.hoz.gamecore.api.game.frame.FrameSpawners
import net.hoz.gamecore.api.game.frame.GameFrame

open class FrameSpawnersImpl(
    protected val frame: GameFrame
) : FrameSpawners {
    override fun count(): Int {
        TODO("Not yet implemented")
    }
}