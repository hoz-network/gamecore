package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoGameSpawner
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.upgrade.Upgrade
import net.hoz.gamecore.api.game.upgrade.Upgradeable
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

class GameSpawnerImpl(
    private val id: UUID,
    private var location: LocationHolder,
    private var holograms: Boolean
) : GameSpawner {
    private var manage: GameSpawner.Manage = GameSpawnerManageImpl(this)
    private var types: GameSpawner.Types = GameSpawnerTypesImpl(this)
    private var items: GameSpawner.Items = GameSpawnerItemsImpl(this)
    private var frame: GameFrame? = null
    private var team: GameTeam? = null

    override fun id(): UUID = id
    override fun team(): GameTeam? = team

    override fun location(): LocationHolder = location
    override fun holograms(): Boolean = holograms
    override fun types(): GameSpawner.Types = types
    override fun manage(): GameSpawner.Manage = manage
    override fun items(): GameSpawner.Items = items

    override fun unsafe(): GameSpawner.Unsafe {
        TODO("Not yet implemented")
    }

    override fun asProto(): ProtoGameSpawner {
        TODO("Not yet implemented")
    }

    override fun toBuilder(): DataResultable<GameSpawner.Builder> {
        TODO("Not yet implemented")
    }

    override fun upgradeType(): Upgradeable.Type {
        TODO("Not yet implemented")
    }

    override fun upgrades(): List<Upgrade> {
        TODO("Not yet implemented")
    }

    override fun addUpgrade(upgrade: Upgrade) {
        TODO("Not yet implemented")
    }

    override fun removeUpgrade(upgrade: Upgrade) {
        TODO("Not yet implemented")
    }

    class UnsafeImpl(private val spawner: GameSpawnerImpl) : GameSpawner.Unsafe {

        override fun hologram(enabled: Boolean) {
            spawner.manage.stop()
            spawner.holograms = enabled
            spawner.manage.start()
        }

        override fun team(team: GameTeam?): Resultable {
            spawner.manage.stop()
            spawner.team = team
            spawner.manage.start()
            return Resultable.ok()
        }

        override fun location(location: LocationHolder) {
            spawner.manage.stop()
            spawner.location = location
            spawner.manage.start()
        }

        override fun addType(type: GameSpawnerType): Resultable {
            spawner.manage.stop()
            val result = spawner.types.add(type)
            spawner.manage.start()
            return result
        }

        override fun removeType(type: GameSpawnerType): Resultable {
            spawner.manage.stop()
            val result = spawner.types.remove(type)
            spawner.manage.start()
            return result
        }

        override fun removeType(name: String): Resultable {
            spawner.manage.stop()
            val result = spawner.types.remove(name)
            spawner.manage.start()
            return result
        }

        override fun types(types: GameSpawner.Types) {
            spawner.manage.stop()
            spawner.types = types
            spawner.manage.start()
        }

        override fun manage(manage: GameSpawner.Manage) {
            spawner.manage.stop()
            spawner.manage = manage
            spawner.manage.start()
        }

        override fun items(items: GameSpawner.Items) {
            spawner.manage.stop()
            spawner.items = items
            spawner.manage.start()
        }

    }
}