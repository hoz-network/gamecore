package net.hoz.gamecore.core.game.store

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.kotlin.dataResultable
import net.hoz.api.data.game.ProtoGameStore
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.StoreInventory
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.entity.EntityLiving
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.hologram.Hologram
import org.screamingsandals.lib.world.LocationHolder

class GameStoreImpl(
    private val name: String,
    private val teamName: String? = null,
    override var location: LocationHolder,
    override var entityType: EntityTypeHolder,
    override var holder: StoreHolder
) : GameStore {
    internal var frame: GameFrame? = null

    private val inventories: MutableMap<GamePlayer, StoreInventoryImpl> = mutableMapOf()
    private val storeNames: MutableMap<GamePlayer, Hologram> = mutableMapOf()
    private var storeEntity: EntityLiving? = null

    override fun name(): String = name
    override var team: GameTeam? = null
    override val players: List<GamePlayer> = inventories.keys.toList()

    override fun open(player: GamePlayer): Resultable {
        val inventory = storeInventory(player)
        if (inventory.isFail) {
            return inventory
        }

        return inventory.data().open()
    }

    override fun storeInventory(player: GamePlayer): DataResultable<StoreInventory> {
        val frame = this.frame
            ?: return DataResultable.fail("Frame is not initialized.")

        val inventory = inventories[player]
        if (inventory != null) {
            return DataResultable.ok(inventory)
        }

        return dataResultable {
            StoreInventoryImpl(player, frame, holder, team)
                .also { inventories[player] = it }
        }
    }

    override fun hasPlayer(player: GamePlayer): Boolean = inventories.containsKey(player)

    override fun unsafe(): GameStore.Unsafe {
        TODO("Not yet implemented")
    }

    override fun asProto(): ProtoGameStore {
        TODO("Not yet implemented")
    }

    internal class UnsafeImpl : GameStore.Unsafe {
        override fun addPlayer(player: GamePlayer) {
            TODO("Not yet implemented")
        }

        override fun removePlayer(player: GamePlayer) {
            TODO("Not yet implemented")
        }

        override fun update(data: StoreHolder) {
            TODO("Not yet implemented")
        }

        override fun create() {
            TODO("Not yet implemented")
        }

        override fun repaint() {
            TODO("Not yet implemented")
        }

        override fun destroy() {
            TODO("Not yet implemented")
        }

    }
}