package net.hoz.gamecore.api.event.store

import net.hoz.api.data.game.StoreItem
import org.screamingsandals.lib.kotlin.SCancellableAsyncEventKt
import org.screamingsandals.simpleinventories.events.OnTradeEvent

data class GameStoreBuyEvent(
    val buyEvent: OnTradeEvent,
    val item: StoreItem,
    override var cancelled: Boolean = false
): SCancellableAsyncEventKt
