package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.Countable
import net.hoz.gamecore.api.game.store.GameStore

interface FrameStores : Countable {
    /**
     * Registers new [GameStore].
     * Given store's [GameFrame] is set to this instance.
     *
     * @param store new store to be added
     * @return Result of this operation.
     */
    fun add(store: GameStore): Resultable

    /**
     * Registers new [GameStore]s.
     * Given store's [GameFrame] is set to this instance.
     *
     * @param stores stores to be added
     */
    fun add(stores: Collection<GameStore>)

    /**
     * Removes store from the frame by name.
     *
     * @param name name of the store to remove
     * @return Result of this operation.
     */
    fun remove(name: String): Resultable

    /**
     * Removes store from the frame.
     *
     * @param store store to remove.
     * @return Result of this operation.
     */
    fun remove(store: GameStore): Resultable {
        return remove(store.name())
    }

    /**
     * Checks if game has this store.
     *
     * @param name name of the store to check
     * @return true if the [GameStore] is in this [GameFrame].
     */
    fun has(name: String): Boolean

    /**
     * Checks if game has this store.
     *
     * @param store store to check
     * @return true if the [GameStore] is in this [GameFrame].
     */
    fun has(store: GameStore): Boolean {
        return has(store.name())
    }

    /**
     * Finds a store by name.
     *
     * @param name name to search for
     * @return [GameStore] that has given name.
     */
    fun find(name: String): GameStore?

    /**
     * Gets all stores in this frame.
     *
     * @return all stores available.
     */
    fun all(): List<GameStore>

    override fun count(): Int = all().size
}