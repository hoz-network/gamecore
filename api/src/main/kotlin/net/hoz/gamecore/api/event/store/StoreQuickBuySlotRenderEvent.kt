package net.hoz.gamecore.api.event.store

import net.hoz.api.data.game.StoreQuickBuySlot
import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.simpleinventories.events.ItemRenderEvent

data class StoreQuickBuySlotRenderEvent(
    val source: ItemRenderEvent,
    var quickBuySlotItem: StoreQuickBuySlot
) : SEvent
