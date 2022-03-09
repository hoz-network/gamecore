package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.GroupedResultable
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.BuilderSpawners
import net.hoz.gamecore.api.game.frame.builder.BuilderTeams
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.service.GameManager
import reactor.core.publisher.Mono
import java.util.*

class GameBuilderImpl(
    private val id: UUID,
    private val name: String,
    private val gameManager: GameManager,
    private val teams: BuilderTeams = BuilderTeamsImpl()
) : GameBuilder {

    private val manage: GameBuilder.Manage = ManageImpl(this)
    private val unsafe: GameBuilder.Unsafe = UnsafeImpl(this)

    override fun name(): String = name
    override fun build(): DataResultable<GameFrame> {
        TODO("Not yet implemented")
    }

    override fun id(): UUID = id
    override fun gameManager(): GameManager = gameManager
    override fun teams(): BuilderTeams = teams
    override fun spawners(): BuilderSpawners {
        TODO("Not yet implemented")
    }

    override fun manage(): GameBuilder.Manage = manage
    override fun unsafe(): GameBuilder.Unsafe = unsafe

    class ManageImpl(builder: GameBuilderImpl) : GameBuilder.Manage {
        override fun destroy() {
            //TODO: handle visuals, etc
            TODO("Not yet implemented")
        }

        override fun checkIntegrity(): GroupedResultable {
            TODO("Not yet implemented")
        }

        override fun save(): Mono<DataResultable<GameFrame>> {
            TODO("Not yet implemented")
        }
    }

    class UnsafeImpl(builder: GameBuilderImpl) : GameBuilder.Unsafe {
        //TODO
        override fun teamHologram(builder: GameTeamBuilder) {
            TODO("Not yet implemented")
        }

    }
}