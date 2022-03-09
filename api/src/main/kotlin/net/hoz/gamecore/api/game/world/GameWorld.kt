package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameWorld
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.util.GLangKeys
import net.hoz.netapi.client.lang.LangResultable
import org.screamingsandals.lib.world.LocationHolder

/**
 * Contains information about all required points to run the game.
 */
interface GameWorld : ProtoWrapper<ProtoGameWorld>, Buildable<GameWorld, GameWorld.Builder> {
    /**
     * Gets a [WorldData] for arena world.
     *
     * @return [WorldData] containing data for the arena.
     */
    fun arenaWorld(): WorldData

    /**
     * Gets a [WorldData] for lobby world.
     *
     * @return [WorldData] containing data for the lobby.
     */
    fun lobbyWorld(): WorldData

    /**
     * Contains custom world data.
     *
     *
     * Key: name of the world
     *
     * @return map of current available custom worlds
     */
    fun customWorlds(): Map<String, WorldData>

    /**
     * Tries to get the custom world by name.
     *
     * @param name name to get the world from
     * @return [WorldData] world if present.
     */
    fun customWorld(name: String?): WorldData?

    /**
     * The builder of the [GameWorld].
     */
    interface Builder: Buildable.Builder<GameWorld> {
        /**
         * Gets arena world.
         *
         * @return arena world if present.
         */
        fun arenaWorld(): WorldData?

        /**
         * Sets arena world
         *
         * @param data data to set
         */
        fun arenaWorld(data: WorldData)

        /**
         * Gets lobby world.
         *
         * @return arena lobby if present.
         */
        fun lobbyWorld(): WorldData?

        /**
         * Sets lobby world
         *
         * @param data data to set
         */
        fun lobbyWorld(data: WorldData)

        /**
         * Adds new custom world.
         *
         * @param name key
         * @param data value
         */
        fun addWorld(name: String, data: WorldData)

        /**
         * Removes custom world, if available.
         *
         * @param name name of the custom world (key)
         * @return true if the world was removed.
         */
        fun removeWorld(name: String): Boolean

        /**
         * Contains custom world data.
         *
         *
         * Key: name of the world
         *
         * @return map of current available custom worlds
         */
        fun customWorlds(): Map<String, WorldData>

        /**
         * Tries to get the custom world by name.
         *
         * @param name name to get the world from
         * @return [WorldData] world if present.
         */
        fun customWorld(name: String): WorldData?
    }

    companion object {
        /**
         * Creates new empty builder.
         *
         * @return empty builder
         */
        fun builder(): Builder {
            return GameWorldImpl.BuilderImpl()
        }

        /**
         * Creates new builder with default data from [ProtoGameWorld].
         *
         * @param data input to get default data from
         * @return new builder
         */
        fun builder(data: ProtoGameWorld): Builder {
            val maybeArena = WorldData.builder(data.arenaWorld).build()
            val maybeLobby = WorldData.builder(data.lobbyWorld).build()
            val maybeCustomWorlds = data.customWorldsMap
                .entries
                .associate {
                    Pair(it.key, WorldData.builder(it.value).build())
                }

            val result = GroupedResultable.of(maybeArena, maybeLobby)
            result.merge(maybeCustomWorlds.values)

            if (result.isFail) {
                throw UnsupportedOperationException(result.message())
            }

            val customWorlds = maybeCustomWorlds
                .entries
                .associate { Pair(it.key, it.value.data()) }
                .toMutableMap()

            return GameWorldImpl.BuilderImpl(maybeArena.data(), maybeLobby.data(), customWorlds)
        }

        /**
         * Performs a check if given location is inside border of given points.
         *
         * @param location location to check onto
         * @param border1  border1 location
         * @param border2  border2 location
         * @return result of the operation.
         */
        fun isInBorder(location: LocationHolder, border1: LocationHolder, border2: LocationHolder): LangResultable {
            val locationWorldId = location.world.uuid
            val border1Id = border1.world.uuid
            val border2Id = border2.world.uuid

            if (!border1Id.equals(locationWorldId)) {
                return LangResultable.fail(GLangKeys.CORE_COMMANDS_ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT)
            }
            if (!border2Id.equals(locationWorldId)) {
                return LangResultable.fail(GLangKeys.CORE_COMMANDS_ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT)
            }

            //this is some black magic copied from Misat
            val min = LocationHolder(
                border1.x.coerceAtMost(border2.x),
                border1.y.coerceAtMost(border2.y),
                border1.z.coerceAtMost(border2.z)
            )
            val max = LocationHolder(
                border1.x.coerceAtLeast(border2.x),
                border1.y.coerceAtLeast(border2.y),
                border1.z.coerceAtLeast(border2.z)
            )

            return if (min.x <= location.x
                && min.y <= location.y
                && min.z <= location.z
                && max.x >= location.x
                && max.y >= location.y
                && max.z >= location.z
            ) {
                LangResultable.ok()
            } else LangResultable.fail(GLangKeys.CORE_COMMON_LOCATION_OUTSIDE_BORDER)
        }
    }
}