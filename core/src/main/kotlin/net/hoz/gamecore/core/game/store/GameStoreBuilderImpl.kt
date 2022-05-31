package net.hoz.gamecore.core.game.store

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.kotlin.dataResultable
import net.hoz.api.data.game.ProtoGameStore
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.store.GameStoreBuilder
import org.screamingsandals.lib.entity.type.EntityTypeHolder
import org.screamingsandals.lib.world.LocationHolder
import org.screamingsandals.lib.world.LocationMapper

internal class GameStoreBuilderImpl(
    name: String,
    location: LocationHolder? = null,
    holder: StoreHolder? = null,
    entityType: EntityTypeHolder? = null,
    team: String? = null
) : GameStoreBuilder(name, location, holder, entityType, team) {

    companion object {
        fun fromProto(data: ProtoGameStore, holder: StoreHolder): GameStoreBuilderImpl = GameStoreBuilderImpl(
            data.name,
            LocationMapper.resolve(data.location).orElseThrow(),
            holder,
            EntityTypeHolder.of(data.entityType),
            data.teamId
        )
    }

    override fun build(): DataResultable<GameStore> {
        val location = this.location ?: return DataResultable.fail("Location is not specified.")
        val holder = this.holder ?: return DataResultable.fail("StoreHolder is not defined.")
        val entityType = this.entityType ?: EntityTypeHolder.of("VILLAGER")

        return dataResultable { GameStoreImpl(name, team, location, entityType, holder) }
    }
}