package net.hoz.gamecore.api.event.store

import net.hoz.api.data.game.StoreUpgrade
import org.screamingsandals.lib.kotlin.SCancellableEventKt
import org.screamingsandals.simpleinventories.events.OnTradeEvent

data class GameStoreUpgradeBuyEvent(
    val source: OnTradeEvent,
    val item: StoreUpgrade,
    override var cancelled: Boolean = false
) : SCancellableEventKt
