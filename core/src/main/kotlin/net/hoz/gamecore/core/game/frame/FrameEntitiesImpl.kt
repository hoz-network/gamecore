package net.hoz.gamecore.core.game.frame

import net.hoz.gamecore.api.game.entity.GameEntity
import net.hoz.gamecore.api.game.frame.FrameEntities
import org.screamingsandals.lib.entity.EntityBasic

class FrameEntitiesImpl : FrameEntities {
    override val entities: MutableList<GameEntity> = mutableListOf()

    override fun add(entity: GameEntity) {
        if (entities.contains(entity)) {
            return
        }
        entities.add(entity)
    }

    override fun remove(entity: GameEntity) {
        entities.removeIf {
            it.entity == entity.entity
        }
        entity.entity.remove()
    }

    override fun has(entity: EntityBasic): Boolean {
        return entities.any { it.entity == entity }
    }

    override fun removeAll() {
        entities.forEach { it.entity.remove() }
        entities.clear()
    }
}