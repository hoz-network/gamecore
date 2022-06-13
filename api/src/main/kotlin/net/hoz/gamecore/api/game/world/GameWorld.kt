package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameWorld
import net.hoz.gamecore.api.Buildable

/**
 * Contains information about all required points to run the game.
 */
interface GameWorld : ProtoWrapper<ProtoGameWorld>, Buildable<GameWorld, GameWorldBuilder> {
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
}