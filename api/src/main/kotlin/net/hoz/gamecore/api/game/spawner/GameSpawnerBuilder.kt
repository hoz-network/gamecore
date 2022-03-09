package net.hoz.gamecore.api.game.spawner

import net.hoz.gamecore.api.Buildable
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

/**
 * Builder of GameSpawner
 */
abstract class GameSpawnerBuilder(
    val id: UUID,
    var team: String?,
    var location: LocationHolder?,
    var useHolograms: Boolean = true,
    var useGlobalValues: Boolean = true,
    var types: MutableList<GameSpawnerType> = mutableListOf(),
) : Buildable.Builder<GameSpawner>