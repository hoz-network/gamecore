package net.hoz.gamecore.core.game.frame

import net.hoz.api.data.GameType
import net.hoz.api.data.game.GameConfig
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.api.data.game.protoGameFrame
import net.hoz.gamecore.api.game.frame.*
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.world.GameWorld
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.core.game.cycle.GameCycleImpl
import org.screamingsandals.lib.spectator.Component
import java.util.*

class GameFrameImpl(
    private val gameManager: GameManager,
    override val uuid: UUID,
    private val name: String,
    override val displayName: Component,
    private val config: GameConfig,
    private val world: GameWorld,
    override var gameType: GameType,
    override var customConfig: Boolean = false
) : GameFrame {

    override var manage: FrameManagement = FrameManagementImpl(gameManager, this, GameCycleImpl(this))
    override var checker: FrameChecker = FrameCheckerImpl(this)
    override var players: FramePlayers = FramePlayersImpl(this)
    override var teams: FrameTeams = FrameTeamsImpl(this)
    override var stores: FrameStores = FrameStoresImpl(this)
    override var spawners: FrameSpawners = FrameSpawnersImpl(this)

    override var minPlayers = 0
    override var maxPlayers = 0

    override fun config(): GameConfig = config
    override fun world(): GameWorld = world
    override fun name(): String = name

    override fun asProto(): ProtoGameFrame {
        val protoTeams = teams.all().map { it.asProto() }
        val protoSpawners = spawners.all().map { it.asProto() }
        val protoStores = stores.all().map { it.asProto() }
        val protoWorld = world.asProto()

        return protoGameFrame {
            uuid = this@GameFrameImpl.uuid.toString()
            name = this@GameFrameImpl.name
            displayName = this@GameFrameImpl.displayName.toJavaJson()
            configName = this@GameFrameImpl.config.name
            minPlayers = this@GameFrameImpl.minPlayers
            type = this@GameFrameImpl.gameType
            world = protoWorld

            if (this@GameFrameImpl.customConfig) {
                customConfig = this@GameFrameImpl.config
            }

            teams.addAll(protoTeams)
            stores.addAll(protoStores)
            spawners.addAll(protoSpawners)
        }
    }

    override fun toBuilder(builder: GameBuilder.() -> Unit): GameBuilder {
        val data = gameManager.builders().from(this)
        builder.invoke(data)
        return data
    }
}