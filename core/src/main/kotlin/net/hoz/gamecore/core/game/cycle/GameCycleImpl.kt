package net.hoz.gamecore.core.game.cycle

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.event.game.GamePreTickEvent
import net.hoz.gamecore.api.event.game.cycle.GamePhaseChangedEvent
import net.hoz.gamecore.api.event.game.cycle.GamePrePhaseChangeEvent
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.util.GConfig
import org.screamingsandals.lib.kotlin.fire
import org.screamingsandals.lib.tasker.Tasker
import org.screamingsandals.lib.tasker.task.TaskerTask

open class GameCycleImpl(
    override var frame: GameFrame
) : GameCycle {
    private val log = KotlinLogging.logger { }

    override val phases: MutableMap<GamePhase, CyclePhase> = mutableMapOf()
    override var nextPhase: GamePhase = GamePhase.LOADING
    override var currentPhase: CyclePhase? = null
    override var previousPhase: CyclePhase? = null
    override var cycleTask: TaskerTask? = null

    override fun switchPhase(nextPhase: GamePhase): Resultable {
        val currentPhaseName = currentPhase?.phaseType?.name ?: "None"
        log.debug { "Switching phase from[$currentPhaseName] to[${nextPhase.name}]" }

        this.nextPhase = nextPhase
        //TODO: visuals manager
        return Resultable.ok()
    }

    override fun doTick() {
        log.debug("Trying to do cycle tick..")

        val currentPhase = this.currentPhase ?: run {
            log.debug { "Current phase is null, cannot do tick." }
            return
        }

        if (GamePreTickEvent(frame, this, currentPhase).fire().cancelled
            || !currentPhase.doPreTick()
        ) {
            log.debug("Pre-tick failed for phase [${currentPhase.phaseType}], skipping.")
            return
        }

        currentPhase.doTick()
    }

    override fun start(): Resultable {
        val taskToRun = build()
        if (taskToRun.isFail) {
            return taskToRun
        }

        if (cycleTask != null) {
            stop()
        }

        cycleTask = taskToRun.data().start()
        return taskToRun
    }

    override fun stop(): Resultable {
        val task = cycleTask
        if (task != null) {
            frame.players.leaveAll()
            frame.spawners.destroy()

            frame.maxPlayers = 0

            switchPhase(GamePhase.DISABLED)

            currentPhase = null
            task.cancel()
            //TODO - result
            return Resultable.ok()
        }

        return Resultable.ok()
    }

    override fun isRunning(): Boolean {
        //TODO - handle this better
        return currentPhase != null
    }

    override fun build(): DataResultable<Tasker.TaskBuilder> {
        val tickUnit = GConfig.GAME_TICK_UNIT(frame)
        val tickSpeed = GConfig.GAME_TICK_SPEED(frame)

        val task = Tasker.build(Runnable { this.tickingTask() })
            .delay(0, tickUnit)
            .repeat(tickSpeed.toLong(), tickUnit)
        return DataResultable.ok(task)
    }

    private fun tickingTask() {
        val currentPhase = this.currentPhase
        if (currentPhase != null && !currentPhase.isFinished()) {
            log.debug("Current phase[{}] is not finished, preparing for tick.", currentPhase.phaseType)
            if (frame.manage.isWaiting() && frame.manage.isEmpty()) {
                log.debug("Game is waiting and no players are present, skipping tick.")
                return
            }

            doTick()
            return
        }

        when (nextPhase) {
            GamePhase.LOADING,
            GamePhase.WAITING,
            GamePhase.STARTING,
            GamePhase.IN_GAME,
            GamePhase.DEATH_MATCH,
            GamePhase.ENDING,
            GamePhase.RESTART -> {
                val toSwitch = phases[nextPhase]
                if (toSwitch == null) {
                    log.warn("New phase[{}] is not defined in GameCycle, stopping.", nextPhase)
                    stop()
                    return
                }

                if (GamePrePhaseChangeEvent(frame, currentPhase, toSwitch.phaseType).fire().cancelled()) {
                    log.debug("Cannot change phase, cancelled by event.")
                    return
                }

                //change previous phase to current
                this.previousPhase = currentPhase
                val previousPhase = this.previousPhase

                //change current phase to next one
                this.currentPhase = toSwitch
                //determine next phase available
                this.nextPhase = toSwitch.nextPhase

                //do not reset starting phase
                if (previousPhase != null && previousPhase.phaseType != GamePhase.STARTING) {
                    previousPhase.reset()
                }

                GamePhaseChangedEvent(frame, currentPhase, previousPhase).fire()
            }
            else -> {
                log.warn("Unexpected phase [{}], stopping the game {}", nextPhase, frame.name())
                //unknown phase, stop and log
                stop()
            }
        }
    }

}