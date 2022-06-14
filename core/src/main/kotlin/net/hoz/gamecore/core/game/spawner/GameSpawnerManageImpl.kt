package net.hoz.gamecore.core.game.spawner

import com.iamceph.resulter.core.Resultable
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.util.GConfig
import org.screamingsandals.lib.tasker.Tasker
import org.screamingsandals.lib.tasker.task.TaskerTask

//TODO: holograms
//TODO: logging

class GameSpawnerManageImpl(
    private val spawner: GameSpawnerImpl
) : GameSpawner.Manage {
    var tasks: MutableMap<GameSpawnerType, TaskerTask> = mutableMapOf()
    var running = false

    override fun start(): Resultable {
        val frame = spawner.frame
        val team = spawner.team
        val types = spawner.types().all().values

        types.forEach {
            val task = Tasker.build(Runnable {
                if (GConfig.SHOULD_SPAWNER_STOP_IF_TEAM_IS_DEAD(frame) && team != null && !team.alive()) {
                    stop()
                    return@Runnable
                }
                spawner.items().spawn(it)
            })
                .repeat(it.settings.period.toLong(), it.settings.timeUnit)
                .start()

            tasks[it] = task
        }
        return Resultable.ok()
    }

    override fun stop(): Resultable {
        tasks.values.forEach {
            it.cancel()
        }
        tasks.clear()

        return Resultable.ok()
    }

    override fun isRunning() = running

    override fun restart(): Resultable {
        val stop = stop()
        if (stop.isFail) {
            return stop
        }

        //TODO: handle spawning statistics here

        return start()
    }

    override fun destroy() {
        stop()
        spawner.items().clear()
    }
}