package net.hoz.gamecore.api.game.entity

import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.entity.EntityBasic

interface GameEntity {
    val frame: GameFrame
    val entity: EntityBasic
    val type: Type

    enum class Type {
        STORE, MONSTER, ITEM, OTHER
    }
}