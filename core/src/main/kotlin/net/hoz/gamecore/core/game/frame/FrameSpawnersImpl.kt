package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.gamecore.api.game.frame.FrameSpawners
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.spawner.GameSpawner
import org.screamingsandals.lib.entity.EntityItem
import java.util.*

private val log = KotlinLogging.logger { }

internal class FrameSpawnersImpl(
    private val frame: GameFrame
) : FrameSpawners {
    override fun add(spawner: GameSpawner): Resultable {
        TODO("Not yet implemented")
    }

    override fun add(spawners: Collection<GameSpawner>) {
        TODO("Not yet implemented")
    }

    override fun remove(name: UUID): Resultable {
        TODO("Not yet implemented")
    }

    override fun has(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun find(id: UUID): GameSpawner? {
        TODO("Not yet implemented")
    }

    override fun findByItem(item: EntityItem): GameSpawner? {
        TODO("Not yet implemented")
    }

    override fun all(): List<GameSpawner> {
        TODO("Not yet implemented")
    }

    override fun count(): Int {
        TODO("Not yet implemented")
    }
}