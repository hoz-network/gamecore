package net.hoz.gamecore.api.game.frame.builder.base

import com.iamceph.resulter.core.DataResultable

/**
 * Base for building spawners, teams, etc.
 *
 * [B] - builder type
 * [R] - type of result after building
 * [ID] - id type of the builder
 */
interface BuilderBase<B, R, ID> {
    val keys: Set<ID>
        get() = all().keys
    val values: Collection<B>
        get() = all().values

    /**
     * All builders created.
     */
    fun all(): Map<ID, B>

    /**
     * Creates new builder by [ID] IF NOT ALREADY EXISTS.
     */
    fun builder(id: ID, block: B.() -> Unit): B

    /**
     * Tries to find builder by [ID].
     */
    fun find(id: ID): B?

    /**
     * Checks if builder with [ID] already exists
     */
    fun has(id: ID): Boolean

    /**
     * Removes builder with [ID].
     */
    fun remove(id: ID): Boolean

    /**
     * Builds all builders.
     */
    fun build(): Map<ID, DataResultable<R>>
}