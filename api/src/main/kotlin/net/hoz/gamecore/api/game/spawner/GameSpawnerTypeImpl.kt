package net.hoz.gamecore.api.game.spawner

import net.hoz.api.data.GameType
import net.hoz.api.data.MinecraftColor
import net.hoz.api.data.game.ProtoSpawnerType
import net.hoz.gamecore.api.lang.GLang
import org.screamingsandals.lib.item.Item
import org.screamingsandals.lib.item.ItemTypeHolder
import org.screamingsandals.lib.item.builder.ItemFactory
import org.screamingsandals.lib.lang.Translation
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.spectator.Component

internal data class GameSpawnerTypeImpl(
    override val nameTranslation: Translation,
    val name: String,
    override val material: ItemTypeHolder,
    override val color: Color,
    override val gameType: GameType,
    override val settings: GameSpawnerSettings,
    override val enabled: Boolean

) : GameSpawnerType {

    override fun item(amount: Int, target: PlayerWrapper?): Item {
        val builder = ItemFactory.builder()
            .type(material)

        if (target == null) {
            builder.displayName(Component.fromJavaJson(name))
        } else {
            builder.displayName(GLang.SPAWNER_TYPE(target, this))
        }

        builder.amount(amount)
        //TODO: item data
        return builder.build().orElseThrow()
    }

    override fun name(): String {
        return name
    }

    override fun asProto(): ProtoSpawnerType {
        return ProtoSpawnerType.newBuilder()
            .setName(name)
            .setNameTranslation(nameTranslation.keys.joinToString("."))
            .setColor(MinecraftColor.valueOf(color.toString()))
            .setMaterial(material.platformName())
            .setSettings(settings.asProto())
            .setType(gameType)
            .build()
    }
}