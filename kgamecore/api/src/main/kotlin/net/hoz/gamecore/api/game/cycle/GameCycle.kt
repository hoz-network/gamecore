package net.hoz.gamecore.api.game.cycle

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.tasker.Tasker
import org.screamingsandals.lib.tasker.task.TaskerTask

interface GameCycle {
    /**
     * All phases available in the GameCycle.
     */
    fun phases(): Map<GamePhase, CyclePhase>

    /**
     * Next phase that will be used after [GameCycle.currentPhase] finishes.
     * This phase can change
     *
     * @return
     */
    fun nextPhase(): GamePhase

    /**
     * Currently running phase.
     *
     * @return currently running phase or null if none
     */
    fun currentPhase(): CyclePhase?

    /**
     * Previously ran phase.
     *
     * @return previously ran phase or null if none
     */
    fun previousPhase(): CyclePhase?

    /**
     * Changes next phase
     */
    fun switchPhase(newPhase: GamePhase): Resultable

    /**
     * Does a tick for currently running phase.
     */
    fun doTick()

    fun start(): Resultable

    fun stop(): Resultable

    fun isRunning(): Boolean

    fun frame(): GameFrame

    fun cycleTask(): TaskerTask?

    fun cycleTask(task: TaskerTask)

    fun initialize(): DataResultable<Tasker.TaskBuilder>

}