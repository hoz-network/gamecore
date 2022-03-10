package net.hoz.gamecore.api.event.player

import org.screamingsandals.lib.kotlin.SCancellableEventKt
import java.util.*

data class GamePlayerPreRegisterEvent(
    val uuid: UUID,
    override var cancelled: Boolean = false
) : SCancellableEventKt