package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.GameType
import net.hoz.api.data.game.ProtoSpawnerType
import net.hoz.gamecore.api.util.GUtil
import org.screamingsandals.lib.item.Item
import org.screamingsandals.lib.item.ItemTypeHolder
import org.screamingsandals.lib.lang.Translation
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.utils.Nameable

interface GameSpawnerType : Nameable, ProtoWrapper<ProtoSpawnerType> {
    /**
     * Translation key for the spawner
     */
    val nameTranslation: Translation

    /**
     * Material of the spawner
     */
    val material: ItemTypeHolder

    /**
     * Color of the text
     */
    val color: Color

    /**
     * Type of the game
     */
    val gameType: GameType

    /**
     * Settings of this type
     */
    val settings: GameSpawnerSettings

    /**
     * If this type is enabled or not
     */
    val enabled: Boolean

    /**
     * Gets an item for spawning.
     *
     * @param amount amount to get
     * @param target target player - used for translations
     * @return [Item] built item.
     */
    fun item(amount: Int, target: PlayerWrapper?): Item

    /**
     * Gets one item.
     *
     * @return [Item] built item.
     */
    fun item(): Item {
        return item(1)
    }

    /**
     * Gets item with given amount.
     *
     * @param amount amount of items in stack
     * @return [Item] built item.
     */
    fun item(amount: Int): Item {
        return item(amount, null)
    }

    /**
     * Gets item with given target for translation.
     *
     * @param target target for translation
     * @return [Item] built item.
     */
    fun item(target: PlayerWrapper?): Item {
        return item(1, target)
    }

    companion object {
        fun of(
            nameTranslation: Translation,
            name: String,
            material: ItemTypeHolder,
            color: Color,
            gameType: GameType,
            settings: GameSpawnerSettings,
            enabled: Boolean
        ): GameSpawnerType =
            GameSpawnerTypeImpl(
                nameTranslation,
                name,
                material,
                color,
                gameType,
                settings,
                enabled
            )

        fun of(type: ProtoSpawnerType): DataResultable<GameSpawnerType> =
            GameSpawnerSettings.of(type.settings)
                .map {
                    GameSpawnerTypeImpl(
                        Translation.of(type.nameTranslation),
                        type.name,
                        ItemTypeHolder.of(type.material),
                        GUtil.fromProtoColor(type.color),
                        type.type,
                        it,
                        true //TODO
                    )
                }
    }
}