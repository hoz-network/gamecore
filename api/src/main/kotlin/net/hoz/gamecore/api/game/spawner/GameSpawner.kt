package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameSpawner
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.upgrade.Upgradeable
import org.jetbrains.annotations.ApiStatus
import org.screamingsandals.lib.entity.EntityItem
import org.screamingsandals.lib.item.ItemTypeHolder
import org.screamingsandals.lib.kotlin.compare
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

/**
 * Spawner for resources. This basically yeets resources from nowhere to the game world.
 */
interface GameSpawner
    : Buildable<GameSpawner, GameSpawnerBuilder>, ProtoWrapper<ProtoGameSpawner>, Upgradeable {
    /**
     * ID of this spawner.
     *
     * @return [UUID] id.
     */
    val id: UUID

    /**
     * Team of this spawner.
     * Teams are not required to be used on Spawners.
     *
     * @return [GameTeam] if present.
     */
    val team: GameTeam?

    /**
     * Location of where the resources are yeeted to.
     *
     * @return location of the spawner
     */
    val location: LocationHolder

    /**
     * Checks if this spawner uses [Hologram] holograms.
     *
     * @return true if the spawner uses holograms.
     */
    val useHolograms: Boolean

    /**
     * Checks if this spawner uses global values or has custom ones.
     */
    val useGlobalValues: Boolean

    /**
     * Access point for multiple types that can be spawned using this spawner.
     *
     * @return types management
     */
    fun types(): Types

    /**
     * Management operations for this spawner.
     *
     * @return manage
     */
    fun manage(): Manage

    /**
     * Management for the item spawning.
     *
     * @return items
     */
    fun items(): Items

    /**
     * Accesses the unsafe methods.
     * Behaviour of these methods is not guaranteed.
     *
     * @return Unsafe.
     */
    @ApiStatus.Internal
    fun unsafe(): Unsafe

    /**
     * Managing for the spawner types.
     * As the [GameSpawner] can have multiple types at once,
     * this provides access to all the types.
     */
    interface Types {
        /**
         * Map that contains all currently used [GameSpawnerType] for spawning.
         *
         * @return immutable copy of currently used [GameSpawnerType].
         */
        fun all(): Map<String, GameSpawnerType>

        /**
         * Checks if this spawner has given [GameSpawnerType].
         *
         * @param type type to check on
         * @return true if this spawner has this type.
         */
        fun has(type: GameSpawnerType): Boolean

        /**
         * Checks if this spawner has given [GameSpawnerType].
         *
         * @param name name of the type.
         * @return true if this spawner has this type.
         */
        fun has(name: String): Boolean

        fun add(type: GameSpawnerType): Resultable

        fun add(types: List<GameSpawnerType>): Resultable

        fun remove(type: GameSpawnerType): Boolean

        fun remove(name: String): Boolean

        /**
         * Tries to find if this spawner contains [GameSpawnerType] by the name.
         *
         * @param name name to search for
         * @return spawner type if present.
         */
        fun resolve(name: String): GameSpawnerType? {
            return all()[name]
        }

        /**
         * Tries to find if this spawner contains [GameSpawnerType] by the material.
         *
         * @param material material to search for
         * @return spawner type if present.
         */
        fun resolve(material: ItemTypeHolder): GameSpawnerType? {
            return all().values.first { it.material().compare(material) }
        }
    }

    /**
     * Spawner managing operations.
     */
    interface Manage {
        /**
         * Starts the spawning.
         *
         * @return [Resultable] result of this operation.
         */
        fun start(): Resultable

        /**
         * Stops the spawning.
         *
         *
         * NOTE: this does not interfere with any statistics/settings!
         * This just stops the spawning.
         *
         * @return [Resultable] result of this operation.
         */
        fun stop(): Resultable

        fun isRunning(): Boolean

        /**
         * Resets all statistics and settings to default, removes upgrades, etc.
         * Spawning is started again via [Manage.start].
         *
         * @return [Resultable] result of this operation.
         */
        fun restart(): Resultable

        /**
         * Destroys this spawner.
         */
        fun destroy()
    }

    /**
     * Management of the spawned items and the spawning itself.
     */
    interface Items {
        /**
         * Gets all spawned entities by this spawner.
         *
         * @return immutable copy of spawned entities.
         */
        fun all(): Map<GameSpawnerType, List<EntityItem>>

        /**
         * Spawns an item from given [GameSpawnerType]
         */
        fun spawn(type: GameSpawnerType)

        /**
         * Clears all spawned items.
         *
         *
         * This method will remove all spawned items on the ground!
         */
        fun clear()

        /**
         * Gets count of spawned items.
         *
         * @return count of spawned items
         */
        fun count(): Int {
            return all().size
        }
    }

    @ApiStatus.Internal
    interface Unsafe {
        /**
         * Activates holograms for this spawner.
         *
         *
         * NOTE: this does not stop the spawner, the hologram will appear
         * in the next "tick" of this spawner.
         *
         * @param enabled new value.
         */
        fun hologram(enabled: Boolean)

        /**
         * Replaces currently used [GameTeam].
         *
         *
         * NOTE: by default implementation, this stops the spawner
         * and starts it with new team settings!
         *
         * @param team team to use
         */
        fun team(team: GameTeam?): Resultable

        /**
         * Replaces currently used [LocationHolder] spawning location.
         *
         *
         * NOTE: by default implementation, this stops the spawner
         * and starts it with new location settings!
         *
         * @param location new spawning location
         */
        fun location(location: LocationHolder)

        /**
         * Adds new [GameSpawnerType] to this spawner.
         *
         *
         * NOTE: by default implementation, this stops the spawner
         * and starts it with new type settings!
         *
         * @param type type to add
         * @return result of this operation
         */
        fun addType(type: GameSpawnerType): Resultable

        /**
         * Removes[GameSpawnerType] from this spawner.
         *
         *
         * NOTE: by default implementation, this stops the spawner
         * and starts it with new type settings!
         *
         * @param type type to remove
         * @return result of this operation
         */
        fun removeType(type: GameSpawnerType): Resultable

        /**
         * Removes[GameSpawnerType] from this spawner.
         *
         *
         * NOTE: by default implementation, this stops the spawner
         * and starts it with new type settings!
         *
         * @param name name of the type to remove
         * @return result of this operation
         */
        fun removeType(name: String): Resultable

        /**
         * Replaces the management for spawner types.
         */
        fun types(types: Types)

        /**
         * Replaces the spawner management.
         */
        fun manage(manage: Manage)

        /**
         * Replaces the management for item spawning.
         */
        fun items(items: Items)
    }
}