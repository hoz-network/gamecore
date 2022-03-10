package net.hoz.gamecore.core.game.spawner

import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.screamingsandals.lib.world.LocationHolder
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class GameSpawnerBuilderImplTest {

    private lateinit var emptyBuilder: GameSpawnerBuilder
    private lateinit var locationBuilder: GameSpawnerBuilder
    private lateinit var typesBuilder: GameSpawnerBuilder

    @BeforeEach
    fun setUp() {
        emptyBuilder = GameSpawnerBuilderImpl(UUID.randomUUID())

        locationBuilder = GameSpawnerBuilderImpl(UUID.randomUUID())
        locationBuilder.location = LocationHolder(0.0, 0.0, 0.0)

        typesBuilder = GameSpawnerBuilderImpl(UUID.randomUUID())
        typesBuilder.location = LocationHolder(0.0, 0.0, 0.0)
        typesBuilder.types.add(SpawnerTestUtils.prepareSpawnerType())
    }

    /**
     * Tests the result
     */
    @Test
    fun build() {
        val empty = emptyBuilder.build()

        assertNotNull(empty)
        assertTrue { empty.isFail }
        assertTrue { empty.message() == "Location is not specified." }

        val location = locationBuilder.build()
        assertNotNull(location)
        assertTrue { location.isFail }
        assertTrue { location.message() == "At-least one spawner type is required!" }

        val typesBuilder = typesBuilder.build()
        assertNotNull(typesBuilder)
        assertTrue { typesBuilder.isOk }
        assertTrue { typesBuilder.hasData() }

        val data = typesBuilder.data()
        assertNotNull(data.location)
        assertTrue { data.types().all().size == 1 }
    }
}