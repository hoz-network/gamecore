package net.hoz.gamecore.api.game.store

import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.StoreCategory
import net.hoz.api.data.game.StoreItem
import net.hoz.api.data.game.StoreQuickBuySlot
import net.hoz.api.data.game.StoreUpgrade
import net.hoz.gamecore.api.game.player.GamePlayer

interface StoreInventory {
    /**
     * The chosen one for whom this inventory is created. Whoosh.
     *
     * @return [GamePlayer] the "owner" of this inventory.
     */
    fun viewer(): GamePlayer

    /**
     * Checks whether the player is viewing this inventory.
     *
     * @return true if the player is viewing this inventory.
     */
    fun viewing(): Boolean

    /**
     * Currently selected category.
     *
     * @return [StoreCategory].
     */
    fun selectedCategory(): StoreCategory?

    fun open(): Resultable

    fun generate(): Resultable

    /**
     * Resolves a [StoreCategory] from given name.
     *
     * @param name
     * @return
     */
    fun category(name: String): StoreCategory?
    fun categoryItem(name: String): StoreCategory?
    fun item(name: String): StoreItem?
    fun upgrade(name: String): StoreUpgrade?
    fun quickBuySlot(name: String): StoreQuickBuySlot?

    companion object {
        const val ITEM_NAME = "gamecore-item"
        const val UPGRADE_NAME = "gamecore-upgrade"
        const val TIER_NAME = "gamecore-tier"
        const val CATEGORY_NAME = "gamecore-category"
    }
}