package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.gamecore.api.game.frame.FrameChecker
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.world.WorldData
import net.hoz.gamecore.api.util.GConfig

private val log = KotlinLogging.logger { }

internal class FrameCheckerImpl(
    private val frame: GameFrame
) : FrameChecker {
    override fun checkIntegrity(): GroupedResultable {
        val result = GroupedResultable.of()
        result.merge(checkArenaWorld(), checkLobbyWorld())

        if (GConfig.ARE_TEAMS_ENABLED(frame) && frame.teams.count() < 2) {
            result.merge(Resultable.fail("Not enough teams."))
        }
        if (GConfig.ARE_STORES_ENABLED(frame) && frame.stores.count() < 1) {
            result.merge(Resultable.fail("Not enough stores."))
        }
        if (frame.manage.cycle.phases.isEmpty()) {
            result.merge(Resultable.fail("Phases are empty."))
        }
        return result
    }

    override fun checkArenaWorld(): Resultable = checkWorld(frame.world.arenaWorld)

    override fun checkLobbyWorld(): Resultable = checkWorld(frame.world.lobbyWorld)

    override fun checkWorld(worldData: WorldData): Resultable {
        val border1 = worldData.border1
        val border2 = worldData.border2
        if (!border1.isWorldSame(border2)) {
            return Resultable.fail("Borders are not in the same world.")
        }

        if (!border1.isWorldSame(worldData.spawn)) {
            return Resultable.fail("Spawn is not in the same world as borders.")
        }

        worldData.spectator?.let {
            if (!it.isWorldSame(worldData.spectator)) {
                return Resultable.fail("Spawn is not in the same world as borders.")
            }
        }

        return Resultable.ok()
    }
}