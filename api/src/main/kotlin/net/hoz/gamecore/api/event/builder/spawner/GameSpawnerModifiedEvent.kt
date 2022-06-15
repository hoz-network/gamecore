package net.hoz.gamecore.api.event.builder.spawner

import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import org.screamingsandals.lib.event.SEvent

data class GameSpawnerModifiedEvent(
    val spawnerBuilder: GameSpawnerBuilder,
    val gameBuilder: GameBuilder
) : SEvent
