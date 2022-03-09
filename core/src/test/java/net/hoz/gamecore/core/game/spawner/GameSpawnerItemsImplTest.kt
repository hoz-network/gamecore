package net.hoz.gamecore.core.game.spawner

import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.screamingsandals.lib.entity.EntityItem
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

internal class GameSpawnerItemsImplTest {
    private lateinit var items: GameSpawnerItemsImpl
    private lateinit var type: GameSpawnerType

    @BeforeEach
    fun setUp() {
        val builder = GameSpawnerBuilderImpl(UUID.randomUUID())
        builder.location = Mockito.mock(LocationHolder::class.java)

        type = SpawnerTestUtils.prepareSpawnerType()
        builder.types.add(type)

        val result = builder.build()
        if (result.isFail) {
            throw UnsupportedOperationException(result.message())
        }

        items = CustomItems(result.data() as GameSpawnerImpl)
    }

    @Test
    fun all() {
        val empty = items.all()
        assertNotNull(empty)
        assertTrue { empty.isEmpty() }

        items.spawn(type)

        val one = items.all()
        assertTrue { one.size == 1 }
    }

    @Test
    fun spawn() {
    }

    @Test
    fun clear() {
    }

    @Test
    fun isAddToInventory() {

    }

    @Test
    fun isMaxSpawned() {
    }

    @Test
    fun countSpawned() {
    }

    @Test
    fun countRemainingToSpawn() {
        val spawned = items.countSpawned(type)
    }

    @Test
    fun spawnItem() {
    }

    class CustomItems(spawner: GameSpawner) : GameSpawnerItemsImpl(spawner as GameSpawnerImpl) {
        override fun spawnItem(type: GameSpawnerType, amount: Int) {
            val mock = Mockito.mock(EntityItem::class.java)
            val items = spawnedItems[type] ?: return
            items.add(mock)
        }

    }
}