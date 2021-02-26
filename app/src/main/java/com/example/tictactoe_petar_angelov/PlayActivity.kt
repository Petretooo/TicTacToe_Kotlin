package com.example.tictactoe_petar_angelov

import android.annotation.SuppressLint
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe_petar_angelov.gameComponents.Board
import com.example.tictactoe_petar_angelov.gameComponents.Bot

class PlayActivity : AppCompatActivity(), View.OnClickListener {
    private var tableLayout: TableLayout? = null
    private var textView: TextView? = null
    private var gameBoard: Board? = null
    private var usernameText: String? = null
    private var givingBotControl = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val intent = intent
        usernameText = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
        gameBoard()
        tableLayout!!.setBackgroundColor(Color.WHITE)
        tableLayout!!.background.alpha = 120
        gameBoard = Board()
        textView = findViewById(R.id.textView)
        textView?.setTextColor(Color.WHITE)
        val resetButton = findViewById<Button>(R.id.buttonPlay)
        resetButton.setOnClickListener {
            tableLayout!!.removeAllViews()
            textView?.setText("")
            gameBoard()
            gameBoard = Board()
        }
    }

    private fun gameBoard() {
        tableLayout = findViewById(R.id.tableLayout)
        tableLayout?.setStretchAllColumns(true)
        var counter = 1
        for (row in 0..2) {
            val tableRow = TableRow(this)
            for (col in 0..2) {
                val button = Button(this)
                button.tag = counter
                button.setOnClickListener(this)
                tableRow.addView(button)
                counter++
            }
            tableLayout?.addView(tableRow, TableLayout.LayoutParams(-2, -2))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View) {
        val you = "X"
        val pc = "O"
        var place = ""
        if (!gameBoard!!.gameOverCHECK) {
            if (gameBoard!!.currentPlayer == "You") {
                place = you
            } else if (gameBoard!!.currentPlayer == "AI") {
                place = pc
            }
            val choice = Integer.valueOf(v.tag.toString())
            gameBoard!!.setOnPosition(choice)
            (v as Button).text = place
            if (gameBoard!!.gameResult == 1) {
                textView!!.text = "Result: You Won!"
                InsertInto("WON")
            } else if (gameBoard!!.gameResult == 2) {
                textView!!.text = "Result: AI Won!"
                InsertInto("LOSE")
            }
            if (!givingBotControl) {
                givingBotControl = true
                val position = Bot().findingPerfectPosition(gameBoard!!.copyGameBoard()).position
                counter = 0
                if (position != 0) {
                    tableLayout!!.findViewWithTag<View>(position).callOnClick()
                }
                givingBotControl = false
            }
        }
    }

    fun InsertInto(score: String?) {
        try {
            ExecSQL("INSERT INTO GAME(Username, Score)" +
                    "VALUES(?, ?)", arrayOf(
                    usernameText,
                    score
            ))
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }

    @Throws(SQLException::class)
    fun ExecSQL(SQL: String?, args: Array<Any?>?) {
        val db = SQLiteDatabase.openOrCreateDatabase(
                filesDir.path + "/GAME.db",
                null
        )
        if (args == null) {
            db.execSQL(SQL)
        } else {
            db.execSQL(SQL, args)
        }
        db.close()
    }

    companion object {
        var counter = 0
    }
}