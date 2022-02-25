package net.hoz.gamecore.api.game.frame.builder

abstract class BuilderBaseImpl<T, R, ID>(
    protected val builders: MutableMap<ID, T> = mutableMapOf()
) : BuilderBase<T, R, ID> {
    override fun all(): Map<ID, T> {
        return builders
    }

    override fun remove(id: ID): Boolean {
        return builders.remove(id) != null
    }

    override fun has(id: ID): Boolean {
        return builders.containsKey(id)
    }

    override fun find(id: ID): T? {
        return builders[id]
    }

}
