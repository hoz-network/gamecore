package net.hoz.gamecore.api.game.entity

import net.hoz.gamecore.api.game.frame.GameFrame

interface GameEntity {
    fun frame(): GameFrame?
}