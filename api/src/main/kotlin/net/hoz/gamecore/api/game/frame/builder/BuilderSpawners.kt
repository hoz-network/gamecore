package net.hoz.gamecore.api.game.frame.builder

import net.hoz.api.data.game.ProtoGameSpawner
import net.hoz.gamecore.api.ProtoBuildable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import java.util.*

interface BuilderSpawners : BuilderBase<GameSpawnerBuilder, GameSpawner, UUID>