package net.hoz.gamecore.api.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.game.team.GameTeam

/**
 * Base for building spawners, teams, etc.
 *
 * @param <B> instance for what to build
 * @param <R> result
 * @param <ID> ID of the instance
 */
interface BuilderBase<B, R, ID> {
    /**
     * All instances that are in this builder.
     *
     * @return map of instances
     */
    fun all(): Map<ID, B>

    /**
     * Tries to find given instance by name in this builder.
     *
     * @param id) id of the instance
     * @return instance if present.
     */
    fun find(id: ID): B?

    /**
     * Checks if this builder has an instance with given name.
     *
     * @param id id to find for
     * @return true if the builder has an instance
     */
    fun has(id: ID): Boolean

    /**
     * Adds new instance to this builder.
     *
     * @return false if the instance already exists
     */
    fun add(id: ID, builder: B.() -> Unit): B

    /**
     * Removes given instance from this builder.
     *
     * @param id id of the builder
     * @return false if the instance never existed
     */
    fun remove(id: ID): Boolean

    fun build(): Map<ID, DataResultable<GameTeam>>
}