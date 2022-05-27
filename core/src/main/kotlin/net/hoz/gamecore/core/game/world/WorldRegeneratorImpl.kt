package net.hoz.gamecore.core.game.world

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import net.hoz.gamecore.api.game.world.WorldRegenerator
import org.screamingsandals.lib.block.BlockHolder
import org.screamingsandals.lib.block.BlockMapper
import org.screamingsandals.lib.block.BlockTypeHolder
import org.screamingsandals.lib.world.LocationHolder

internal class WorldRegeneratorImpl : WorldRegenerator {
    private val registeredBlocks: Multimap<WorldRegenerator.Type, Pair<LocationHolder, BlockTypeHolder>> =
        ArrayListMultimap.create()

    override fun regenerate() {
        registeredBlocks[WorldRegenerator.Type.BUILT]
            .forEach { loadAndReplace(it.first, BlockTypeHolder.air()) }
        registeredBlocks[WorldRegenerator.Type.DESTROYED]
            .forEach { loadAndReplace(it.first, it.second) }
        registeredBlocks[WorldRegenerator.Type.INTERACTED]
            .forEach { loadAndReplace(it.first, it.second) }
    }

    override fun addBlock(type: WorldRegenerator.Type, block: BlockHolder) {
        registeredBlocks.put(type, Pair(block.location, block.type))
    }

    override fun removeBlock(type: WorldRegenerator.Type, block: BlockHolder) {
        removeBlock(type, block.location)
    }

    override fun removeBlock(type: WorldRegenerator.Type, location: LocationHolder) {
        registeredBlocks.get(type).removeIf { it.first == location }
    }

    override fun wasBlockAddedDuringGame(holder: LocationHolder): Boolean =
        registeredBlocks[WorldRegenerator.Type.BUILT].any { it.first == holder }

    private fun loadAndReplace(location: LocationHolder, holder: BlockTypeHolder) {
        val chunk = location.chunk
        if (!chunk.isLoaded) {
            chunk.load()
        }
        BlockMapper.setBlockAt(location, holder, true)
    }
}