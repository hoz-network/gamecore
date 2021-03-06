package net.hoz.gamecore.api.game.frame

import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.GameType
import net.hoz.api.data.game.GameConfig
import net.hoz.api.data.game.ProtoGameFrame
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.world.GameWorld
import net.kyori.adventure.text.Component
import org.screamingsandals.lib.utils.Nameable
import java.util.*

/**
 * Represents a base frame for a game.
 * This frame holds all data about players, teams, spawners and other mandatory data.
 *
 * @since 2.0.0
 */
interface GameFrame
    : Nameable, ProtoWrapper<ProtoGameFrame>, Buildable<GameFrame, GameBuilder> {
    /**
     * Gets [UUID] of this frame.
     *
     * @return [UUID]
     * @since 2.0.0
     */
    val uuid: UUID

    /**
     * Gets display name of this frame.
     * NOTE: if none is found, the [GameFrame.name] is used!
     *
     * @return display name of this frame
     * @since 2.0.0
     */
    val displayName: Component

    /**
     * Gets current configuration of this frame.
     *
     * @return [GameConfig]
     * @since 2.0.0
     */
    fun config(): GameConfig

    /**
     * Gets current [GameWorld] that contains worlds for this frame.
     *
     * @return holder of the worlds :P
     * @since 2.0.0
     */
    fun world(): GameWorld

    /**
     * Gets minimal players required to start this frame.
     *
     * @return minimal players required to start this frame.
     * @since 2.0.0
     */
    var minPlayers: Int

    /**
     * Gets maximal players that can join this frame.
     *
     * @return maximal players that can join this frame.
     * @since 2.0.0
     */
    var maxPlayers: Int

    /**
     * A [GameType] for this frame.
     *
     * @return game type.
     * @since 2.0.0
     */
    var gameType: GameType

    /**
     * Manages this frame, for example starting, stopping, etc.
     *
     * @return [FrameManagement] of this frame.
     */
    var manage: FrameManagement

    /**
     * Checks the integrity of the game.
     *
     * @return current checker
     */
    var checker: FrameChecker

    /**
     * Manages players in this frame.
     *
     * @return player management.
     */
    var players: FramePlayers

    /**
     * Gets team management for this frame.
     *
     * @return team management
     */
    var teams: FrameTeams

    var stores: FrameStores

    var spawners: FrameSpawners
}