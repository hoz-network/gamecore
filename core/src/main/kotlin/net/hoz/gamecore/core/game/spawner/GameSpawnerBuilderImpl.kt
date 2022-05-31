package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.kotlin.dataResultable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

internal class GameSpawnerBuilderImpl(
    id: UUID,
    team: String? = null,
    location: LocationHolder? = null,
    useHolograms: Boolean = true,
    useGlobalValues: Boolean = true,
    types: MutableList<GameSpawnerType> = mutableListOf()
) : GameSpawnerBuilder(id, team, location, useHolograms, useGlobalValues, types) {

    override fun build(): DataResultable<GameSpawner> {
        val location = this.location ?: return DataResultable.fail("Location is not specified.")
        if (types.size < 1) {
            return DataResultable.fail("At-least one spawner type is required!")
        }

        return dataResultable {
            GameSpawnerImpl(id, location, useHolograms, useGlobalValues)
                .also { it.types().add(types) }
        }
    }
}