package net.hoz.gamecore.api.game.store

import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.Buildable
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.world.LocationHolder

abstract class GameStoreBuilder(
    val name: String,
    var location: LocationHolder? = null,
    var holder: StoreHolder? = null,
    var entityType: EntityTypeHolder? = null,
    var team: String? = null
) : Buildable.Builder<GameStore>