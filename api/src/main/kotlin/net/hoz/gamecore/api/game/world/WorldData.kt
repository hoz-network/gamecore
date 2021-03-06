package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.util.GUtil
import org.screamingsandals.lib.world.LocationHolder

/**
 * Contains all required world data for the game.
 */
interface WorldData
    : ProtoWrapper<ProtoWorldData>, Buildable<WorldData, WorldDataBuilder> {
    /**
     * The world type.
     *
     * @return type of the world.
     */
    val type: ProtoWorldData.WorldType

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
     * Checks if the given location is in the world border
     */
    fun isInBorder(location: LocationHolder): Resultable = GUtil.isInBorder(location, border1, border2)

    companion object {
//
        /**
         * Creates new builder with default data from [ProtoWorldData].
         *
         * @param data input to get default data from
         * @return new builder
         */
       //fun builder(data: ProtoWorldData): WorldDataBuilder {
       //    val border1 = LocationMapper.resolve(data.border1).orElseThrow()
       //    val border2 = LocationMapper.resolve(data.border2).orElseThrow()
       //    val spawn = LocationMapper.resolve(data.spawn).orElseThrow()
       //    val spectator = if (data.spectator != ProtoLocation.getDefaultInstance()) LocationMapper.resolve(data.spectator).orElseThrow() else null
       //    val type = data.type
//
       //    return WorldDataImpl.BuilderImpl(WorldRegenerator.regenerator(), border1, border2, spawn, spectator, type)
       //}
    }
}