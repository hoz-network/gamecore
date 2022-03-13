package net.hoz.gamecore.core.game.frame

import net.hoz.api.data.GameType
import net.hoz.api.data.game.GameConfig
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.gamecore.api.game.frame.*
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.world.GameWorld
import net.hoz.gamecore.api.service.GameManager
import net.hoz.gamecore.core.game.cycle.GameCycleImpl
import net.kyori.adventure.text.Component
import org.screamingsandals.lib.utils.AdventureHelper
import java.util.*

class GameFrameImpl(
    override val uuid: UUID,
    private val name: String,
    override val displayName: Component,
    private val config: GameConfig,
    private val world: GameWorld,
    override var gameType: GameType,

    private val gameManager: GameManager,
) : GameFrame {

    override var manage: FrameManagement = FrameManagementImpl(gameManager, this, GameCycleImpl(this))
    override var checker: FrameChecker = FrameCheckerImpl(this)
    override var players: FramePlayers = FramePlayersImpl(this)
    override var teams: FrameTeams = FrameTeamsImpl(this)
    override var stores: FrameStores = FrameStoresImpl(this)
    override var spawners: FrameSpawners = FrameSpawnersImpl(this)

    override var minPlayers = 0
    override var maxPlayers = 0

    override fun config(): GameConfig {
        return config
    }

    override fun world(): GameWorld {
        return world
    }

    override fun name(): String {
        return name
    }

    override fun asProto(): ProtoGameFrame {
        val builder = ProtoGameFrame.newBuilder()
            .setUuid(uuid.toString())
            .setName(name)
            .setDisplayName(AdventureHelper.toJson(displayName))
            .setConfigName(config.name)

        return builder.build()
    }

    override fun toBuilder(builder: GameBuilder.() -> Unit): GameBuilder {
        val data = gameManager.builders().from(this)
        builder.invoke(data)
        return data
    }
}