package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import mu.KotlinLogging
import net.hoz.gamecore.api.game.frame.builder.BuilderBaseImpl
import net.hoz.gamecore.api.game.frame.builder.BuilderSpawners
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import net.hoz.gamecore.core.game.spawner.GameSpawnerBuilderImpl
import java.util.*

private val log = KotlinLogging.logger { }

class BuilderSpawnersImpl : BuilderBaseImpl<GameSpawnerBuilder, GameSpawner, UUID>(), BuilderSpawners {
    override fun provideBuilder(id: UUID): GameSpawnerBuilder = GameSpawnerBuilderImpl(id)

    override fun build(): Map<UUID, DataResultable<GameSpawner>> = builders.map {
        val id = it.key
        val result = it.value.build()

        log.debug { "Building of spawner [${id}] resulted - $result" }
        Pair(id, result)
    }.toMap()
}