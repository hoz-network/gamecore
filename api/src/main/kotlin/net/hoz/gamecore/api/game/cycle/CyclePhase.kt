package net.hoz.gamecore.api.game.cycle

import net.hoz.api.data.game.GamePhase
import org.jetbrains.annotations.ApiStatus

/**
 * Actual implementation of [GamePhase].
 *
 *
 * This handles ticking. //TODO
 */
interface CyclePhase {
    /**
     * Phase that should be next after this phase finishes.
     *
     *
     * NOTE: this does not represent actual [GameCycle.nextPhase],
     * because that can be changed by [GameCycle.switchPhase]!
     */
    var nextPhase: GamePhase

    /**
     * What [GamePhase] is this cycle phase for.
     *
     * @return GamePhase that this cycle is for.
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
     * Count of remaining ticks to run
     */
    fun remainingTicks(): Int

    /**
     * Checks if this is the last tick.
     */
    fun isLastTick(): Boolean

    /**
     * Checks if this is the first tick
     */
    fun isFirstTick(): Boolean

    /**
     * Checks if the phase has ended.
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
     * Fires an action that happens before the tick.
     * If this returns false, the actual tick WON'T happen.
     */
    fun doPreTick(): Boolean

    /**
     * Action to do on normal tick.
     */
    fun doTick()

    /**
     * Resets the phase to default value.
     */
    fun reset()

    /**
     * Internal use only, called from [GameCycle].
     */
    @ApiStatus.Internal
    fun tick()
}