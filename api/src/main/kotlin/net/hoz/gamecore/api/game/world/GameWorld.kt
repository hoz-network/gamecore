package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameWorld
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.lang.CommandLang
import net.hoz.gamecore.api.lang.CommonLang
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
    val arenaWorld: WorldData

    /**
     * Gets a [WorldData] for lobby world.
     *
     * @return [WorldData] containing data for the lobby.
     */
    val lobbyWorld: WorldData

    /**
     * Contains custom world data.
     *
     *
     * Key: name of the world
     *
     * @return map of current available custom worlds
     */
    val customWorlds: Map<String, WorldData>

    /**
     * The builder of the [GameWorld].
     */
    interface Builder : Buildable.Builder<GameWorld> {
        var arenaWorld: WorldData?
        var lobbyWorld: WorldData?
        var customWorlds: MutableMap<String, WorldData>
    }

    companion object {
        /**
         * Creates new empty builder.
         *
         * @return empty builder
         */
        fun builder(): Builder = GameWorldImpl.BuilderImpl()

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

            if (border1Id != locationWorldId) {
                return LangResultable.fail(CommandLang.ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT)
            }
            if (border2Id != locationWorldId) {
                return LangResultable.fail(CommandLang.ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT)
            }

            // this is some black magic copied from Misat
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
            } else LangResultable.fail(CommonLang.LOCATION_OUTSIDE_BORDER)
        }
    }
}