package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.DataResultable
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
) {

    /**
     * Builds the actual [GameSpawner].
     *
     * @return result of the operation.
     */
    abstract fun build(): DataResultable<GameSpawner>
}