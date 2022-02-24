package net.hoz.gamecore.core.game.cycle

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.event.game.GamePhaseChangedEvent
import net.hoz.gamecore.api.event.game.GamePrePhaseChangeEvent
import net.hoz.gamecore.api.event.game.GamePreTickEvent
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.core.util.GConfig
import org.screamingsandals.lib.event.EventManager
import org.screamingsandals.lib.tasker.Tasker
import org.screamingsandals.lib.tasker.task.TaskerTask

open class GameCycleImpl(
    protected var frame: GameFrame
) : GameCycle {
    private val log = KotlinLogging.logger { GameCycle::javaClass.name }
    protected val phases: MutableMap<GamePhase, CyclePhase> = mutableMapOf()

    protected var nextPhase: GamePhase = GamePhase.LOADING
    protected var currentPhase: CyclePhase? = null
    protected var previousPhase: CyclePhase? = null
    protected var cycleTask: TaskerTask? = null

    override fun phases(): Map<GamePhase, CyclePhase> {
        return phases
    }

    override fun nextPhase(): GamePhase {
        return nextPhase
    }

    override fun currentPhase(): CyclePhase? {
        return currentPhase
    }

    override fun previousPhase(): CyclePhase? {
        return previousPhase
    }

    override fun switchPhase(newPhase: GamePhase): Resultable {
        val currentPhaseName = currentPhase?.phaseType()?.name ?: "None"
        log.debug("Switching phase from [$currentPhaseName] to [${newPhase.name}]!")

        nextPhase = newPhase
        //TODO: visuals manager
        return Resultable.ok()
    }

    override fun doTick() {
        log.debug("Trying to do cycle tick..")

        val currentPhase = this.currentPhase
        if (currentPhase == null) {
            log.debug { "Current phase is null, cannot do tick." }
            return
        }

        val event = EventManager.fire(GamePreTickEvent(frame, this, currentPhase))
        if (event.cancelled() || !currentPhase.shouldTick()) {
            log.debug("Pre-tick failed for phase [${currentPhase.phaseType()}], skipping.")
            return
        }

        currentPhase.doTick()
    }

    override fun start(): Resultable {
        val toRun: DataResultable<Tasker.TaskBuilder> = initialize()
        if (toRun.isFail) {
            return toRun
        }

        if (cycleTask != null) {
            stop()
        }

        cycleTask(toRun.data().start())
        return toRun
    }

    override fun stop(): Resultable {
        val task = cycleTask
        if (task != null) {
            frame.players().leaveAll()

            //TODO - services.spawnerManager().removeAll(frame);
            frame.maxPlayers(0)
            switchPhase(GamePhase.DISABLED)

            currentPhase = null
            task.cancel()
            //TODO - result
            return Resultable.ok()
        }

        return Resultable.ok()
    }

    override fun isRunning(): Boolean {
        //TODO
        return currentPhase != null
    }

    override fun frame(): GameFrame {
        return frame
    }

    override fun cycleTask(): TaskerTask? {
        return cycleTask
    }

    override fun cycleTask(task: TaskerTask) {
        this.cycleTask = task
    }

    override fun initialize(): DataResultable<Tasker.TaskBuilder> {
        val tickUnit = GConfig.GAME_TICK_UNIT(frame)
        val tickSpeed = GConfig.GAME_TICK_SPEED(frame)

        val task = Tasker.build(Runnable { this.tickingTask() })
            .delay(0, tickUnit)
            .repeat(tickSpeed.toLong(), tickUnit)
        return DataResultable.ok(task)
    }

    private fun tickingTask() {
        val currentPhase = this.currentPhase
        if (currentPhase != null && !currentPhase.finished()) {
            log.debug("Current phase[{}] is not finished, preparing for tick.", currentPhase.phaseType())
            if (frame.manage().isWaiting() && frame.manage().isEmpty()) {
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

                if (EventManager.fire(GamePrePhaseChangeEvent(frame, currentPhase, toSwitch)).cancelled()) {
                    log.debug("Cannot change phase, cancelled by event.")
                    return
                }

                //change previous phase to current
                this.previousPhase = currentPhase
                val previousPhase = this.previousPhase

                //change current phase to next one
                this.currentPhase = toSwitch
                //determine next phase available
                this.nextPhase = toSwitch.nextPhase()

                //do not reset starting phase
                if (previousPhase != null && previousPhase.phaseType() != GamePhase.STARTING) {
                    previousPhase.reset()
                }

                EventManager.fire(GamePhaseChangedEvent(frame, currentPhase, previousPhase))
            }
            else -> {
                log.warn("Unexpected phase [{}], stopping the game {}", nextPhase, frame.name())
                //unknown phase, stop and log
                stop()
            }
        }
    }

}