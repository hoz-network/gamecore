package net.hoz.gamecore.core.game.store

import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.kotlin.resultable
import mu.KotlinLogging
import net.hoz.api.data.game.*
import net.hoz.api.data.game.StoreUpgrade.Tier
import net.hoz.gamecore.api.event.store.*
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.StoreInventory
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.item.Item
import org.screamingsandals.lib.item.builder.ItemFactory
import org.screamingsandals.lib.item.meta.EnchantmentHolder
import org.screamingsandals.lib.kotlin.fire
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.utils.Pair
import org.screamingsandals.simpleinventories.SimpleInventoriesCore
import org.screamingsandals.simpleinventories.builder.CategoryBuilder
import org.screamingsandals.simpleinventories.events.ItemRenderEvent
import org.screamingsandals.simpleinventories.events.OnTradeEvent
import org.screamingsandals.simpleinventories.events.PreClickEvent
import org.screamingsandals.simpleinventories.events.SubInventoryCloseEvent
import org.screamingsandals.simpleinventories.inventory.GenericItemInfo
import org.screamingsandals.simpleinventories.inventory.InventorySet
import org.screamingsandals.simpleinventories.inventory.Price
import org.screamingsandals.simpleinventories.inventory.SubInventory
import org.spongepowered.configurate.BasicConfigurationNode
import org.spongepowered.configurate.ConfigurationNode

