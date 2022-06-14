package net.hoz.gamecore.core.service

import com.google.inject.Inject
import mu.KotlinLogging
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.core.service.manager.GameManagerBuildersImpl
import net.hoz.gamecore.core.service.manager.GameManagerFramesImpl
import net.hoz.netapi.client.provider.NetGameProvider

private val log = KotlinLogging.logger {  }

class GameManagerImpl @Inject constructor(
    override val backend: NetGameProvider
) : GameManager {
    override val frames = GameManagerFramesImpl(this)
    override val builders = GameManagerBuildersImpl(this)

    override fun dispose() {
        frames.all()
            .forEach { it.manage.stop() }
    }

    override suspend fun initialize() {
        backend.allGames()
            .forEach {
                val id = it.uuid
                val loaded = frames.load(it)

                log.info { "Loading of game[$id] - $loaded" }
            }
    }
}