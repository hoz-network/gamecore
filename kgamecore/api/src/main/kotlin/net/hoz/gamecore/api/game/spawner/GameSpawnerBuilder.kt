package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

internal class GameSpawnerBuilder : GameSpawner.Builder {
    override fun uuid(): UUID {
        TODO("Not yet implemented")
    }

    override fun team(): GameTeam? {
        TODO("Not yet implemented")
    }

    override fun team(team: GameTeam): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun location(): LocationHolder? {
        TODO("Not yet implemented")
    }

    override fun location(location: LocationHolder): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun hologram(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hologram(useHologram: Boolean): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun types(): List<GameSpawnerType> {
        TODO("Not yet implemented")
    }

    override fun types(vararg types: GameSpawnerType): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun types(types: Collection<GameSpawnerType>): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun type(type: GameSpawnerType): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun clearTypes(): GameSpawner.Builder {
        TODO("Not yet implemented")
    }

    override fun build(): DataResultable<GameSpawner> {
        TODO("Not yet implemented")
    }
}