package net.hoz.gamecore.api.lang

import org.screamingsandals.lib.lang.Translation

@Suppress("unused", "MemberVisibilityCanBePrivate") // suppressing useless inspections
object GLangKeys {
    val MAIN_KEY: Translation = Translation.of("game-core")
    val COMMON_KEY: Translation = MAIN_KEY.join("common")
    val COMMANDS_KEY: Translation = MAIN_KEY.join("commands")
    val SUGGESTIONS_KEY: Translation = MAIN_KEY.join("suggestions")

    val HOLOGRAMS_KEY: Translation = MAIN_KEY.join("holograms")
    val CORE_PHASE: Translation = MAIN_KEY.join("phase", "name") //todo - IDK what this is

    val CORE_SUGGESTIONS_ENTER_NEW_NAME: Translation = SUGGESTIONS_KEY.join("enter-new-name")

    val CORE_HOLOGRAMS_BUILDER_TEAM_SPAWN: Translation = HOLOGRAMS_KEY.join("builder", "team-spawn")
    val CORE_HOLOGRAMS_BUILDER_RESOURCE_SPAWNER: Translation =
        HOLOGRAMS_KEY.join("builder", "resource-spawner")
}