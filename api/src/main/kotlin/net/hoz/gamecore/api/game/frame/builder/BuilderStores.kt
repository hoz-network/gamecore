package net.hoz.gamecore.api.game.frame.builder

import net.hoz.gamecore.api.game.frame.builder.base.BuilderBase
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.GameStoreBuilder

/**
 * Builder for the [GameStore].
 */
interface BuilderStores : BuilderBase<GameStoreBuilder, GameStore, String>