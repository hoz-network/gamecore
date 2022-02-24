package net.hoz.gamecore.core.game.frame.builder

import net.hoz.gamecore.api.game.frame.builder.BuilderBase

abstract class SimpleBuilderBase<B, R, ID> : BuilderBase<B, R, ID> {
    protected val builders: MutableMap<ID, B> = mutableMapOf()

    override fun all(): Map<ID, B> = builders

    override fun remove(id: ID): Boolean = builders.remove(id) != null

    override fun has(id: ID): Boolean = builders[id] != null

    override fun find(id: ID): B? = builders[id]
}