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
    fun nextPhase(): GamePhase

    /**
     * Changes the next phase that will be used after this phase finishes.
     *
     *
     * NOTE: this does not represent actual [GameCycle.nextPhase],
     * because that can be only changed by [GameCycle.switchPhase]!
     *
     * @param phase new phase to use after this.
     */
    fun nextPhase(phase: GamePhase): CyclePhase

    /**
     * What [GamePhase] is this cycle phase for.
     *
     * @return GamePhase that this cycle is for.
     */
    fun phaseType(): GamePhase

    fun ticksToRun(): Int

    fun ticksToRun(ticksToRun: Int): CyclePhase

    fun elapsedTicks(): Int

    fun remainingTicks(): Int

    fun lastTick(): Boolean {
        return remainingTicks() == 0
    }

    fun firstTick(): Boolean

    fun shouldTick(): Boolean

    fun finished(): Boolean

    fun doFirstTick()

    fun doTick()

    fun reset()

    /**
     * Internal use only, called from [GameCycle].
     */
    @ApiStatus.Internal
    fun tick()
}