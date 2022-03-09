package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

class GameSpawnerBuilderImpl(
    id: UUID,
    team: String?,
    location: LocationHolder?,
    useHolograms: Boolean = true,
    useGlobalValues: Boolean = true,
    types: MutableList<GameSpawnerType> = mutableListOf()
) : GameSpawnerBuilder(id, team, location, useHolograms, useGlobalValues, types) {

    override fun build(): DataResultable<GameSpawner> {
        if (types.size < 1) {
            return DataResultable.fail("At-least one spawner type is required!")
        }
        val location = this.location ?: return DataResultable.fail("Location is not specified.")

        val spawner = GameSpawnerImpl(id, location, useHolograms, useGlobalValues)
        spawner.types().add(types)

        return DataResultable.ok(spawner)
    }
}