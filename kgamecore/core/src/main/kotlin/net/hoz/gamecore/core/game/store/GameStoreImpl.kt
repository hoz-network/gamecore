package net.hoz.gamecore.core.game.store

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
    val gameFrame: GameFrame,
    var holder: StoreHolder,
    var team: GameTeam? = null,
) : GameStore {
    private val inventories: MutableMap<GamePlayer, StoreInventoryImpl> = mutableMapOf()
    private var storeEntity: EntityLiving? = null

    override fun holder(): StoreHolder {
        TODO("Not yet implemented")
    }

    override fun team(): GameTeam? {
        TODO("Not yet implemented")
    }

    override fun frame(): GameFrame? {
        TODO("Not yet implemented")
    }

    override fun location(): LocationHolder {
        TODO("Not yet implemented")
    }

    override fun entityType(): EntityTypeHolder {
        TODO("Not yet implemented")
    }

    override fun open(player: GamePlayer) {
        TODO("Not yet implemented")
    }

    override fun storeInventory(player: GamePlayer): StoreInventory {
        TODO("Not yet implemented")
    }

    override fun players(): List<GamePlayer> {
        TODO("Not yet implemented")
    }

    override fun hasPlayer(player: GamePlayer): Boolean {
        TODO("Not yet implemented")
    }

    override fun unsafe(): GameStore.Unsafe {
        TODO("Not yet implemented")
    }

    override fun name(): String {
        TODO("Not yet implemented")
    }

    override fun asProto(): ProtoGameStore {
        TODO("Not yet implemented")
    }
}