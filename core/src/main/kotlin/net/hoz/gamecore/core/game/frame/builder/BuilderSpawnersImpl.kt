package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import mu.KotlinLogging
import net.hoz.gamecore.api.game.frame.builder.BuilderBaseImpl
import net.hoz.gamecore.api.game.frame.builder.BuilderSpawners
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerBuilder
import net.hoz.gamecore.core.game.spawner.GameSpawnerBuilderImpl
import java.util.*

class BuilderSpawnersImpl : BuilderBaseImpl<GameSpawnerBuilder, GameSpawner, UUID>(), BuilderSpawners {
    private val log = KotlinLogging.logger { }

    override fun provideBuilder(id: UUID): GameSpawnerBuilder {
        return GameSpawnerBuilderImpl(id)
    }

    override fun build(): Map<UUID, DataResultable<GameSpawner>> {
        return builders
            .map {
                val id = it.key
                val result = it.value.build()

                log.debug { "Building of team [${id}] resulted - $result" }
                Pair(id, result)
            }
            .toMap()
    }
}