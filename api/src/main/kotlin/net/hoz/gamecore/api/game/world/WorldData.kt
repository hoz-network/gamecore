package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.Buildable
import org.screamingsandals.lib.utils.ProtoLocation
import org.screamingsandals.lib.world.LocationHolder
import org.screamingsandals.lib.world.LocationMapper

/**
 * Contains all required world data for the game.
 */
interface WorldData : ProtoWrapper<ProtoWorldData>, Buildable<WorldData, WorldData.Builder> {
    /**
     * Gets the [WorldRegenerator]
     *
     * @return the instance of world regenerator currently used.
     */
    val regenerator: WorldRegenerator

    /**
     * Gets the first border point of the world
     *
     * @return location of the first point
     */
    val border1: LocationHolder

    /**
     * Gets the second border point of the world
     *
     * @return location of the second point
     */
    val border2: LocationHolder

    /**
     * Gets the spawn location in this world
     *
     * @return location of the spawn
     */
    val spawn: LocationHolder

    /**
     * Gets the spectator location in this world
     *
     * @return spectator location if present.
     */
    val spectator: LocationHolder?

    /**
     * The world type.
     *
     * @return type of the world.
     */
    val type: ProtoWorldData.WorldType

    /**
     * Checks if the given location is in the world border
     */
    fun isInBorder(location: LocationHolder): Resultable = GameWorld.isInBorder(location, border1, border2)

    /**
     * Builder of the world data.
     */
    interface Builder : Buildable.Builder<WorldData> {
        var regenerator: WorldRegenerator
        var border1: LocationHolder?
        var border2: LocationHolder?
        var spawn: LocationHolder?
        var spectator: LocationHolder?
        var type: ProtoWorldData.WorldType?
    }

    companion object {
        /**
         * Creates new empty builder.
         *
         * @return empty builder
         */
        fun builder(): Builder = WorldDataImpl.BuilderImpl()

        /**
         * Creates new empty builder with default [ProtoWorldData.WorldType].
         *
         * @param type type of the world
         * @return empty builder with type defined.
         */
        fun builder(type: ProtoWorldData.WorldType): Builder = builder().also { it.type = type }

        /**
         * Creates new builder with default data from [ProtoWorldData].
         *
         * @param data input to get default data from
         * @return new builder
         */
        fun builder(data: ProtoWorldData): Builder {
            val border1 = LocationMapper.resolve(data.border1).orElseThrow()
            val border2 = LocationMapper.resolve(data.border2).orElseThrow()
            val spawn = LocationMapper.resolve(data.spawn).orElseThrow()
            val spectator = if (data.spectator != ProtoLocation.getDefaultInstance()) LocationMapper.resolve(data.spectator).orElseThrow() else null
            val type = data.type

            return WorldDataImpl.BuilderImpl(WorldRegenerator.regenerator(), border1, border2, spawn, spectator, type)
        }
    }
}