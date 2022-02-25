package net.hoz.gamecore.core.game.spawner

import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.core.util.GConfig
import org.screamingsandals.lib.entity.EntityItem
import org.screamingsandals.lib.entity.EntityMapper
import org.screamingsandals.lib.player.PlayerWrapper

class GameSpawnerItemsImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Items {
    private val spawnedItems: MutableMap<GameSpawnerType, MutableList<EntityItem>> = mutableMapOf()

    override fun all(): Map<GameSpawnerType, List<EntityItem>> = spawnedItems.toMap()

    override fun spawn(type: GameSpawnerType) {
        if (!spawnedItems.containsKey(type)) {
            spawnedItems[type] = mutableListOf()
        }

        if (isMaxSpawned(type)) {
            return
        }

        val remainingToSpawn = countRemainingToSpawn(type)
        var amount = type.settings().amount()
        if (amount > remainingToSpawn) {
            amount -= remainingToSpawn
        }

        val amountToSpawn = amount
        if (amountToSpawn <= 0) {
            return
        }

        if (!GConfig.SPAWNERS_ADD_ITEMS_TO_INVENTORY(spawner.frame)) {
            spawnItem(type, amountToSpawn)
            return
        }

        val targets = spawner.location().getNearbyEntitiesByClass(PlayerWrapper::class.java, 1)
        if (targets.isEmpty()) {
            spawnItem(type, amountToSpawn)
        } else {
            targets.forEach { it.playerInventory.addItem(type.item(amountToSpawn, it)) }
        }
    }

    override fun clear() {
        spawnedItems.values.forEach {
            it.forEach { that -> that.remove() }
        }
        spawnedItems.clear()
    }

    private fun isMaxSpawned(type: GameSpawnerType): Boolean {
        val items = spawnedItems[type] ?: return true
        items.removeIf { it.isDead }

        val maxSpawned = type.settings().max()
        return if (maxSpawned == -1) {
            false
        } else items.size < maxSpawned
    }

    private fun countSpawned(type: GameSpawnerType): Int {
        val items = spawnedItems[type] ?: return 0
        return items.size
    }

    private fun countRemainingToSpawn(type: GameSpawnerType): Int {
        val spawned = countSpawned(type)
        return type.settings().max() - spawned
    }

    private fun spawnItem(type: GameSpawnerType, amount: Int) {
        val spawned = EntityMapper.dropItem(type.item(amount), spawner.location()).orElseThrow()
        val items = spawnedItems[type] ?: return

        items.add(spawned)
    }

}