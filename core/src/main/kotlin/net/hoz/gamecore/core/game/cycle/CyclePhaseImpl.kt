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
    override val phaseType: GamePhase,
    override var nextPhase: GamePhase,
    protected val frame: GameFrame = cycle.frame
) : CyclePhase {
    private val log = KotlinLogging.logger { }

    override var maxTicks: Int = -1
    override var elapsedTicks: Int = 0
    open var firstTick = true
    open var finished = false

    override fun remainingTicks(): Int {
        return if (maxTicks == -1) {
            -1
        } else elapsedTicks - maxTicks
    }

    override fun isLastTick(): Boolean {
        return remainingTicks() == 0
    }

    override fun isFirstTick(): Boolean {
        return firstTick
    }

    override fun shouldTick(): Boolean {
        return if (isInfinityTicking()) {
            true
        } else remainingTicks() > 0
    }

    override fun isFinished(): Boolean {
        return finished
    }

    override fun reset() {
        elapsedTicks = 0
        firstTick = false
        finished = true
    }

    override fun tick() {
        if (firstTick) {
            log.debug { "[$phaseType] - Doing first tick." }
            doFirstTick()

            elapsedTicks++
            finished = isLastTick()
            firstTick = false
            return
        }

        log.debug { "[$phaseType] - Firing tick event." }
        if (GameTickEvent(frame, cycle, this).fire().cancelled) {
            log.debug { "[$phaseType] - Skipping tick, event cancelled." }
            return
        }

        log.debug { "[$phaseType] - Doing tick." }
        doTick()

        elapsedTicks++
        finished = isLastTick()
        log.debug { "[$phaseType] - Tick done, elapsed[$elapsedTicks], finished[$finished]" }
    }

    protected open fun isInfinityTicking(): Boolean {
        return remainingTicks() == -1
    }
}