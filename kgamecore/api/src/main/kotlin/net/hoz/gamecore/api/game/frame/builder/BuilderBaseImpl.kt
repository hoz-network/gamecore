package net.hoz.gamecore.api.game.frame.builder

abstract class BuilderBaseImpl<T, ID>(
    protected val instances: MutableMap<ID, T> = mutableMapOf()
) : BuilderBase<T, ID> {
    override fun all(): Map<ID, T> {
        return instances
    }

    override fun remove(id: ID): Boolean {
        return instances.remove(id) != null
    }

    override fun add(id: ID, builder: T): BuilderBase<T, ID> {
        instances[id] = builder
        return this
    }

    override fun has(id: ID): Boolean {
        return instances.containsKey(id)
    }

    override fun find(id: ID): T? {
        return instances[id]
    }

}
