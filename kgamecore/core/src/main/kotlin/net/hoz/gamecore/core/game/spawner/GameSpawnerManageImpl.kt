package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.spawner.GameSpawner

class GameSpawnerManageImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Manage {

    override fun start(): Resultable {
        TODO("Not yet implemented")
    }

    override fun stop(): Resultable {
        TODO("Not yet implemented")
    }

    override fun restart(): Resultable {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }
}