package net.hoz.gamecore.core.service

import net.hoz.gamecore.api.service.GameManager
import net.hoz.netapi.client.provider.NetGameProvider

class GameManagerImpl : GameManager {
    override fun backend(): NetGameProvider {
        TODO("Not yet implemented")
    }

    override fun frames(): GameManager.Frames {
        TODO("Not yet implemented")
    }

    override fun builders(): GameManager.Builders {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

    override suspend fun initialize() {
        TODO("Not yet implemented")
    }
}