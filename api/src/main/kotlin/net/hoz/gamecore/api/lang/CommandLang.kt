package net.hoz.gamecore.api.lang

import org.screamingsandals.lib.lang.Translation

object CommandLang {

    val DESCRIPTIONS_BUILDER: Translation = GLangKeys.COMMANDS_KEY.join("descriptions", "builder")
    val ABOUT: Translation = GLangKeys.COMMANDS_KEY.join("about")
    val LIST_GAMES_HEADER: Translation = GLangKeys.COMMANDS_KEY.join("list-games", "header")
    val LIST_GAMES_GAME_INFO: Translation = GLangKeys.COMMANDS_KEY.join("list-games", "game-info")
    val LIST_GAMES_NO_AVAILABLE_GAME: Translation =
        GLangKeys.COMMANDS_KEY.join("list-games", "no-available-games")
    val LIST_BUILDERS_HEADER: Translation = GLangKeys.COMMANDS_KEY.join("list-builders", "header")
    val LIST_BUILDERS_BUILDER_INFO: Translation =
        GLangKeys.COMMANDS_KEY.join("list-builders", "builder-info")
    val LIST_BUILDERS_BUILDER_INFO_TEAMS: Translation =
        GLangKeys.COMMANDS_KEY.join("list-builders", "builder-info-teams")
    val LIST_BUILDERS_BUILDER_INFO_STORES: Translation =
        GLangKeys.COMMANDS_KEY.join("list-builders", "builder-info-stores")
    val LIST_BUILDERS_BUILDER_INFO_SPAWNERS: Translation =
        GLangKeys.COMMANDS_KEY.join("list-builders", "builder-info-spawners")
    val LIST_BUILDERS_NO_AVAILABLE_BUILDER: Translation =
        GLangKeys.COMMANDS_KEY.join("list-builders", "no-available-builders")
    val ERROR_GAME_NOT_FOUND: Translation = GLangKeys.COMMANDS_KEY.join("error", "game.not-found")
    val ERROR_GAME_FAILED_STOPPING: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "game.failed-stopping")
    val ERROR_GAME_FAILED_STARTING: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "game.failed-starting")
    val ERROR_GAME_CANNOT_STOP_STOPPED: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "game.cannot-stop-stopped")
    val ERROR_GAME_ALREADY_EXISTS: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "game.already-exists")
    val ERROR_BUILDER_NOT_FOUND: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "not-found")
    val ERROR_BUILDER_ALREADY_EXISTS: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "already-exists")
    val ERROR_BUILDER_FAILED_CREATING: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "failed-creating")
    val ERROR_BUILDER_BORDER_SET: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "border-set")
    val ERROR_BUILDER_WORLD_MISSING: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "world-missing")
    val ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "border-world-is-different")
    val ERROR_BUILDER_TEAM_ALREADY_EXISTS: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "team-already-exists")
    val ERROR_BUILDER_TEAM_DOES_NOT_EXISTS: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "team-does-not-exists")
    val ERROR_BUILDER_TEAM_NEEDS_MORE_PLAYERS: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "team-needs-more-players")
    val ERROR_BUILDER_TEAM_WRONG_SPAWN: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "team-wrong-spawn")
    val ERROR_BUILDER_GAME_CANNOT_BE_SAVED: Translation =
        GLangKeys.COMMANDS_KEY.join("error", "builder", "game-cannot-be-saved")
    val SUCCESS_GAME_STOPPED: Translation = GLangKeys.COMMANDS_KEY.join("success", "game", "stopped")
    val SUCCESS_GAME_STARTED: Translation = GLangKeys.COMMANDS_KEY.join("success", "game", "started")
    val SUCCESS_BUILDER_CREATED: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "created")
    val SUCCESS_BUILDER_BORDER_SET: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "border-set")
    val SUCCESS_BUILDER_SPAWN_SET: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "spawn-set")
    val SUCCESS_BUILDER_SPECTATOR_SPAWN_SET: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "spectator-spawn-set")
    val SUCCESS_BUILDER_TEAM_SPAWN_SET: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "team-spawn-set")
    val SUCCESS_BUILDER_GAME_SAVED: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "game-saved")
    val SUCCESS_BUILDER_TEAM_ADDED: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "team-added")
    val SUCCESS_BUILDER_SPAWNER_ADDED: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "builder", "spawner-added")
    val SUCCESS_PLAYER_JOINED_GAME: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "player", "joined-game")
    val SUCCESS_PLAYER_LEFT_GAME: Translation =
        GLangKeys.COMMANDS_KEY.join("success", "player", "left-game")
    val ERROR_PLAYER_ALREADY_IN_GAME: Translation =
        GLangKeys.COMMANDS_KEY.join("builder", "player", "already-in-game")
    val ERROR_PLAYER_GAME_IS_NOT_RUNNING: Translation =
        GLangKeys.COMMANDS_KEY.join("builder", "player", "game-is-not-running")

}