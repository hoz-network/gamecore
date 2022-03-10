package net.hoz.gamecore.api.game.cycle

import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.tasker.Tasker
import org.screamingsandals.lib.tasker.task.TaskerTask

interface GameCycle : Buildable.Builder<Tasker.TaskBuilder> {
    /**
     * The [GameFrame] that this cycle is used for.
     */
    val frame: GameFrame

    /**
     * Task that runs this cycle.
     */
    var cycleTask: TaskerTask?

    /**
     * All phases available in the GameCycle.
     */
    val phases: Map<GamePhase, CyclePhase>

    /**
     * Next phase that will be used after [GameCycle.currentPhase] finishes.
     * This phase can change
     *
     * @return
     */
    val nextPhase: GamePhase

    /**
     * Currently running phase.
     *
     * @return currently running phase or null if none
     */
    val currentPhase: CyclePhase?

    /**
     * Previously ran phase.
     *
     * @return previously ran phase or null if none
     */
    val previousPhase: CyclePhase?

    /**
     * Changes next phase
     */
    fun switchPhase(newPhase: GamePhase): Resultable

    /**
     * Does a tick for currently running phase.
     */
    fun doTick()

    /**
     * Starts the GameCycle.
     */
    fun start(): Resultable

    /**
     * Stops the GameCycle.
     */
    fun stop(): Resultable

    /**
     * Checks if this GameCycle is running.
     */
    fun isRunning(): Boolean
}