package net.hoz.gamecore.core.game.spawner

import net.hoz.api.data.GameType
import net.hoz.gamecore.api.game.spawner.GameSpawnerSettingsBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.kyori.adventure.text.format.NamedTextColor
import org.mockito.Mockito
import org.screamingsandals.lib.item.ItemTypeHolder
import org.screamingsandals.lib.lang.Translation

class SpawnerTestUtils {
    companion object {
        fun prepareSpawnerType(): GameSpawnerType {
            val item = Mockito.mock(ItemTypeHolder::class.java)

            val settings = GameSpawnerSettingsBuilder.default()
            return GameSpawnerType.of(
                Translation.of("test"),
                "test",
                item,
                NamedTextColor.AQUA,
                GameType.BEDWARS,
                settings,
                true
            )
        }
    }
}