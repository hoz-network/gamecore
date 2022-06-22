package net.hoz.gamecore.api.game.cycle

import net.hoz.api.data.game.GamePhase
import org.jetbrains.annotations.ApiStatus

/**
 * This is the implementation of the [GamePhase].
 * Implementation of this is responsible for handling given phase.
 *
 * This contains: logic for ticking, switching to another phase and so on.
 */
interface CyclePhase {
    /**
     * Phase that should be used next after this phase finishes.
     */
    var nextPhase: GamePhase

    /**
     * Current phase type.
     */
    val phaseType: GamePhase

    /**
     * Maximum count of ticks that this phase can run
     *
     * NOTE: -1 == infinity ticking.
     */
    var maxTicks: Int

    /**
     * Count of ticks that already happened.
     */
    var elapsedTicks: Int

    /**
     * Count of remaining ticks to run.
     */
    fun remainingTicks(): Int

    /**
     * Returns true if this is the last tick.
     */
    fun isLastTick(): Boolean

    /**
     * Returns true if this is the first tick.
     */
    fun isFirstTick(): Boolean

    /**
     * Returns true if this the phase is finished.
     */
    fun isFinished(): Boolean

    /**
     * Action to do on first tick.
     */
    fun doFirstTick()

    /**
     * Action to do on last tick.
     */
    fun doOnLastTick()

    /**
     * Runs check-logic if the next tick can actually happen.
     */
    fun doPreTick(): Boolean

    /**
     * Action to do on normal tick.
     */
    fun doTick()

    /**
     * Resets the phase to new-like state.
     */
    fun reset()

    /**
     * Internal use only, called from [GameCycle].
     */
    @ApiStatus.Internal
    fun tick()
}