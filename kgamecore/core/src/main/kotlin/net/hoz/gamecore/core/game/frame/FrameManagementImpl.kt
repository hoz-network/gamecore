package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.event.game.GameDisabledEvent
import net.hoz.gamecore.api.event.game.GamePreStartEvent
import net.hoz.gamecore.api.event.game.GameStartedEvent
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.FrameManagement
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.event.EventManager

open class FrameManagementImpl(
    protected val manager: GameManager,
    protected val frame: GameFrame,
    protected var cycle: GameCycle
) : FrameManagement {
    private val log = KotlinLogging.logger { }

    override fun prepare(): GroupedResultable {
        if (cycle.isRunning()) {
            cycle.stop()
            cycle.phases()
                .values
                .forEach { it.reset() }
        }

        val phaseChange = cycle.switchPhase(GamePhase.LOADING)
        if (phaseChange.isFail) {
            return GroupedResultable.of(phaseChange)
        }

        frame.maxPlayers(countMaxPlayers())

        val minPlayers = frame.minPlayers()
        if (minPlayers == 0) {
            frame.minPlayers(countMinPlayers())
        }

        return GroupedResultable.of(Resultable.ok())
    }

    override fun start(): Resultable {
        if (EventManager.fire(GamePreStartEvent(frame)).cancelled()) {
            return Resultable.fail("Cancelled by event.")
        }

        val prepare = prepare()
        if (prepare.isFail) {
            cycle.switchPhase(GamePhase.DISABLED)
            return prepare
        }

        val checkIntegrity = frame.checker().checkIntegrity()
        if (checkIntegrity.isFail) {
            cycle.switchPhase(GamePhase.DISABLED)
            return checkIntegrity
        }

        val startResult = cycle.start()
        if (startResult.isFail) {
            return startResult
        }

        EventManager.fire(GameStartedEvent(frame))
        return startResult
    }

    override fun stop(): Resultable {
        if (!isRunning()) {
            return Resultable.ok("Already stopped.")
        }

        try {
            val result = cycle.stop()
            //TODO
            if (cycle.isRunning()) {
                log.warn("Fuckup happened, GameCycle is still running.. Trying to kill it now!")
                EventManager.fire(GameDisabledEvent(frame))
                return cycle.stop()
            }
            EventManager.fire(GameDisabledEvent(frame))

            return result
        } catch (e: Exception) {
            log.warn("Exception occurred while stopping the game! {}", e.message, e)
            return Resultable.fail("Cannot stop the game because of Exception!", e)
        }
    }

    override fun cycle(): GameCycle {
        return cycle
    }

    override fun cycle(cycle: GameCycle): Resultable {
        val result = cycle.stop()
        if (result.isFail) {
            return result
        }

        this.cycle = cycle
        return Resultable.ok()
    }

    override fun isRunning(): Boolean {
        val phase = cycle.currentPhase() ?: return false

        return when (phase.phaseType()) {
            GamePhase.WAITING,
            GamePhase.LOADING,
            GamePhase.DISABLED -> true
            else -> false
        }
    }

    override fun isEmpty(): Boolean {
        return frame.players().count() == 0
    }

    private fun countMinPlayers(): Int {
        return frame.maxPlayers() / frame.teams().count()
    }

    private fun countMaxPlayers(): Int {
        return frame.teams()
            .all()
            .values
            .sumOf { it.maxPlayers() }
    }

}
