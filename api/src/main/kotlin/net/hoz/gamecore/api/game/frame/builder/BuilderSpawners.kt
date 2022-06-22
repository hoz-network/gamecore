package net.hoz.gamecore.api.game.frame.builder

import net.hoz.gamecore.api.game.frame.builder.base.UuidBuilderBase
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder

/**
 * Builder for the [GameSpawner].
 */
interface BuilderSpawners : UuidBuilderBase<GameSpawnerBuilder, GameSpawner>
