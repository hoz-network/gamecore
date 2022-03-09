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
    fun regenerator(): WorldRegenerator

    /**
     * Gets the first border point of the world
     *
     * @return location of the first point
     */
    fun border1(): LocationHolder

    /**
     * Gets the second border point of the world
     *
     * @return location of the second point
     */
    fun border2(): LocationHolder

    /**
     * Gets the spawn location in this world
     *
     * @return location of the spawn
     */
    fun spawn(): LocationHolder

    /**
     * Gets the spectator location in this world
     *
     * @return spectator location if present.
     */
    fun spectator(): LocationHolder?

    /**
     * The world type.
     *
     * @return type of the world.
     */
    fun type(): ProtoWorldData.WorldType

    /**
     * Checks if the given location is in the world border
     */
    fun isInBorder(location: LocationHolder): Resultable = GameWorld.isInBorder(location, border1(), border2())

    /**
     * Builder of the world data.
     */
    interface Builder: Buildable.Builder<WorldData> {
        /**
         * Sets new [WorldRegenerator].
         *
         * @param regenerator the new regenerator
         */
        fun regenerator(regenerator: WorldRegenerator)

        /**
         * Currently used regenerator.
         *
         * @return [WorldRegenerator].
         */
        fun regenerator(): WorldRegenerator

        /**
         * Gets the border1 location.
         *
         * @return border1 location if present.
         */
        fun border1(): LocationHolder?

        /**
         * Sets new border1 location
         *
         * @param holder new location
         */
        fun border1(holder: LocationHolder)

        /**
         * Gets the border2 location.
         *
         * @return border2 location if present.
         */
        fun border2(): LocationHolder?

        /**
         * Sets new border2 location
         *
         * @param holder new location
         */
        fun border2(holder: LocationHolder)

        /**
         * Gets the spawn location.
         *
         * @return spawn location if present.
         */
        fun spawn(): LocationHolder?

        /**
         * Sets new spawn location
         *
         * @param holder new location
         */
        fun spawn(holder: LocationHolder)

        /**
         * Gets the spectator location.
         *
         * @return spawn spectator if present.
         */
        fun spectator(): LocationHolder?

        /**
         * Sets new spectator location
         *
         * @param holder new location
         */
        fun spectator(holder: LocationHolder)
        fun type(): ProtoWorldData.WorldType?

        /**
         * Sets new world type
         *
         * @param type new type
         */
        fun type(type: ProtoWorldData.WorldType)
    }

    companion object {
        /**
         * Creates new empty builder.
         *
         * @return empty builder
         */
        fun builder(): Builder {
            return WorldDataImpl.BuilderImpl()
        }

        /**
         * Creates new empty builder with default [ProtoWorldData.WorldType].
         *
         * @param type type of the world
         * @return empty builder with type defined.
         */
        fun builder(type: ProtoWorldData.WorldType): Builder {
            val builder = WorldDataImpl.BuilderImpl()
            builder.type(type)
            return builder
        }

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
            val spectator =
                if (data.spectator != ProtoLocation.getDefaultInstance())
                    LocationMapper.resolve(data.spectator).orElseThrow()
                else null
            val type = data.type

            return WorldDataImpl.BuilderImpl(border1, border2, spawn, spectator, type)
        }
    }
}