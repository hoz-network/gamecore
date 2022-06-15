package net.hoz.gamecore.api.game.frame.builder.base

import com.iamceph.resulter.core.DataResultable

/**
 * Base for building spawners, teams, etc.
 *
 * @param <B> builder
 * @param <R> result
 * @param <ID> ID of the instance
 */
interface BuilderBase<B, R, ID> {
    val keys: Set<ID>
        get() = all().keys
    val values: Collection<B>
        get() = all().values

    /**
     * All instances that are in this builder.
     *
     * @return map of instances
     */
    fun all(): Map<ID, B>

    /**
     * Adds new instance to this builder.
     *
     * @return false if the instance already exists
     */
    fun builder(id: ID, block: B.() -> Unit): B

    /**
     * Tries to find given instance by name in this builder.
     *
     * @param id) id of the instance
     * @return instance if present.
     */
    fun find(id: ID): B?

    /**
     * Checks if this builder has an instance with given name.
     *0
     * @param id id to find for
     * @return true if the builder has an instance
     */
    fun has(id: ID): Boolean

    /**
     * Removes given instance from this builder.
     *
     * @param id id of the builder
     * @return false if the instance never existed
     */
    fun remove(id: ID): Boolean

    fun build(): Map<ID, DataResultable<R>>
}