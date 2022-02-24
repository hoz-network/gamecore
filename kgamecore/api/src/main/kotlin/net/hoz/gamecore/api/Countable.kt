package net.hoz.gamecore.api

/**
 * Something that can have a count
 */
interface Countable {
    /**
     * size of the countable
     */
    fun count(): Int
}