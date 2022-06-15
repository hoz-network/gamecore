package net.hoz.gamecore.api.event.builder.store

import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.store.GameStoreBuilder
import org.screamingsandals.lib.event.SEvent

data class GameStoreRemovedEvent(
    val storeBuilder: GameStoreBuilder,
    val gameBuilder: GameBuilder
) : SEvent
