package com.example.tictactoe_petar_angelov

import android.content.Intent
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe_petar_angelov.MainActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var usernameText: EditText? = null
    var buttonPlay: Button? = null
    var buttonScore: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonScore = findViewById(R.id.buttonScore)
        usernameText = findViewById(R.id.usernameText)
        buttonPlay?.setOnClickListener(this)
        buttonScore?.setOnClickListener(this)
        try {
            initDb()
        } catch (e: SQLException) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonPlay -> if (!usernameText!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                playGame(view)
            }
            R.id.buttonScore -> viewScore(view)
            else -> {
            }
        }
    }

    fun playGame(view: View?) {
        val intent = Intent(this@MainActivity, PlayActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, usernameText!!.text.toString())
        startActivity(intent)
    }

    fun viewScore(view: View?) {
        val intent = Intent(this@MainActivity, ScoreActivity::class.java)
        startActivity(intent)
    }

    @Throws(SQLException::class)
    fun initDb() {
        val db = SQLiteDatabase.openOrCreateDatabase(
                filesDir.path + "/GAME.db", null
        )
        val createQuery = "CREATE TABLE if not exists GAME( " +
                "ID integer PRIMARY KEY AUTOINCREMENT, " +
                "Username text not null, " +
                "Score text" +
                " );"
        db.execSQL(createQuery)
        db.close()
    }

    companion object {
        const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }
}