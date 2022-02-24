package net.hoz.gamecore.core.game.spawner

import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import org.screamingsandals.lib.entity.EntityItem

class GameSpawnerItemsImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Items {
    private val spawnedEntities: MutableList<EntityItem> = mutableListOf()

    override fun all(): List<EntityItem> = spawnedEntities.toList()

    override fun spawn(type: GameSpawnerType) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        spawnedEntities.forEach {
            it.remove()
        }
        spawnedEntities.clear()
    }
}