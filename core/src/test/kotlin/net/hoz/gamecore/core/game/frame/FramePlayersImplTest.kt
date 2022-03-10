package net.hoz.gamecore.core.game.frame

import net.hoz.gamecore.api.game.cycle.GameCycle
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.service.GameManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.screamingsandals.lib.event.EventManager
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class FramePlayersImplTest {
    private lateinit var gameFrame: GameFrame
    private lateinit var framePlayers: FramePlayersImpl

    @BeforeEach
    fun setUp() {
        if (EventManager.getDefaultEventManager() == null) {
            val eventManager = object : EventManager() {
                override fun isServerThread(): Boolean = false
            }
            EventManager.init { eventManager }
        }

        val manager = Mockito.mock(GameManager::class.java)
        val cycle = Mockito.mock(GameCycle::class.java)
        gameFrame = Mockito.mock(GameFrame::class.java)

        val manage = FrameManagementImpl(manager, gameFrame, cycle)
        whenever(gameFrame.manage()).thenReturn(manage)

        framePlayers = FramePlayersImpl(gameFrame)
    }

    @Test
    fun join() {
        //Test if not tracked players returns fail resultable
        val trackedInGame: GamePlayer = mock()
        whenever(trackedInGame.frame).thenReturn(gameFrame)
        whenever(trackedInGame.state).thenReturn(GamePlayer.State.SPECTATOR)

        val trackedAndInGameResult = framePlayers.join(trackedInGame)
        assertTrue { trackedAndInGameResult.isFail }
        assertEquals(trackedAndInGameResult.message(), "Cannot join the player, already in game.")
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