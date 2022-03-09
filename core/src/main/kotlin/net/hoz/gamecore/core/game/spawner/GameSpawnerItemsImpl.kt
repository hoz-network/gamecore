package net.hoz.gamecore.core.game.spawner

import mu.KotlinLogging
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.core.util.GConfig
import org.screamingsandals.lib.entity.EntityItem
import org.screamingsandals.lib.entity.EntityMapper
import org.screamingsandals.lib.player.PlayerWrapper

open class GameSpawnerItemsImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Items {
    private val log = KotlinLogging.logger { }
    protected val spawnedItems: MutableMap<GameSpawnerType, MutableList<EntityItem>> = mutableMapOf()

    override fun all(): Map<GameSpawnerType, List<EntityItem>> = spawnedItems.toMap()

    override fun spawn(type: GameSpawnerType) {
        if (!spawnedItems.containsKey(type)) {
            log.debug { "Type ${type.name()} is not registered in spawned items, registering." }
            spawnedItems[type] = mutableListOf()
        }

        if (isMaxSpawned(type)) {
            log.debug { "Spawner already spawned maximum, doing nothing." }
            return
        }

        val remainingToSpawn = countRemainingToSpawn(type)
        var amount = type.settings().amount()

        if (remainingToSpawn == -1) {
            log.debug { "Remaining to spawn is unlimited." }
        } else if (amount > remainingToSpawn) {
            val originalAmount = amount
            amount -= remainingToSpawn
            log.debug { "Counting remaining to spawn, original[$originalAmount], new[$amount]" }
        }

        val amountToSpawn = amount
        if (amountToSpawn <= 0) {
            log.debug { "Amount to spawn is <= to 0, doing nothing." }
            return
        }

        if (!isAddToInventory()) {
            log.debug { "Adding items to inventory is disabled, spawning.." }
            spawnItem(type, amountToSpawn)
            return
        }

        val targets = spawner.location().getNearbyEntitiesByClass(PlayerWrapper::class.java, 1)
        if (targets.isEmpty()) {
            log.debug { "No players are nearby, spawning on the ground.." }
            spawnItem(type, amountToSpawn)
        } else {
            targets.forEach {
                log.debug { "Adding item[${type.name()}] to inventory of player[${it.uuid}]" }
                it.playerInventory.addItem(type.item(amountToSpawn, it))
            }
        }
    }

    override fun clear() {
        log.debug { "Trying to clear all spawned items." }
        spawnedItems.values.forEach {
            it.forEach { that -> that.remove() }
        }
        spawnedItems.clear()
    }

    open fun isAddToInventory(): Boolean {
        return GConfig.SPAWNERS_ADD_ITEMS_TO_INVENTORY(spawner.frame)
    }

    open fun isMaxSpawned(type: GameSpawnerType): Boolean {
        val items = spawnedItems[type] ?: return true
        items.removeIf { it.isDead }

        val maxSpawned = type.settings().max()
        return if (maxSpawned == -1) {
            false
        } else items.size < maxSpawned
    }

    open fun countSpawned(type: GameSpawnerType): Int {
        val items = spawnedItems[type] ?: return 0
        return items.size
    }

    open fun countRemainingToSpawn(type: GameSpawnerType): Int {
        val spawned = countSpawned(type)
        val maxSpawned = type.settings().max()
        return if (maxSpawned == -1) {
            return -1
        } else type.settings().max() - spawned
    }

    open fun spawnItem(type: GameSpawnerType, amount: Int) {
        val spawned = EntityMapper.dropItem(type.item(amount), spawner.location()).orElseThrow()
        val items = spawnedItems[type] ?: return

        items.add(spawned)
    }

}