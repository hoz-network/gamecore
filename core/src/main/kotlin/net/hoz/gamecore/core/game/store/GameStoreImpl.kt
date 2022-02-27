package net.hoz.gamecore.core.game.store

import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoGameStore
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.StoreInventory
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.entity.EntityLiving
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.world.LocationHolder

class GameStoreImpl(
    val name: String,
    val location: LocationHolder,
    val entityType: EntityTypeHolder,
    val frame: GameFrame,
    var holder: StoreHolder,
    var team: GameTeam? = null,
) : GameStore {
    private val inventories: MutableMap<GamePlayer, StoreInventoryImpl> = mutableMapOf()
    private var storeEntity: EntityLiving? = null

    override fun name(): String = name
    override fun location(): LocationHolder = location
    override fun entityType(): EntityTypeHolder = entityType
    override fun frame(): GameFrame = frame
    override fun holder(): StoreHolder = holder
    override fun team(): GameTeam? = team

    override fun open(player: GamePlayer): Resultable = storeInventory(player).open()

    override fun storeInventory(player: GamePlayer): StoreInventory {
        val inventory = inventories[player]
        if (inventory != null) {
            return inventory
        }

        val newInventory = StoreInventoryImpl(player, frame, holder, team)
        inventories[player] = newInventory
        return newInventory
    }

    override fun players(): List<GamePlayer> = inventories.keys.toList()

    override fun hasPlayer(player: GamePlayer): Boolean = inventories.containsKey(player)

    override fun unsafe(): GameStore.Unsafe {
        TODO("Not yet implemented")
    }

    override fun asProto(): ProtoGameStore {
        TODO("Not yet implemented")
    }
}