package net.hoz.gamecore.api.game.frame

import net.hoz.gamecore.api.game.entity.GameEntity
import org.screamingsandals.lib.entity.EntityBasic

/**
 * A manager for [GameEntity] in the [GameFrame].
 */
interface FrameEntities {
    /**
     * List of all entities present in the game.
     */
    val entities: Map<Int, GameEntity>

    /**
     * Adds new entity into the game.
     */
    fun add(entity: GameEntity)

    fun remove(entity: GameEntity)

    fun has(entity: EntityBasic): Boolean

    fun removeAll()
}