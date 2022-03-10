package net.hoz.gamecore.api.service

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.netapi.api.Controlled
import net.hoz.netapi.client.provider.NetGameProvider
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 * Everything is about Frames.
 */
interface GameManager : Controlled {
    /**
     * Provides a connection to backend.
     */
    fun backend(): NetGameProvider
    fun frames(): Frames
    fun builders(): Builders

    // TODO: add kotlin operators and perhaps property overrides?
    interface Frames {
        fun all(): List<GameFrame>
        fun register(frame: GameFrame): Resultable
        fun save(frame: GameFrame): Mono<Resultable>
        fun remove(uuid: UUID): Boolean
        fun has(uuid: UUID): Boolean
        fun has(name: String): Boolean = all().any { it.name().equals(name) }

        fun find(uuid: UUID): GameFrame?
        fun find(name: String): GameFrame?
        fun load(uuid: UUID): Mono<DataResultable<GameFrame>>
        fun load(name: String): Mono<DataResultable<GameFrame>>
        fun build(data: ProtoGameFrame): Mono<DataResultable<GameFrame>>
        fun updates(): Flux<ProtoGameFrame>
    }

    // TODO: add kotlin operators and perhaps property overrides?
    interface Builders {
        fun all(): List<GameBuilder>

        fun create(name: String, configName: String): DataResultable<GameBuilder>
        fun from(data: ProtoGameFrame): DataResultable<GameBuilder>
        fun from(frame: GameFrame): GameBuilder

        fun remove(uuid: UUID): Boolean
        fun has(uuid: UUID): Boolean
        fun has(name: String): Boolean = all().any { it.name().equals(name) }

        fun find(name: String): GameBuilder?
    }
}