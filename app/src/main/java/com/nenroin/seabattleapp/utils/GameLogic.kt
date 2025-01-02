package com.nenroin.seabattleapp.utils

import com.nenroin.seabattleapp.model.GameBoard
import com.nenroin.seabattleapp.model.Ship

class GameLogic {
    fun placeShip(board: GameBoard, ship: Ship): Boolean {
        for ((x, y) in ship.coordinates) {
            if (board.cells[x][y].hasShip) return false
        }
        for ((x, y) in ship.coordinates) {
            board.cells[x][y].hasShip = true
        }
        return true
    }

    fun attackCell(board: GameBoard, x: Int, y: Int): Boolean {
        if (board.cells[x][y].isHit) return false
        board.cells[x][y].isHit = true
        return board.cells[x][y].hasShip
    }
}