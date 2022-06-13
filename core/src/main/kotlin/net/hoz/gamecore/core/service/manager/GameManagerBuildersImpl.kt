package net.hoz.gamecore.core.service.manager

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.kotlin.dataResultable
import net.hoz.api.data.GameType
import net.hoz.api.data.game.GameConfig
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.core.game.frame.builder.GameBuilderImpl
import java.util.*

class GameManagerBuildersImpl(
    private val manager: GameManager
) : GameManager.Builders {
    private val builders = mutableMapOf<String, GameBuilder>()

    override fun all(): List<GameBuilder> = builders.values.toList()

    override fun create(name: String, config: GameConfig, type: GameType): DataResultable<GameBuilder> {
        if (builders.containsKey(name)) {
            return DataResultable.fail("Builder already exists.")
        }

        return dataResultable {
            GameBuilderImpl(manager, UUID.randomUUID(), name, type, config)
                .also {
                    builders[name] = it
                }
        }
    }

    override fun from(data: ProtoGameFrame): DataResultable<GameBuilder> {
        TODO("Not yet implemented")
    }

    override fun from(frame: GameFrame): GameBuilder {
        TODO("Not yet implemented")
    }

    override fun remove(uuid: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun has(uuid: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun find(name: String): GameBuilder? {
        TODO("Not yet implemented")
    }
}