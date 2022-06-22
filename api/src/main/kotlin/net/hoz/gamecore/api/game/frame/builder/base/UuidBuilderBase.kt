package net.hoz.gamecore.api.game.frame.builder.base

import java.util.*

/**
 * Extension for the [BuilderBase]
 */
interface UuidBuilderBase<B, R> : BuilderBase<B, R, UUID> {

    /**
     * Creates new builder with [UUID.randomUUID] IF NOT ALREADY EXISTS.
     */
    fun builder(builder: B.() -> Unit): B {
        return builder(UUID.randomUUID(), builder)
    }
}