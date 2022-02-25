package net.hoz.gamecore.api.game.store

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.world.LocationHolder

abstract class GameStoreBuilder(
    val name: String,
    var location: LocationHolder? = null,
    var holder: StoreHolder? = null,
    var entityType: EntityTypeHolder? = null,
    var team: GameTeam? = null
) {
    abstract fun build(): DataResultable<GameStore>
}