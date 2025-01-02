package com.nenroin.seabattleapp.model

data class GameBoard(
    val size: Int = 10,
    val cells: Array<Array<Cell>> = Array(size) { Array(size) { Cell() } }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameBoard

        if (size != other.size) return false
        if (!cells.contentDeepEquals(other.cells)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + cells.contentDeepHashCode()
        return result
    }
}