class StoreInventoryImpl(
    private val viewer: GamePlayer,
    private val frame: GameFrame,
    private var holder: StoreHolder,
    private var team: GameTeam?
) : StoreInventory {
    private val log = KotlinLogging.logger { }

    private val upgrades: MutableMap<String, Pair<StoreUpgrade, GenericItemInfo>> = mutableMapOf()
    private val items: MutableMap<String, Pair<StoreItem, GenericItemInfo>> = mutableMapOf()
    private val quickBuySlots: MutableMap<String, Pair<StoreQuickBuySlot, GenericItemInfo>> = mutableMapOf()
    private val categories: MutableMap<String, Pair<StoreCategory, SubInventory>> = mutableMapOf()
    private val categoryItems: MutableMap<String, Pair<StoreCategory, GenericItemInfo>> = mutableMapOf()

    private var isOpened = false
    private var store: GameStore? = null
    private var selectedCategory: StoreCategory? = null
    private var inventorySet: InventorySet? = null

    override fun viewer(): GamePlayer = viewer

    override fun viewing(): Boolean = isOpened

    override fun selectedCategory(): StoreCategory? = selectedCategory

    override fun open(): Resultable {
        val inventory = inventorySet ?: return Resultable.fail("Inventory is not generated!")

        inventory.openInventory(viewer)
        isOpened = true
        return Resultable.ok()
    }

    override fun generate(): Resultable = resultable {
        inventorySet = SimpleInventoriesCore.builder()
            .animationsEnabled(true)
            .genericShop(true)
            .categoryOptions {
                it.prefix(Message.of(holder.name).getForJoined(viewer))
                    .showPageNumber(false)
                    .renderFooterStart(55)
                    .renderHeaderStart(55)
                    .rows(holder.rows)
                    .renderActualRows(holder.rows)
                if (holder.decorativeItem != StoreItem.getDefaultInstance()) {
                    it.emptySlotItem(ItemFactory.readStack(holder.decorativeItem))
                }
            }
            .preClick { onPreClick(it) }
            .render { onRender(it) }
            .buy { buyBuilder(it) }
            .close { closeBuilder(it) }
            .call { categoryBuilder(it) }
            .process()
            .inventorySet
    }

    override fun category(name: String): StoreCategory? {
        val category = categories[name] ?: return null
        return category.first
    }

    override fun categoryItem(name: String): StoreCategory? {
        val category = categoryItems[name] ?: return null
        return category.first
    }

    override fun item(name: String): StoreItem? {
        val item = items[name] ?: return null
        return item.first
    }

    override fun upgrade(name: String): StoreUpgrade? {
        val upgrade = upgrades[name] ?: return null
        return upgrade.first
    }

    override fun quickBuySlot(name: String): StoreQuickBuySlot? {
        val quickBuySlot = quickBuySlots[name] ?: return null
        return quickBuySlot.first
    }

    private fun onPreClick(event: PreClickEvent) {
        val item = event.item ?: return
        if (item.properties.isEmpty()) {
            log.debug { "No properties found for event: $event" }
            return
        }

        item.getFirstPropertyByName(StoreInventory.CATEGORY_NAME)
            .ifPresent {
                val data = it.propertyData.node("identifier").string
                if (data == null) {
                    log.debug { "No data found in preClickEvent" }
                    return@ifPresent
                }

                selectedCategory = category(data) ?: return@ifPresent
                log.debug { "Marked category as selected: $selectedCategory" }
            }
    }

    private fun onRender(event: ItemRenderEvent) {
        val itemInfo = event.item ?: return
        if (!itemInfo.hasProperties()) {
            log.debug { "No properties found for event: $event" }
            return
        }

        itemInfo.getFirstPropertyByName(StoreInventory.CATEGORY_NAME)
            .ifPresent {
                val data = it.propertyData.node("identifier").string
                if (data == null) {
                    log.debug { "No data found in ItemRenderEvent" }
                    return@ifPresent
                }

                val selectedCategory = this.selectedCategory
                val category = category(data) ?: return@ifPresent

                if (StoreCategoryItemRenderEvent(event, category.categoryItem).fire().cancelled) {
                    log.debug { "Render cancelled by event" }
                    return@ifPresent
                }

                if (selectedCategory != null
                    && (selectedCategory.categoryItem.name == category.categoryItem.name)
                ) {
                    log.trace("Marking as selected: {}", category.categoryItem.name)
                    //TODO: mark the item as selected

                    val currentItem = event.item.stack
                    event.item.stack = markItemAsSelected(currentItem)
                }

                val item = item(data)
                if (item != null) {
                    StoreItemRenderEvent(event, item).fire()
                }
                val upgrade = upgrade(data)
                if (upgrade != null) {
                    StoreUpgradeRenderEvent(event, upgrade).fire()
                }
                val quickBuySlot = quickBuySlot(data)
                if (quickBuySlot != null) {
                    StoreQuickBuySlotRenderEvent(event, quickBuySlot).fire()
                }
            }
    }

    private fun buyBuilder(event: OnTradeEvent) {
        event.properties
            .forEach {
                val data = it.propertyData.node("identifier").string
                    ?: run {
                        log.debug { "No data found in OnTradeEvent" }
                        return
                    }

                val item = item(data)
                if (item != null) {
                    GameStoreBuyEvent(event, item).fire()
                }

                val upgrade = upgrade(data)
                if (upgrade != null) {
                    GameStoreUpgradeBuyEvent(event, upgrade).fire()
                }
                val quickBuySlot = quickBuySlot(data)
                if (quickBuySlot != null) {
                    val itemToHandle = items[quickBuySlot.itemName] ?: return
                    GameStoreBuyEvent(event, itemToHandle.first).fire()
                }
            }
    }

    private fun closeBuilder(event: SubInventoryCloseEvent) {
        val inventorySet = this.inventorySet ?: return
        if (event.subInventory == inventorySet.mainSubInventory) {
            log.trace("Closing..")
            selectedCategory = null
            isOpened = false
        }
    }

    private fun categoryBuilder(builder: CategoryBuilder) {
        //list all available categories
        val availableCategories = holder.categoriesList
        //modify the name to have some Misat black magic
        val modifiedCategoryNames = availableCategories
            .map { it.categoryItem.name }
            .map { "$$it" }
            .toMutableList()

        //builder main category too
        modifiedCategoryNames.add("main")

        //prepare categories and build items for them
        availableCategories.forEach {
            val categoryName = it.categoryItem.name
            val subCategory = builder.hidden(categoryName) { builder ->
                buildItems(it, builder)
            }.subInventory

            this.categories[categoryName] = Pair.of(it, subCategory)
        }

        //build category items for the main menu
        builder.insert(modifiedCategoryNames) { categoryInfo ->
            availableCategories.forEach { category ->
                categoryInfo.item(ItemFactory.readStack(category.categoryItem).orElseThrow()) { itemBuilder ->
                    val itemLocation = category.categoryItem.location
                    val itemName = category.categoryItem.name

                    itemBuilder.column(itemLocation.column)
                        .row(itemLocation.row)
                        .locate("$${itemName}")
                        .property(buildCategoryProperty(category))

                    this.categoryItems[itemName] = Pair.of(category, itemBuilder.itemInfo)
                }
            }
        }
    }

    private fun buildItems(category: StoreCategory, builder: CategoryBuilder) {
        //prepare items
        category.itemsList.forEach { item ->
            buildCategoryItems(item, builder)
        }

        //prepare upgrades
        category.upgradesList.forEach { upgrade ->
            buildCategoryUpgrades(upgrade, builder)
        }

        //TODO: handle quick buy slots here
    }

    private fun buildCategoryItems(item: StoreItem, builder: CategoryBuilder) {
        //resolve the item from proto
        builder.item(ItemFactory.readStack(item).orElseThrow()) { itemInfo ->
            //set location of the item
            val location = item.location
            itemInfo.column(location.column)
                .row(location.row)
                .property(buildItemProperty(item))

            //builder prices from proto
            item.pricesList.forEach { price ->
                itemInfo.price(Price.of(price.count, price.currencyName))
            }

            //item done, yay
            items[item.name] = Pair.of(item, itemInfo.itemInfo)
        }
    }

    private fun buildCategoryUpgrades(upgrade: StoreUpgrade, builder: CategoryBuilder) {
        val tiers = upgrade.tiersList
        if (tiers.isEmpty()) {
            return
        }

        //only build first tier, the rest is in the future.
        //we will cross that bridge when we get there :>
        val tier = tiers.first()
        builder.item(ItemFactory.readStack(tier.item).orElseThrow()) { itemInfo ->
            val location = tier.item.location
            itemInfo.column(location.column)
                .row(location.row)

            buildUpgradeProperty(upgrade, tier).forEach { itemInfo.property(it) }
            upgrades[upgrade.name] = Pair.of(upgrade, itemInfo.itemInfo)
        }
    }

    private fun buildCategoryProperty(category: StoreCategory): ConfigurationNode {
        val node = BasicConfigurationNode.root()
        try {
            node.node("name").set(StoreInventory.CATEGORY_NAME)
            node.node("identifier").set(category.categoryItem.name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        log.trace("Node name: {}", node.node("name").string)
        log.trace("Data: {}", node.string)
        return node
    }

    private fun buildItemProperty(item: StoreItem): ConfigurationNode {
        val node = BasicConfigurationNode.root()
        try {
            node.node("name").set(StoreInventory.ITEM_NAME)
            node.node("identifier").set(item.name)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return node
    }

    private fun buildUpgradeProperty(upgrade: StoreUpgrade, tier: Tier): List<ConfigurationNode> {
        try {
            val upgradeNode = BasicConfigurationNode.root().node("name").set(StoreInventory.UPGRADE_NAME)
            val tierNode = BasicConfigurationNode.root().node("name").set(StoreInventory.TIER_NAME)
            upgradeNode.set(upgrade.name)
            tierNode.set(tier.position)
            return listOf(upgradeNode, tierNode)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return listOf()
    }

    private fun markItemAsSelected(item: Item): Item {
        return item.withEnchantment(EnchantmentHolder.of("LUCK"))
    }
}