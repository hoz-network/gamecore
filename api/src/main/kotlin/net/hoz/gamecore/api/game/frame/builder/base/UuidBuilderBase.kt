package net.hoz.gamecore.api.game.frame.builder.base

import java.util.*

interface UuidBuilderBase<B, R> : BuilderBase<B, R, UUID> {

    /**
     * Adds new instance to this builder.
     *
     * @return false if the instance already exists
     */
    fun builder(builder: B.() -> Unit): B {
        return builder(UUID.randomUUID(), builder)
    }
}