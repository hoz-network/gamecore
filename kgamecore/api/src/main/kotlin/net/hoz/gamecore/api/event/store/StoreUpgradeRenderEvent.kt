package net.hoz.gamecore.api.event.store

import net.hoz.api.data.game.StoreUpgrade
import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.simpleinventories.events.ItemRenderEvent

data class StoreUpgradeRenderEvent(
    val source: ItemRenderEvent,
    var upgrade: StoreUpgrade
): SEvent

