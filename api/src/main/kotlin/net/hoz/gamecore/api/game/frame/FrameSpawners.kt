package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.Countable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.entity.EntityItem
import java.util.*

interface FrameSpawners : Countable {
    /**
     * Registers new [GameSpawner].
     * Given spawner's [GameFrame] is set to this instance.
     *
     * @param spawner new spawner to be added
     * @return Result of this operation.
     */
    fun add(spawner: GameSpawner): Resultable

    /**
     * Registers new [GameSpawner]s.
     * Given spawner's [GameFrame] is set to this instance.
     *
     * @param spawners spawners to be added
     */
    fun add(spawners: Collection<GameSpawner>)

    /**
     * Removes spawner from the frame by name.
     *
     * @param name name of the spawner to remove
     * @return Result of this operation.
     */
    fun remove(name: UUID): Resultable

    /**
     * Removes spawner from the frame.
     *
     * @param spawner spawner to remove.
     * @return Result of this operation.
     */
    fun remove(spawner: GameSpawner): Resultable {
        return remove(spawner.id)
    }

    /**
     * Checks if game has this spawner.
     *
     * @param id id of the spawner to check
     * @return true if the [GameSpawner] is in this [GameFrame].
     */
    fun has(id: UUID): Boolean

    /**
     * Checks if game has this spawner.
     *
     * @param spawner spawner to check
     * @return true if the [GameSpawner] is in this [GameFrame].
     */
    fun has(spawner: GameSpawner): Boolean {
        return has(spawner.id)
    }

    /**
     * Finds a spawner by name.
     *
     * @param id id to search for
     * @return [GameSpawner] that has given name.
     */
    fun find(id: UUID): GameSpawner?

    /**
     * Finds a list of spawners for given team
     */
    fun findForTeam(team: GameTeam): List<GameSpawner> =
        all().filter { it.team == team }

    /**
     * Tries to find a spawner by given item.
     */
    fun findByItem(item: EntityItem): GameSpawner?

    /**
     * Gets all spawners in this frame.
     */
    fun all(): List<GameSpawner>

    fun start() {
        all().forEach {
            val manage = it.manage()
            if (manage.isRunning()) {
                manage.stop()
            }
            manage.start()
        }
    }

    fun stop() {
        all().forEach {
            val manage = it.manage()
            if (!manage.isRunning()) {
                manage.stop()
            }
        }
    }

    fun destroy() {
        all().forEach {
            val manage = it.manage()
            if (manage.isRunning()) {
                manage.destroy()
            }
        }
    }
}