package net.hoz.gamecore.api.game.frame

import net.hoz.gamecore.api.game.entity.GameEntity
import org.screamingsandals.lib.entity.EntityBasic

interface FrameEntities {
    val entities: List<GameEntity>

    fun add(entity: GameEntity)

    fun remove(entity: GameEntity)

    fun has(entity: EntityBasic): Boolean

    fun removeAll()
}