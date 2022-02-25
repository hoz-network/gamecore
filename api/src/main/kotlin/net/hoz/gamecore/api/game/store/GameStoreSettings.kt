package net.hoz.gamecore.api.game.store

import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameStore
import org.screamingsandals.lib.utils.Nameable

interface GameStoreSettings : Nameable, ProtoWrapper<ProtoGameStore>