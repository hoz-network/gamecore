package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.frame.FrameStores
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.store.GameStore

internal class FrameStoresImpl(
    private val frame: GameFrame
) : FrameStores {
    private val stores: MutableMap<String, GameStore> = mutableMapOf()

    override fun add(store: GameStore): Resultable {
        if (has(store.name())) {
            return Resultable.fail("Store already exists.")
        }

        stores[store.name()] = store
        return Resultable.ok()
    }

    override fun remove(name: String): Resultable {
        stores.remove(name)
        return Resultable.ok()
    }

    override fun add(stores: Collection<GameStore>) = stores.forEach { add(it) }
    override fun has(name: String): Boolean = stores.containsKey(name)
    override fun find(name: String): GameStore? = stores[name]
    override fun all(): List<GameStore> = stores.values.toList()
}