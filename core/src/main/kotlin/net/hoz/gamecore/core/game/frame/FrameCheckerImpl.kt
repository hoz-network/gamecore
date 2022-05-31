package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.game.frame.FrameChecker
import net.hoz.gamecore.api.game.frame.GameFrame

private val log = KotlinLogging.logger { }

internal class FrameCheckerImpl(
    private val frame: GameFrame
) : FrameChecker {
    override fun checkIntegrity(): GroupedResultable {
        return GroupedResultable.of()
    }

    override fun checkArenaWorld(): Resultable {
        return Resultable.ok()
    }

    override fun checkLobbyWorld(): Resultable {
        return Resultable.ok()
    }

    override fun checkWorld(worldData: ProtoWorldData): Resultable {
        //TODO
        return Resultable.ok()
    }
}