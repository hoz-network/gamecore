package net.hoz.gamecore.api.game.frame

import org.screamingsandals.lib.hologram.Hologram
import java.util.UUID

interface FrameVisuals {

    val teams: Teams
    val stores: Stores

    fun update()

    sealed interface Teams {
        val activeHolograms: List<Hologram>

        fun update()
    }

    interface Stores {

    }
}