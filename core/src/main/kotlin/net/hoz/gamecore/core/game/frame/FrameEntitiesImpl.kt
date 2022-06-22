package net.hoz.gamecore.core.game.frame

import net.hoz.gamecore.api.game.entity.GameEntity
import net.hoz.gamecore.api.game.frame.FrameEntities
import org.screamingsandals.lib.entity.EntityBasic

class FrameEntitiesImpl : FrameEntities {
    override val entities: MutableMap<Int, GameEntity> = mutableMapOf()

    override fun add(entity: GameEntity) {
        if (entities.contains(entity.id)) {
            return
        }
        entities[entity.id] = entity
    }

    override fun remove(entity: GameEntity) {
        entities.remove(entity.id)?.entity?.remove()
    }

    override fun has(entity: EntityBasic): Boolean {
        return entities.contains(entity.entityId)
    }

    override fun removeAll() {
        entities.values.forEach { it.entity.remove() }
        entities.clear()
    }
}