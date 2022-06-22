package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.game.world.WorldData

/**
 * Frame checking functionalities.
 *
 * This provides customizable checking capabilities before starting the frame.
 */
interface FrameChecker {
    /**
     * Checks integrity of the game - if the frame is basically prepared to be used.
     */
    fun checkIntegrity(): GroupedResultable

    /**
     * Performs a check for the [ProtoWorldData.WorldType.ARENA] state.
     */
    fun checkArenaWorld(): Resultable

    /**
     * Performs a check for the [ProtoWorldData.WorldType.LOBBY] state.
     */
    fun checkLobbyWorld(): Resultable

    /**
     * Checks if given [WorldData] is OK.
     */
    fun checkWorld(worldData: WorldData): Resultable
}