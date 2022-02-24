package net.hoz.gamecore.core.util

import net.hoz.gamecore.api.game.frame.GameFrame
import org.screamingsandals.lib.block.BlockTypeHolder
import org.screamingsandals.lib.block.BlockTypeMapper
import org.screamingsandals.lib.tasker.TaskerTime

interface GConfig {
    companion object {
        val LOBBY_SPAWN_IDENTIFIER = "lobby-spawn-identifier"
        val TEAM_IDENTIFIER_KEY = "team-identifier"
        val SPAWNER_IDENTIFIER_KEY = "spawner-identifier"
        val STORE_IDENTIFIER_KEY = "store-identifier"
        val GAME_CONFIG_KEY = "game.config"

        fun GAME_TICK_UNIT(frame: GameFrame): TaskerTime {
            return TaskerTime.from(frame.config().tick.unit)
        }

        fun GAME_TICK_SPEED(frame: GameFrame): Int {
            return frame.config().tick.speed
        }

        fun GAME_START_TIME(frame: GameFrame): Int {
            return frame.config().times.start
        }

        fun GAME_IN_GAME_TIME(frame: GameFrame): Int {
            return frame.config().times.inGame
        }

        fun GAME_DEATH_MATCH_TIME(frame: GameFrame): Int {
            return frame.config().times.deathMatch
        }

        fun GAME_END_TIME(frame: GameFrame): Int {
            return frame.config().times.end
        }

        fun ARE_TEAMS_ENABLED(frame: GameFrame): Boolean {
            return frame.config().enabled.teams
        }

        fun ARE_STORES_ENABLED(frame: GameFrame): Boolean {
            return frame.config().enabled.stores
        }

        fun ARE_SPECTATORS_ENABLED(frame: GameFrame): Boolean {
            return frame.config().enabled.spectators
        }

        fun ARE_SPAWNERS_ENABLED(frame: GameFrame): Boolean {
            return frame.config().enabled.spawners
        }

        fun ARE_CRAFTING_ENABLED(frame: GameFrame): Boolean {
            return frame.config().enabled.crafting
        }

        fun SHOULD_SPAWNER_STOP_IF_TEAM_IS_DEAD(frame: GameFrame): Boolean {
            return frame.config().spawners.stopIfTeamIsDead
        }

        fun SPAWNERS_ADD_ITEMS_TO_INVENTORY(frame: GameFrame): Boolean {
            return frame.config().spawners.addItemsToInventory
        }

        fun GET_DESTROYABLE_BLOCKS(frame: GameFrame): List<BlockTypeHolder> {
            val blocks = frame.config().destroyable.blocksList
            if (blocks.isEmpty()) {
                return listOf()
            }

            return blocks
                .map { BlockTypeMapper.resolve(it) }
                .filter { it.isPresent }
                .map { it.get() }
        }
    }
}