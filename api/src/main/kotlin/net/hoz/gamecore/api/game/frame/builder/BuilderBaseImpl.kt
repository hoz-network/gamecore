package net.hoz.gamecore.api.game.frame.builder

abstract class BuilderBaseImpl<B, R, ID> : BuilderBase<B, R, ID> {
    protected val builders: MutableMap<ID, B> = mutableMapOf()

    override fun all(): Map<ID, B> {
        return builders
    }

    override fun add(id: ID, builder: B.() -> Unit): B {
        val data = provideBuilder(id)
        builder.invoke(data)

        builders[id] = data
        return data
    }

    override fun remove(id: ID): Boolean {
        return builders.remove(id) != null
    }

    override fun has(id: ID): Boolean {
        return builders.containsKey(id)
    }

    override fun find(id: ID): B? {
        return builders[id]
    }

    abstract fun provideBuilder(id: ID): B

}
