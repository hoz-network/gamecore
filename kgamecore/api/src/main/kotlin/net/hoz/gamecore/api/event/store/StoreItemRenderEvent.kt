package net.hoz.gamecore.api.event.store

import net.hoz.api.data.game.StoreItem
import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.simpleinventories.events.ItemRenderEvent

data class StoreItemRenderEvent(
    val source: ItemRenderEvent,
    var item: StoreItem
): SEvent
