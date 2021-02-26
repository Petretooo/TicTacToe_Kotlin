package com.example.tictactoe_petar_angelov

import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ScoreActivity : AppCompatActivity(), View.OnClickListener {
    interface OnSelectElement {
        fun OnElementIterate(Name: String, Score: String, ID: String)
    }

    private var list: ListView? = null
    private var textView: TextView? = null
    private var deleteButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        textView = findViewById(R.id.textScore)
        textView?.setMovementMethod(ScrollingMovementMethod())
        deleteButton = findViewById(R.id.buttonDelete)
        populateList()
        deleteButton?.setOnClickListener(View.OnClickListener {
            try {
                TruncateQuery()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
            populateList()
        })
    }

    private fun populateList() {
        var listResults: List<String?>? = null
        var textS: String? = ""
        try {
            listResults = SelectQuery()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (s in listResults!!) {
            textS += s
        }
        textView!!.text = textS
        list = findViewById(R.id.simpleList)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonPlay -> try {
                TruncateQuery()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }
    }

    @Throws(Exception::class)
    fun SelectQuery(): List<String?> {
        val listRes: MutableList<String?> = ArrayList()
        listRes.add("""
    ID              Username              Score
    
    """.trimIndent())
        listRes.add("""
    ______________________________________
    
    """.trimIndent())
        SelectSQL(
                "SELECT * FROM GAME;",
                null,
                object : OnSelectElement {
                    override fun OnElementIterate(Username: String, Score: String, ID: String) {
                        listRes.add("$ID.\t     |    $Username\t     |    $Score\n")
                    }
                }
        )
        return listRes
    }

    @Throws(Exception::class)
    fun TruncateQuery(): List<String> {
        val listRes: List<String> = ArrayList()
        ExecSQL(
                "DELETE FROM GAME;",
                null
        )
        return listRes
    }

    @Throws(Exception::class)
    fun SelectSQL(SQL: String?, args: Array<String?>?, onSelectElement: OnSelectElement) {
        val db = SQLiteDatabase.openOrCreateDatabase(
                filesDir.path + "/GAME.db",
                null
        )
        val cursor = db.rawQuery(SQL, args)
        while (cursor.moveToNext()) {
            val ID = cursor.getString(cursor.getColumnIndex("ID"))
            val Username = cursor.getString(cursor.getColumnIndex("Username"))
            val Score = cursor.getString(cursor.getColumnIndex("Score"))
            onSelectElement.OnElementIterate(Username, Score, ID)
        }
        db.close()
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
}