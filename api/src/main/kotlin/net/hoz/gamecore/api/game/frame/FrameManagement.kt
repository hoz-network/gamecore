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
     * Prepares this frame for start.
     * NOTE:What happens here is dependent on the implementation.
     *
     * @return [GroupedResultable] result of this operation.
     */
    fun prepare(): GroupedResultable

    /**
     * Starts the frame, duh.
     * NOTE: What happens here is dependent on the implementation.
     *
     * @return [Resultable] result of this operation.
     */
    fun start(): Resultable

    /**
     * Tries to stop the frame.
     * NOTE: What happens here is dependent on the implementation.
     *
     * @return [Resultable] result of this operation.
     */
    fun stop(): Resultable

    /**
     * A [GameCycle] used to run this frame.
     *
     * @return cycle
     */
    fun cycle(): GameCycle

    /**
     * Changes a [GameCycle] for a new one.
     *
     * This stops the game!
     */
    fun cycle(cycle: GameCycle): Resultable

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
        val currentPhase = cycle().currentPhase ?: return false

        return currentPhase.phaseType == GamePhase.WAITING
    }
}