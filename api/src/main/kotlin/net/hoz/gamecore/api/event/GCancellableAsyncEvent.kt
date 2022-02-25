package net.hoz.gamecore.api.event

import org.screamingsandals.lib.event.SCancellableAsyncEvent

open class GCancellableAsyncEvent : SCancellableAsyncEvent {
    private var cancel = false
    override fun cancelled(): Boolean {
        return cancel
    }

    override fun cancelled(cancel: Boolean) {
        this.cancel = cancel

    }
}