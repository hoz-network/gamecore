package net.hoz.gamecore.api.service

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import kotlinx.coroutines.flow.SharedFlow
import net.hoz.api.data.GameType
import net.hoz.api.data.game.GameConfig
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.netapi.api.Controlled
import net.hoz.netapi.client.provider.NetGameProvider
import java.util.*

/**
 * Everything is about Frames.
 */
interface GameManager : Controlled {
    /**
     * Provides a connection to backend.
     */
    val backend: NetGameProvider
    val frames: Frames
    val builders: Builders

    // TODO: builder kotlin operators and perhaps property overrides?
    interface Frames {
        fun all(): List<GameFrame>
        fun register(frame: GameFrame): Resultable
        suspend fun save(frame: GameFrame): Resultable
        fun remove(uuid: UUID): Boolean
        fun has(uuid: UUID): Boolean
        fun has(name: String): Boolean = all().any { it.name().equals(name) }

        fun find(uuid: UUID): GameFrame?
        fun find(name: String): GameFrame?
        suspend fun load(uuid: UUID): DataResultable<GameFrame>
        suspend fun load(name: String): DataResultable<GameFrame>
        fun load(protoGameFrame: ProtoGameFrame): DataResultable<GameFrame>
        fun updates(): SharedFlow<ProtoGameFrame>

        /**
         *
         */
        fun supplyInitialPhases(phases: Collection<CyclePhase>)
    }

    // TODO: builder kotlin operators and perhaps property overrides?
    interface Builders {
        fun all(): List<GameBuilder>

        fun create(name: String, config: GameConfig, type: GameType): DataResultable<GameBuilder>
        fun from(data: ProtoGameFrame): DataResultable<GameBuilder>
        fun from(frame: GameFrame): GameBuilder

        fun remove(uuid: UUID): Boolean
        fun has(uuid: UUID): Boolean
        fun has(name: String): Boolean = all().any { it.name().equals(name) }

        fun find(name: String): GameBuilder?
    }
}