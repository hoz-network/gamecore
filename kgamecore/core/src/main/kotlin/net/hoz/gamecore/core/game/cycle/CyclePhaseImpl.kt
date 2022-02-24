package net.hoz.gamecore.core.game.cycle

import mu.KotlinLogging
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.event.game.GameTickEvent
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.kotlin.fire

abstract class CyclePhaseImpl(
    protected val cycle: GameCycle,
    protected val phaseType: GamePhase,
    protected var nextPhase: GamePhase,
    protected val frame: GameFrame = cycle.frame()
) : CyclePhase {
    protected val log = KotlinLogging.logger {  }
    protected var ticksToRun = -1
    protected var elapsedTicks = 0

    protected var firstTick = true
    protected var finished = false

    override fun nextPhase(): GamePhase {
        return nextPhase
    }

    override fun nextPhase(phase: GamePhase): CyclePhase {
        nextPhase = phase
        return this
    }

    override fun phaseType(): GamePhase {
        return phaseType
    }

    override fun ticksToRun(): Int {
        return ticksToRun
    }

    override fun ticksToRun(ticksToRun: Int): CyclePhase {
        this.ticksToRun = ticksToRun
        return this
    }

    override fun elapsedTicks(): Int {
        return elapsedTicks
    }

    override fun remainingTicks(): Int {
        return if (ticksToRun == -1 && elapsedTicks == 1) {
            0
        } else elapsedTicks - ticksToRun
    }

    override fun firstTick(): Boolean {
       return firstTick
    }

    override fun shouldTick(): Boolean {
        return remainingTicks() > 0
    }

    override fun finished(): Boolean {
        return finished
    }

    override fun reset() {
        elapsedTicks = 0
        firstTick = false
        finished = true
    }

    override fun tick() {
        if (firstTick) {
            log.debug("First tick, doing doOnFirstTick!")
            doFirstTick()

            elapsedTicks++
            finished = isCountdownFinished()
            firstTick = false
            return
        }

        log.debug("Doing cycle tick!")
        GameTickEvent(frame, cycle, this).fire()

        elapsedTicks++
        finished = isCountdownFinished()
        log.debug("Elapsed ticks: {}, hasFinished: {}", elapsedTicks, finished)
        doTick()
    }

    protected open fun isCountdownFinished(): Boolean {
        return if (ticksToRun == -1) { //infinity ticking
            false
        } else elapsedTicks >= ticksToRun
    }
}