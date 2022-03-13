package net.hoz.gamecore.core.game.frame

import net.hoz.api.data.game.GamePhase
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.core.game.ConfigUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.screamingsandals.lib.event.EventManager
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class FramePlayersImplTest {
    private lateinit var gameFrame: GameFrame
    private lateinit var framePlayers: FramePlayersImpl
    private lateinit var frameManagement: FrameManagementImpl
    private lateinit var cycle: GameCycle
    private lateinit var phase: CyclePhase

    @BeforeEach
    fun setUp() {
        if (EventManager.getDefaultEventManager() == null) {
            val eventManager = object : EventManager() {
                override fun isServerThread(): Boolean = false
            }
            EventManager.init { eventManager }
        }

        val manager = Mockito.mock(GameManager::class.java)
        cycle = Mockito.mock(GameCycle::class.java)
        phase = Mockito.mock(CyclePhase::class.java)
        gameFrame = Mockito.mock(GameFrame::class.java)

        frameManagement = FrameManagementImpl(manager, gameFrame, cycle)
        whenever(gameFrame.manage).thenReturn(frameManagement)

        framePlayers = FramePlayersImpl(gameFrame)
    }

    @Test
    fun joinTracked() {
        //Test if not tracked players returns fail resultable
        val player: GamePlayer = mock()
        whenever(player.state).thenReturn(GamePlayer.State.SPECTATOR)

        Assertions.assertFalse { player.state.untracked() }
        assertNull(player.frame)

        val result = framePlayers.join(player)
        assertTrue { result.isFail }
        assertEquals(result.message(), "Cannot join the player, already in game.")
    }

    @Test
    fun joinInGame() {
        //Test if not tracked players returns fail resultable
        val player: GamePlayer = mock()
        whenever(player.frame).thenReturn(gameFrame)
        whenever(player.state).thenReturn(GamePlayer.State.NOT_TRACKED)

        assertTrue { player.state.untracked() }
        assertNotNull(player.frame)

        val result = framePlayers.join(player)
        assertTrue { result.isFail }
        assertEquals(result.message(), "Cannot join the player, already in game.")
    }

    @Test
    fun joinRunningGameWithSpectatorsDisabled() {
        //Test if not tracked players returns fail resultable
        val player: GamePlayer = mock()
        whenever(player.state).thenReturn(GamePlayer.State.NOT_TRACKED)

        val config = ConfigUtils.provideConfig {
            enabled = ConfigUtils.Enabled.provideEnabled {
                spectators = false
            }
        }

        whenever(gameFrame.config()).thenReturn(config)
        whenever(phase.phaseType).thenReturn(GamePhase.DEATH_MATCH)
        whenever(cycle.currentPhase).thenReturn(phase)

        assertTrue { player.state.untracked() }
        assertNull(player.frame)
        assertNotNull(gameFrame.config())

        val trackedAndInGameResult = framePlayers.join(player)
        assertTrue { trackedAndInGameResult.isFail }
        assertEquals(trackedAndInGameResult.message(), "This game is running and you cannot join. Sad. :)")
    }

    @Test
    fun joinWaitingGame() {
        //Test if not tracked players returns fail resultable
        val player: GamePlayer = mock()
        whenever(player.uuid).thenReturn(UUID.randomUUID())
        whenever(player.state).thenReturn(GamePlayer.State.NOT_TRACKED)
        whenever(player.unsafe()).thenReturn(mock())

        val config = ConfigUtils.provideConfig {
            enabled = ConfigUtils.Enabled.provideEnabled {
                spectators = false
            }
        }

        whenever(gameFrame.config()).thenReturn(config)
        whenever(phase.phaseType).thenReturn(GamePhase.WAITING)
        whenever(cycle.currentPhase).thenReturn(phase)
        whenever(gameFrame.world()).thenReturn(mock())
        whenever(gameFrame.world().lobbyWorld).thenReturn(mock())
        whenever(gameFrame.world().lobbyWorld.spawn).thenReturn(mock())

        assertTrue { player.state.untracked() }
        assertNull(player.frame)
        assertNotNull(gameFrame.config())

        val trackedAndInGameResult = framePlayers.join(player)
        assertTrue { trackedAndInGameResult.isFail }
    }

    @Test
    fun leave() {
    }

    @Test
    fun joinTeam() {
    }

    @Test
    fun leaveTeam() {
    }

    @Test
    fun has() {
    }

    @Test
    fun hasEnough() {
    }

    @Test
    fun find() {
    }

    @Test
    fun testFind() {
    }

    @Test
    fun all() {
    }

    @Test
    fun toAlive() {
    }

    @Test
    fun toViewer() {
    }

    @Test
    fun toSpectator() {
    }

    @Test
    fun toLobby() {
    }

    @Test
    fun audiences() {
    }

    @Test
    fun getFrame() {
    }
}