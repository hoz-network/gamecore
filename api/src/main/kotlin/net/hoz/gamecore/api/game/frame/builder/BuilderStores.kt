package net.hoz.gamecore.api.game.frame.builder

import net.hoz.gamecore.api.game.frame.builder.base.BuilderBase
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.GameStoreBuilder

interface BuilderStores : BuilderBase<GameStoreBuilder, GameStore, String>