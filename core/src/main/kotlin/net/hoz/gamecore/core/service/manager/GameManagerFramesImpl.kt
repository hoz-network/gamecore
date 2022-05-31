package net.hoz.gamecore.core.service.manager

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
import kotlinx.coroutines.flow.SharedFlow
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.gamecore.api.game.cycle.CyclePhase
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.service.GameManager
import java.util.*

internal class GameManagerFramesImpl(
    private val manager: GameManager,
    private val initialPhases: List<CyclePhase>
) : GameManager.Frames {
    private val frames: MutableMap<UUID, GameFrame> = mutableMapOf()

    override fun all(): List<GameFrame> = frames.values.toList()

    override fun register(frame: GameFrame): Resultable {
        if (frames[frame.uuid] != null) {
            return Resultable.fail("Frame already exists.")
        }

        frames[frame.uuid] = frame
        return Resultable.ok()
    }

    override suspend fun save(frame: GameFrame): Resultable = manager.backend().saveGame(frame.asProto())

    override fun remove(uuid: UUID): Boolean = frames.remove(uuid) != null
    override fun has(uuid: UUID): Boolean = frames.containsKey(uuid)
    override fun find(uuid: UUID): GameFrame? = frames[uuid]

    override fun find(name: String): GameFrame? = frames.values
        .firstOrNull { it.name() == name }

    override suspend fun load(uuid: UUID): DataResultable<GameFrame> = buildFrame(manager.backend().loadGame(uuid))

    override suspend fun load(name: String): DataResultable<GameFrame> = buildFrame(manager.backend().loadGame(name))

    override fun updates(): SharedFlow<ProtoGameFrame> {
        TODO("Not yet implemented")
    }

    override fun supplyInitialPhases(phases: Collection<CyclePhase>) {
        TODO("Not yet implemented")
    }

    private fun buildFrame(input: DataResultable<ProtoGameFrame>): DataResultable<GameFrame> =
        input.flatMap { manager.builders().from(it) }
            .flatMap { it.build() }
}