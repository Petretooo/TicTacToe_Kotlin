package com.example.tictactoe_petar_angelov.gameComponents

import java.util.*

class Board {
    private val gameBoardMATRIX = Array(3) { CharArray(3) }
    private var counter = 1

    constructor(){
        for(i in 0..2){
            for(j in 0..2){
                gameBoardMATRIX[i][j] = '0';
            }
        }
    }


    @JvmField
    var gameOverCHECK = false
    fun copyGameBoard(): Board {
        val copyBoardMATRIX = Board()
        copyBoardMATRIX.gameOverCHECK = gameOverCHECK
        copyBoardMATRIX.counter = counter
        for (i in 0..2) {
            System.arraycopy(gameBoardMATRIX[i], 0, copyBoardMATRIX.gameBoardMATRIX[i], 0, 3)
        }
        return copyBoardMATRIX
    }

    fun setOnPosition(position: Int) {
        if (!gameOverCHECK) {
            var score = ' '
            if (currentPlayer == "You") {
                score = 'x'
            } else if (currentPlayer == "AI") {
                score = 'o'
            }
            if (position == 1) {
                gameBoardMATRIX[0][0] = score
            } else if (position == 2) {
                gameBoardMATRIX[0][1] = score
            } else if (position == 3) {
                gameBoardMATRIX[0][2] = score
            } else if (position == 4) {
                gameBoardMATRIX[1][0] = score
            } else if (position == 5) {
                gameBoardMATRIX[1][1] = score
            } else if (position == 6) {
                gameBoardMATRIX[1][2] = score
            } else if (position == 7) {
                gameBoardMATRIX[2][0] = score
            } else if (position == 8) {
                gameBoardMATRIX[2][1] = score
            } else if (position == 9) {
                gameBoardMATRIX[2][2] = score
            }
            counter++
            if (gameResult != 66666) {
                gameOverCHECK = true
            }
        }
    }

    val gameResult: Int
        get() {
            for (i in 0..2) {
                if (gameBoardMATRIX[i][0] == 'o' && gameBoardMATRIX[i][1] == 'o' && gameBoardMATRIX[i][2] == 'o') {
                    return 2
                } else if (gameBoardMATRIX[i][0] == 'x' && gameBoardMATRIX[i][1] == 'x' && gameBoardMATRIX[i][2] == 'x') {
                    return 1
                }
            }
            for (i in 0..2) {
                if (gameBoardMATRIX[0][i] == 'o' && gameBoardMATRIX[1][i] == 'o' && gameBoardMATRIX[2][i] == 'o') {
                    return 2
                } else if (gameBoardMATRIX[0][i] == 'x' && gameBoardMATRIX[1][i] == 'x' && gameBoardMATRIX[2][i] == 'x') {
                    return 1
                }
            }
            if (gameBoardMATRIX[0][0] == 'x' && gameBoardMATRIX[1][1] == 'x' && gameBoardMATRIX[2][2] == 'x' ||
                    gameBoardMATRIX[2][0] == 'x' && gameBoardMATRIX[1][1] == 'x' && gameBoardMATRIX[0][2] == 'x') {
                return 1
            } else if (gameBoardMATRIX[0][0] == 'o' && gameBoardMATRIX[1][1] == 'o' && gameBoardMATRIX[2][2] == 'o' ||
                    gameBoardMATRIX[2][0] == 'o' && gameBoardMATRIX[1][1] == 'o' && gameBoardMATRIX[0][2] == 'o') {
                return 2
            }
            var checkIsThereZERO = false
            for (i in 0..2) {
                for (j in 0..2) {
                    if (gameBoardMATRIX[i][j] == '0') {
                        checkIsThereZERO = true
                    }
                }
            }
            return if (!checkIsThereZERO) {
                0
            } else 66666
        }
    val currentPlayer: String
        get() = if (counter % 2 == 0) "AI" else "You"

    fun availablePositions(): List<Int> {
        val list: MutableList<Int> = ArrayList()
        var counterPosition = 1
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoardMATRIX[i][j] == '0') {
                    list.add(counterPosition)
                }
                counterPosition++
            }
        }
        return list
    }
}