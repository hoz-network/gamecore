package net.hoz.gamecore.api.util

import org.screamingsandals.lib.lang.Translation

@Suppress("unused", "MemberVisibilityCanBePrivate") // suppressing useless inspections
object GLangKeys {
    val GAME_CORE_KEY: Translation = Translation.of("game-core")
    val GAME_CORE_KEY_COMMON: Translation = GAME_CORE_KEY.join("common")
    val GAME_CORE_KEY_COMMANDS: Translation = GAME_CORE_KEY.join("commands")
    val GAME_CORE_KEY_SUGGESTIONS: Translation = GAME_CORE_KEY.join("suggestions")
    val GAME_CORE_KEY_HOLOGRAMS: Translation = GAME_CORE_KEY.join("holograms")
    val CORE_PHASE: Translation = GAME_CORE_KEY.join("phase", "name")
    val CORE_COMMON_ARENA_WORLD: Translation = GAME_CORE_KEY_COMMON.join("arena-world")
    val CORE_COMMON_LOBBY_WORLD: Translation = GAME_CORE_KEY_COMMON.join("lobby-world")
    val CORE_COMMON_UNKNOWN_BORDER_VALUE: Translation = GAME_CORE_KEY_COMMON.join("unknown-border-value")
    val CORE_COMMON_UNKNOWN_WORLD_VALUE: Translation = GAME_CORE_KEY_COMMON.join("unknown-world-value")
    val CORE_COMMON_WORLD_NOT_FOUND: Translation = GAME_CORE_KEY_COMMON.join("world-not-found")
    val CORE_COMMON_NORMAL_STORE_NAME: Translation = GAME_CORE_KEY_COMMON.join("normal-store-name")
    val CORE_COMMON_LOCATION_OUTSIDE_BORDER: Translation = GAME_CORE_KEY_COMMON.join("location-outside-of-border")
    val CORE_COMMON_STORE_INFO: Translation = GAME_CORE_KEY_COMMON.join("store-info")
    val CORE_COMMON_TEAM_INFO: Translation = GAME_CORE_KEY_COMMON.join("team-info")
    val CORE_SUGGESTIONS_ENTER_NEW_NAME: Translation = GAME_CORE_KEY_SUGGESTIONS.join("enter-new-name")
    val CORE_COMMANDS_DESCRIPTIONS_BUILDER: Translation = GAME_CORE_KEY_COMMANDS.join("descriptions", "builder")
    val CORE_COMMANDS_ABOUT: Translation = GAME_CORE_KEY_COMMANDS.join("about")
    val CORE_COMMANDS_LIST_GAMES_HEADER: Translation = GAME_CORE_KEY_COMMANDS.join("list-games", "header")
    val CORE_COMMANDS_LIST_GAMES_GAME_INFO: Translation = GAME_CORE_KEY_COMMANDS.join("list-games", "game-info")
    val CORE_COMMANDS_LIST_GAMES_NO_AVAILABLE_GAME: Translation =
        GAME_CORE_KEY_COMMANDS.join("list-games", "no-available-games")
    val CORE_COMMANDS_LIST_BUILDERS_HEADER: Translation = GAME_CORE_KEY_COMMANDS.join("list-builders", "header")
    val CORE_COMMANDS_LIST_BUILDERS_BUILDER_INFO: Translation =
        GAME_CORE_KEY_COMMANDS.join("list-builders", "builder-info")
    val CORE_COMMANDS_LIST_BUILDERS_BUILDER_INFO_TEAMS: Translation =
        GAME_CORE_KEY_COMMANDS.join("list-builders", "builder-info-teams")
    val CORE_COMMANDS_LIST_BUILDERS_BUILDER_INFO_STORES: Translation =
        GAME_CORE_KEY_COMMANDS.join("list-builders", "builder-info-stores")
    val CORE_COMMANDS_LIST_BUILDERS_BUILDER_INFO_SPAWNERS: Translation =
        GAME_CORE_KEY_COMMANDS.join("list-builders", "builder-info-spawners")
    val CORE_COMMANDS_LIST_BUILDERS_NO_AVAILABLE_BUILDER: Translation =
        GAME_CORE_KEY_COMMANDS.join("list-builders", "no-available-builders")
    val CORE_COMMANDS_ERROR_GAME_NOT_FOUND: Translation = GAME_CORE_KEY_COMMANDS.join("error", "game.not-found")
    val CORE_COMMANDS_ERROR_GAME_FAILED_STOPPING: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "game.failed-stopping")
    val CORE_COMMANDS_ERROR_GAME_FAILED_STARTING: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "game.failed-starting")
    val CORE_COMMANDS_ERROR_GAME_CANNOT_STOP_STOPPED: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "game.cannot-stop-stopped")
    val CORE_COMMANDS_ERROR_GAME_ALREADY_EXISTS: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "game.already-exists")
    val CORE_COMMANDS_ERROR_BUILDER_NOT_FOUND: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "not-found")
    val CORE_COMMANDS_ERROR_BUILDER_ALREADY_EXISTS: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "already-exists")
    val CORE_COMMANDS_ERROR_BUILDER_FAILED_CREATING: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "failed-creating")
    val CORE_COMMANDS_ERROR_BUILDER_BORDER_SET: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "border-set")
    val CORE_COMMANDS_ERROR_BUILDER_WORLD_MISSING: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "world-missing")
    val CORE_COMMANDS_ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "border-world-is-different")
    val CORE_COMMANDS_ERROR_BUILDER_TEAM_ALREADY_EXISTS: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "team-already-exists")
    val CORE_COMMANDS_ERROR_BUILDER_TEAM_DOES_NOT_EXISTS: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "team-does-not-exists")
    val CORE_COMMANDS_ERROR_BUILDER_TEAM_NEEDS_MORE_PLAYERS: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "team-needs-more-players")
    val CORE_COMMANDS_ERROR_BUILDER_TEAM_WRONG_SPAWN: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "team-wrong-spawn")
    val CORE_COMMANDS_ERROR_BUILDER_GAME_CANNOT_BE_SAVED: Translation =
        GAME_CORE_KEY_COMMANDS.join("error", "builder", "game-cannot-be-saved")
    val CORE_COMMANDS_SUCCESS_GAME_STOPPED: Translation = GAME_CORE_KEY_COMMANDS.join("success", "game", "stopped")
    val CORE_COMMANDS_SUCCESS_GAME_STARTED: Translation = GAME_CORE_KEY_COMMANDS.join("success", "game", "started")
    val CORE_COMMANDS_SUCCESS_BUILDER_CREATED: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "created")
    val CORE_COMMANDS_SUCCESS_BUILDER_BORDER_SET: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "border-set")
    val CORE_COMMANDS_SUCCESS_BUILDER_SPAWN_SET: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "spawn-set")
    val CORE_COMMANDS_SUCCESS_BUILDER_SPECTATOR_SPAWN_SET: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "spectator-spawn-set")
    val CORE_COMMANDS_SUCCESS_BUILDER_TEAM_SPAWN_SET: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "team-spawn-set")
    val CORE_COMMANDS_SUCCESS_BUILDER_GAME_SAVED: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "game-saved")
    val CORE_COMMANDS_SUCCESS_BUILDER_TEAM_ADDED: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "team-added")
    val CORE_COMMANDS_SUCCESS_BUILDER_SPAWNER_ADDED: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "builder", "spawner-added")
    val CORE_COMMANDS_SUCCESS_PLAYER_JOINED_GAME: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "player", "joined-game")
    val CORE_COMMANDS_SUCCESS_PLAYER_LEFT_GAME: Translation =
        GAME_CORE_KEY_COMMANDS.join("success", "player", "left-game")
    val CORE_COMMANDS_ERROR_PLAYER_ALREADY_IN_GAME: Translation =
        GAME_CORE_KEY_COMMANDS.join("builder", "player", "already-in-game")
    val CORE_COMMANDS_ERROR_PLAYER_GAME_IS_NOT_RUNNING: Translation =
        GAME_CORE_KEY_COMMANDS.join("builder", "player", "game-is-not-running")
    val CORE_HOLOGRAMS_BUILDER_TEAM_SPAWN: Translation = GAME_CORE_KEY_HOLOGRAMS.join("builder", "team-spawn")
    val CORE_HOLOGRAMS_BUILDER_RESOURCE_SPAWNER: Translation =
        GAME_CORE_KEY_HOLOGRAMS.join("builder", "resource-spawner")
}