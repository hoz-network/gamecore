package net.hoz.gamecore.core.game.frame.builder

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.GroupedResultable
import com.iamceph.resulter.kotlin.dataResultable
import mu.KotlinLogging
import net.hoz.api.data.GameType
import net.hoz.api.data.game.GameConfig
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.BuilderSpawners
import net.hoz.gamecore.api.game.frame.builder.BuilderStores
import net.hoz.gamecore.api.game.frame.builder.BuilderTeams
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.game.world.GameWorldBuilder
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.core.game.frame.GameFrameImpl
import net.hoz.gamecore.core.game.world.GameWorldBuilderImpl
import net.kyori.adventure.text.Component
import java.util.*

private val log = KotlinLogging.logger { }

class GameBuilderImpl(
    override val gameManager: GameManager,
    override val id: UUID,
    private val name: String,
    private val type: GameType,
    private val config: GameConfig,
    private val fromProto: Boolean = false
) : GameBuilder {

    private val teams: BuilderTeams = BuilderTeamsImpl()
    private val spawners: BuilderSpawners = BuilderSpawnersImpl()
    private val stores: BuilderStores = BuilderStoresImpl()
    private val world: GameWorldBuilder = GameWorldBuilderImpl()
    private val manage: GameBuilder.Manage = ManageImpl(this)
    private val unsafe: GameBuilder.Unsafe = UnsafeImpl(this)

    override fun name(): String = name
    override fun world(): GameWorldBuilder = world

    override fun manage(): GameBuilder.Manage = manage
    override fun unsafe(): GameBuilder.Unsafe = unsafe
    override fun teams(): BuilderTeams = teams
    override fun spawners(): BuilderSpawners = spawners
    override fun stores(): BuilderStores = stores

    override fun build(): DataResultable<GameFrame> {
        log.debug { "Trying to build frame [$id - $name]" }
        val integrity = manage.checkIntegrity()
        if (integrity.isFail) {
            log.debug { "Building failed - ${integrity.message()}" }
            return integrity.transform()
        }

        val builtTeams = teams.build()
        if (builtTeams.any { it.value.isFail }) {
            return GroupedResultable.of(builtTeams.values)
                .transform<GameFrame?>()
                .also {
                    log.debug { "Building failed - ${it.message()}" }
                }
        }
        val builtSpawners = spawners.build()
        if (builtSpawners.any { it.value.isFail }) {
            return GroupedResultable.of(builtSpawners.values)
                .transform<GameFrame?>()
                .also {
                    log.debug { "Building failed - ${it.message()}" }
                }
        }
        val builtStores = stores.build()
        if (builtStores.any { it.value.isFail }) {
            return GroupedResultable.of(builtStores.values)
                .transform<GameFrame?>()
                .also {
                    log.debug { "Building failed - ${it.message()}" }
                }
        }
        val builtWorld = world.build()
        if (builtWorld.isFail) {
            return builtWorld.transform<GameFrame?>()
                .also {
                    log.debug { "Building failed - ${it.message()}" }
                }

        }

        return dataResultable {
            manage.destroy()

            GameFrameImpl(gameManager, id, name, Component.text(name), config, builtWorld.data(), type)
                .also { frame ->
                    frame.teams.add(builtTeams.values.map { it.data() })
                    frame.stores.add(builtStores.values.map { it.data() })
                    frame.spawners.add(builtSpawners.values.map { it.data() })
                }
        }

    }

    class ManageImpl(builder: GameBuilderImpl) : GameBuilder.Manage {
        override fun destroy() {
            //TODO: handle visuals, etc
            TODO("Not yet implemented")
        }

        override fun checkIntegrity(): GroupedResultable {
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