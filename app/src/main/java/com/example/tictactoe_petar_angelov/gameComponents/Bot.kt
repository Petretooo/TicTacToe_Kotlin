package com.example.tictactoe_petar_angelov.gameComponents

import com.example.tictactoe_petar_angelov.gameComponents.Board
import com.example.tictactoe_petar_angelov.gameComponents.Bot
import com.example.tictactoe_petar_angelov.PlayActivity

class Bot {
    @JvmField
    var position = 0
    private var score = 0

    constructor() {}
    private constructor(score: Int) {
        this.score = score
    }

    private constructor(score: Int, position: Int) {
        this.score = score
        this.position = position
    }

    private fun score(game: Board): Bot {
        val score: Int
        score = when (game.gameResult) {
            0 -> 0
            1 -> 1
            2 -> -1
            else -> 999
        }
        return Bot(score)
    }

    fun findingPerfectPosition(game: Board): Bot {
        game.gameResult
        return if (game.gameOverCHECK) {
            PlayActivity.counter++
            score(game)
        } else {
            var currentScore = 0
            var bestScoreForMove = 0
            var bestMove = 0
            var gameCopy: Board
            if (game.currentPlayer == "You") {
                bestScoreForMove = -666
            } else if (game.currentPlayer == "AI") {
                bestScoreForMove = 666
            }
            val moves: List<Int?> = game.availablePositions()
            for (i in moves.indices) {
                gameCopy = game.copyGameBoard()
                gameCopy.setOnPosition(moves[i]!!)
                currentScore = findingPerfectPosition(gameCopy).score
                if (game.currentPlayer == "You") {
                    if (currentScore > bestScoreForMove) {
                        bestScoreForMove = currentScore
                        bestMove = moves[i]!!
                    }
                } else {
                    if (currentScore < bestScoreForMove) {
                        bestScoreForMove = currentScore
                        bestMove = moves[i]!!
                    }
                }
            }
            Bot(bestScoreForMove, bestMove)
        }
    }
}