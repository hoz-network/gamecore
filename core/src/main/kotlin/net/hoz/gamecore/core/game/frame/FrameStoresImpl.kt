package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.frame.FrameStores
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.store.GameStore

internal class FrameStoresImpl(
    private val frame: GameFrame
) : FrameStores {
    override fun add(store: GameStore): Resultable {
        TODO("Not yet implemented")
    }

    override fun add(stores: Collection<GameStore>) {
        TODO("Not yet implemented")
    }

    override fun remove(name: String): Resultable {
        TODO("Not yet implemented")
    }

    override fun has(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun find(name: String): GameStore? {
        TODO("Not yet implemented")
    }

    override fun all(): List<GameStore> {
        TODO("Not yet implemented")
    }

    override fun count(): Int {
        TODO("Not yet implemented")
    }
}