package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.GameCycle

/**
 * All frame management operations in one place.
 */
interface FrameManagement {
    /**
     * A [GameCycle] used to run this frame.
     */
    var cycle: GameCycle
    /**
     * Prepares this frame for start.
     * NOTE:What happens here is dependent on the implementation.
     */
    fun prepare(): GroupedResultable

    /**
     * Starts the frame, duh.
     * NOTE: What happens here is dependent on the implementation.
     */
    fun start(): Resultable

    /**
     * Tries to stop the frame.
     * NOTE: What happens here is dependent on the implementation.
     */
    fun stop(): Resultable

    /**
     * Checks whether the frame is running or not.
     *
     * @return true if the frame is running.
     */
    fun isRunning(): Boolean

    /**
     * Checks if the frame contains any players.
     *
     * @return true if the frame is empty.
     */
    fun isEmpty(): Boolean

    /**
     * Checks if the frame is in [GamePhase.WAITING]
     *
     * @return true if the frame is waiting.
     */
    fun isWaiting(): Boolean {
        val currentPhase = cycle.currentPhase
            ?: return false

        return currentPhase.phaseType == GamePhase.WAITING
    }
}
