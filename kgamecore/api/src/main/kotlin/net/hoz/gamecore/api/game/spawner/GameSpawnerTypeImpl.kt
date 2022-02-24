package net.hoz.gamecore.api.game.spawner

import net.hoz.api.data.GameType
import net.hoz.api.data.MinecraftColor
import net.hoz.api.data.game.ProtoSpawnerType
import net.hoz.gamecore.api.util.GLang
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.item.Item
import org.screamingsandals.lib.item.ItemTypeHolder
import org.screamingsandals.lib.item.builder.ItemFactory
import org.screamingsandals.lib.lang.Translation
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.utils.AdventureHelper

internal data class GameSpawnerTypeImpl(
    val nameTranslation: Translation,
    val name: String,
    val material: ItemTypeHolder,
    val color: NamedTextColor,
    val gameType: GameType,
    val settings: GameSpawnerSettings,
    val enabled: Boolean

) : GameSpawnerType {
    override fun nameTranslation(): Translation {
        return nameTranslation
    }

    override fun material(): ItemTypeHolder {
        return material
    }

    override fun color(): NamedTextColor {
        return color
    }

    override fun gameType(): GameType {
        return gameType
    }

    override fun settings(): GameSpawnerSettings {
        return settings
    }

    override fun enabled(): Boolean {
        return enabled
    }

    override fun item(amount: Int, target: PlayerWrapper?): Item {
        val builder = ItemFactory.builder()
            .type(material)

        if (target == null) {
            builder.displayName(AdventureHelper.toComponent(name))
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
            .setMaterial(material().platformName())
            .setSettings(settings.asProto())
            .setType(gameType)
            .build()
    }
}