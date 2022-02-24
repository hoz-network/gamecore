package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType

class GameSpawnerTypesImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Types {
    private val types: MutableMap<String, GameSpawnerType> = mutableMapOf()

    override fun all(): Map<String, GameSpawnerType> = types

    override fun has(type: GameSpawnerType): Boolean {
        TODO("Not yet implemented")
    }

    override fun has(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(type: GameSpawnerType): Resultable {
        TODO("Not yet implemented")
    }

    override fun remove(type: GameSpawnerType): Resultable {
        TODO("Not yet implemented")
    }

    override fun remove(name: String): Resultable {
        TODO("Not yet implemented")
    }
}