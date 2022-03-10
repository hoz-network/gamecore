package net.hoz.gamecore.api.util

import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.game.store.GameStore
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.netapi.client.lang.NLang
import net.kyori.adventure.text.Component
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper
import java.text.DecimalFormat

@Suppress("FunctionName", "MemberVisibilityCanBePrivate", "unused") // suppress useless inspections
object GLang {
    val NUMBER_FORMAT: DecimalFormat = DecimalFormat("#.##")

    fun WORLD_TYPE(player: CommandSenderWrapper, type: ProtoWorldData.WorldType): Component {
        //TODO: translation
        return Component.empty()
        //return Message.of(type.getTranslation()).getForJoined(player)
    }

    fun WORLD_TYPE(type: ProtoWorldData.WorldType): Component {
        //TODO: translation
        return Component.empty()
        //return Message.of(type.getTranslation()).getForAnyoneJoined()
    }

    fun TRUE(player: CommandSenderWrapper): Component {
        return Message.of(NLang.COMMON_TRUE).getForJoined(player)
    }

    fun TRUE(): Component {
        return Message.of(NLang.COMMON_TRUE).forAnyoneJoined
    }

    fun FALSE(player: CommandSenderWrapper): Component {
        return Message.of(NLang.COMMON_FALSE).getForJoined(player)
    }

    fun FALSE(): Component? {
        return Message.of(NLang.COMMON_FALSE).forAnyoneJoined
    }

    fun TRUE_FALSE(player: CommandSenderWrapper, result: Resultable): Component {
        return if (result.isFail) {
            FALSE(player)
        } else TRUE(player)
    }

    fun TEAM_INFO(player: CommandSenderWrapper, team: GameTeam): List<Component> {
        val spawn = team.spawn
        return Message.of(GLangKeys.CORE_COMMON_TEAM_INFO)
            .placeholder("identifier", team.coloredName())
            .placeholder("X", NUMBER_FORMAT.format(spawn.x))
            .placeholder("Y", NUMBER_FORMAT.format(spawn.y))
            .placeholder("Z", NUMBER_FORMAT.format(spawn.z))
            .placeholder("team-color", team.coloredName())
            .placeholder("max-players", team.maxPlayers.toString())
            .getFor(player)
    }

    fun STORE_INFO(player: CommandSenderWrapper?, store: GameStore): List<Component> {
        val location = store.location()
        val team = store.team()
        return Message.of(GLangKeys.CORE_COMMON_STORE_INFO)
            .placeholder("identifier", store.name())
            .placeholder("store-data-identifier", store.holder().name)
            .placeholder("X", NUMBER_FORMAT.format(location.x))
            .placeholder("Y", NUMBER_FORMAT.format(location.y))
            .placeholder("Z", NUMBER_FORMAT.format(location.z))
            .placeholder(
                "team-identifier",
                if (team != null) team.name() else "none"
            )
            .placeholder("entity-type", store.entityType().platformName())
            .getFor(player)
    }

    fun SPAWNER_TYPE(player: CommandSenderWrapper?, spawner: GameSpawner): Component {
        //TODO
        return Component.empty()
        //return Message.of(type.()).getForJoined(player).color(type.color())
    }

    fun SPAWNER_TYPE(player: CommandSenderWrapper?, type: GameSpawnerType): Component {
        //TODO
        return Component.empty()
        //return Message.of(type.()).getForJoined(player).color(type.color())
    }

    fun SPAWNER_INFO(player: CommandSenderWrapper?, spawner: GameSpawner): List<Component> {
        return emptyList()
        //val location = spawner.location()
        //val type = spawner.types()
        //return Message.of(GLangKeys.Companion.CORE_COMMON_STORE_INFO)
        //    .placeholder("resource-name", type.name())
        //    .placeholder("translated-resource-name", SPAWNER_TYPE(player, spawner.type()))
        //    .placeholder("X", if (location.isPresent()) NUMBER_FORMAT.format(location.get().getX()) else "none")
        //    .placeholder("Y", if (location.isPresent()) NUMBER_FORMAT.format(location.get().getY()) else "none")
        //    .placeholder("Z", if (location.isPresent()) NUMBER_FORMAT.format(location.get().getZ()) else "none")
        //    .placeholder("spawn-amount", String.valueOf(settings.amount()))
        //    .placeholder("spawn-max-amount", String.valueOf(settings.max()))
        //    .placeholder("spawn-period", String.valueOf(settings.period()))
        //    .placeholder("spawn-time-unit", settings.timeUnit().name())
        //    .placeholder("is-hologram-visible-in-game", if (spawner.holograms()) TRUE(player) else FALSE(player))
        //    .placeholder("using-global-values", if (spawner.settings().global()) TRUE(player) else FALSE(player))
        //    .getFor(player)
    }
}