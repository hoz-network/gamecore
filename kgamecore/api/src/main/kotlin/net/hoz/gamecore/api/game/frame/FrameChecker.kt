package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoWorldData

/**
 * Frame checking functionalities.
 *
 *
 * This provides customizable checking capabilities before starting the frame.
 */
interface FrameChecker {
    /**
     * Checks integrity of the game - if the frame is basically prepared to start.
     *
     * @return [Resultable] of this operation.
     */
    fun checkIntegrity(): GroupedResultable

    /**
     * Performs a check for the [ProtoWorldData.WorldType.ARENA] state.
     *
     * @return [Resultable] of this operation.
     */
    fun checkArenaWorld(): Resultable

    /**
     * Performs a check for the [ProtoWorldData.WorldType.LOBBY] state.
     *
     * @return [Resultable] of this operation.
     */
    fun checkLobbyWorld(): Resultable

    /**
     * Checks if given [ProtoWorldData] is OK.
     *
     * @param worldData world data to check
     * @return [Resultable] of this operation.
     */
    fun checkWorld(worldData: ProtoWorldData): Resultable
}