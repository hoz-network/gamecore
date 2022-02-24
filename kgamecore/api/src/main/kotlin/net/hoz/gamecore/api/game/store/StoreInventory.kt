package net.hoz.gamecore.api.game.store

import net.hoz.api.data.game.StoreCategory
import net.hoz.api.data.game.StoreItem
import net.hoz.api.data.game.StoreQuickBuySlot
import net.hoz.api.data.game.StoreUpgrade
import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.simpleinventories.events.ItemRenderEvent
import org.screamingsandals.simpleinventories.events.OnTradeEvent

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
    fun open()
    fun generate()

    /**
     * Resolves a [StoreCategory] from given name.
     *
     * @param name
     * @return
     */
    fun category(name: String?): StoreCategory?
    fun categoryItem(name: String?): StoreCategory?
    fun item(name: String?): StoreItem?
    fun upgrade(name: String?): StoreUpgrade?
    fun quickBuySlot(name: String?): StoreQuickBuySlot?

    /**
     * Callback when new [StoreItem] is generating.
     * Default implementation is empty.
     *
     * @param event        the render event
     * @param originalItem item that was resolved.
     */
    fun onItemGenerate(event: ItemRenderEvent, originalItem: StoreItem) {
        //empty - depends on the implementation
    }

    /**
     * Callback when new store category [StoreItem] is generating.
     * Default implementation is empty.
     *
     * @param event        the render event
     * @param originalItem item that was resolved.
     */
    fun onCategoryItemGenerate(event: ItemRenderEvent, originalItem: StoreItem) {
        //empty - depends on the implementation
    }

    /**
     * Callback when new [StoreUpgrade] is generating.
     * Default implementation is empty.
     *
     * @param event           the render event
     * @param originalUpgrade upgrade that was resolved.
     */
    fun onUpgradeGenerate(event: ItemRenderEvent, originalUpgrade: StoreUpgrade) {
        //empty - depends on the implementation
    }

    /**
     * Callback when new [StoreQuickBuySlot] is generating.
     * Default implementation is empty.
     *
     * @param event        the render event
     * @param originalSlot quick buy slot that was resolved.
     */
    fun doQuickBuySlotGenerate(event: ItemRenderEvent, originalSlot: StoreQuickBuySlot) {
        //empty - depends on the implementation
    }

    /**
     * Callback when player is trying to buy new item.
     *
     * @param event event
     * @param item  the item
     */
    fun doOnBuy(event: OnTradeEvent, item: StoreItem) {
        //empty - depends on the implementation
    }

    /**
     * Callback when player is trying to buy new upgrade.
     *
     * @param event   event
     * @param upgrade the upgrade
     */
    fun doOnUpgradeBuy(event: OnTradeEvent, upgrade: StoreUpgrade) {
        //empty - depends on the implementation
    }

    companion object {
        const val ITEM_NAME = "gamecore-item"
        const val UPGRADE_NAME = "gamecore-upgrade"
        const val TIER_NAME = "gamecore-tier"
        const val CATEGORY_NAME = "gamecore-category"
    }
}