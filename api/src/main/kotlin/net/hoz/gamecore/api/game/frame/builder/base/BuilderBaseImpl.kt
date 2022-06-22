package net.hoz.gamecore.api.game.frame.builder.base

/**
 * Default implementation of the [BuilderBase].
 */
abstract class BuilderBaseImpl<B, R, ID> : BuilderBase<B, R, ID> {
    protected val builders: MutableMap<ID, B> = mutableMapOf()

    override fun all(): Map<ID, B> {
        return builders
    }

    override fun builder(id: ID, block: B.() -> Unit): B {
        val builder = builders[id]
            ?: run {
                val data = provideBuilder(id)
                builders[id] = data
                return data
            }

        builder.block()
        onModify(builder)
        return builder
    }

    override fun remove(id: ID): Boolean {
        val removed = builders.remove(id)
            ?: return false

        onRemove(removed)
        return true
    }

    override fun has(id: ID): Boolean {
        return builders.containsKey(id)
    }

    override fun find(id: ID): B? {
        return builders[id]
    }

    abstract fun provideBuilder(id: ID): B

    abstract fun onModify(builder: B)

    abstract fun onRemove(builder: B)

}
