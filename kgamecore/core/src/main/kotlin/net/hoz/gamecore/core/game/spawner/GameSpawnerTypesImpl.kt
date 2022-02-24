package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType

class GameSpawnerTypesImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Types {
    private val types: MutableMap<String, GameSpawnerType> = mutableMapOf()

    override fun all(): Map<String, GameSpawnerType> = types

    override fun has(type: GameSpawnerType): Boolean = types[type.name()] != null

    override fun has(name: String): Boolean = types[name] != null

    override fun add(type: GameSpawnerType): Resultable {
        if (types.containsKey(type.name())) {
            return Resultable.fail("Already have this type.")
        }

        types[type.name()] = type
        if (spawner.manage().isRunning()) {
            spawner.manage().stop()
            spawner.manage().start()
        }

        return Resultable.ok()
    }

    override fun add(types: List<GameSpawnerType>): GroupedResultable = GroupedResultable.of(types.map { add(it) })

    override fun remove(type: GameSpawnerType): Boolean = types.remove(type.name()) != null

    override fun remove(name: String): Boolean = types.remove(name) != null
}