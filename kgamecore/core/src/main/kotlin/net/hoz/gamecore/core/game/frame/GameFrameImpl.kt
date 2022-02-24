package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.Resultable
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
    private val uuid: UUID,
    private val name: String,
    private val displayName: Component,
    private val config: GameConfig,
    private val world: GameWorld,
    private val gameType: GameType,

    private val gameManager: GameManager,
) : GameFrame {

    private var manage: FrameManagement = FrameManagementImpl(gameManager, this, GameCycleImpl(this))
    private var checker: FrameChecker = FrameCheckerImpl(this)
    private var players: FramePlayers = FramePlayersImpl(this)
    private var teams: FrameTeams = FrameTeamsImpl(this)
    private var stores: FrameStores = FrameStoresImpl(this)
    private var spawners: FrameSpawners = FrameSpawnersImpl(this)

    private var minPlayers = 0
    private var maxPlayers = 0

    override fun uuid(): UUID {
        return uuid
    }

    override fun displayName(): Component {
        return displayName
    }

    override fun config(): GameConfig {
        return config
    }

    override fun world(): GameWorld {
        return world
    }

    override fun minPlayers(): Int {
        return minPlayers
    }

    override fun minPlayers(minPlayers: Int): Resultable {
        this.minPlayers = minPlayers
        return Resultable.ok()
    }

    override fun maxPlayers(): Int {
        return maxPlayers
    }

    override fun maxPlayers(maxPlayers: Int): Resultable {
        this.maxPlayers = maxPlayers
        return Resultable.ok()
    }

    override fun gameType(): GameType {
        return gameType
    }

    override fun manage(): FrameManagement {
        return manage
    }

    override fun manage(management: FrameManagement) {
        this.manage = management
    }

    override fun checker(): FrameChecker {
        return checker
    }

    override fun checker(checker: FrameChecker) {
        this.checker = checker
    }

    override fun players(): FramePlayers {
        return players
    }

    override fun players(players: FramePlayers) {
        this.players = players
    }

    override fun teams(): FrameTeams {
        return teams
    }

    override fun teams(teams: FrameTeams) {
        this.teams = teams
    }

    override fun stores(): FrameStores {
        return stores
    }

    override fun stores(stores: FrameStores) {
        this.stores = stores
    }

    override fun spawners(): FrameSpawners {
        return spawners
    }

    override fun spawners(spawners: FrameSpawners) {
        this.spawners = spawners
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

    override fun toBuilder(): DataResultable<GameBuilder> {
        return gameManager.builders().from(this)
    }
}