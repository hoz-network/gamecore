package net.hoz.gamecore.api

import com.iamceph.resulter.core.DataResultable

interface ProtoBuildable<R, I> {

    fun fromProto(input: I): DataResultable<R>
}