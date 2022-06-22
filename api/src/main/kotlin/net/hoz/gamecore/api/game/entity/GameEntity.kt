package net.hoz.gamecore.api.game.entity

import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.entity.EntityBasic

enum class EntityType {
    STORE, MONSTER, ITEM, OTHER
}

/**
 * Represents an entity in the game.
 */
open class GameEntity(
    val frame: GameFrame,
    val entity: EntityBasic,
    val type: EntityType,
    val id: Int = entity.entityId
)