package net.hoz.gamecore.api.event.store

import net.hoz.api.data.game.StoreItem
import org.screamingsandals.lib.kotlin.SCancellableEventKt
import org.screamingsandals.simpleinventories.events.ItemRenderEvent

data class StoreCategoryItemRenderEvent(
    val source: ItemRenderEvent,
    val item: StoreItem,
    override var cancelled: Boolean = false
): SCancellableEventKt
