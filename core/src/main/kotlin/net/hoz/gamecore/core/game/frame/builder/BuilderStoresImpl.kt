package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import mu.KotlinLogging
import net.hoz.gamecore.api.event.builder.store.GameStoreModifiedEvent
import net.hoz.gamecore.api.event.builder.store.GameStoreRemovedEvent
import net.hoz.gamecore.api.game.frame.builder.BuilderStores
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.frame.builder.base.BuilderBaseImpl
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.GameStoreBuilder
import net.hoz.gamecore.core.game.store.GameStoreBuilderImpl
import org.screamingsandals.lib.kotlin.fire

private val log = KotlinLogging.logger { }

class BuilderStoresImpl(
    private val gameBuilder: GameBuilder
) : BuilderBaseImpl<GameStoreBuilder, GameStore, String>(), BuilderStores {
    override fun provideBuilder(id: String): GameStoreBuilder = GameStoreBuilderImpl(id)

    override fun onModify(builder: GameStoreBuilder) {
        GameStoreModifiedEvent(builder, gameBuilder).fire()
    }

    override fun onRemove(builder: GameStoreBuilder) {
        GameStoreRemovedEvent(builder, gameBuilder).fire()
    }

    override fun build(): Map<String, DataResultable<GameStore>> = builders.map {
        val id = it.key
        val result = it.value.build()

        log.debug { "Building of store [${id}] resulted - $result" }
        Pair(id, result)
    }.toMap()
}