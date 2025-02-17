package com.example.projetor.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projetor.R
import com.example.projetor.models.Player

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "projetoR.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PLAYER = "player"

        private const val COLUMN_NAME = "name"
        private const val COLUMN_HP = "hp"
        private const val COLUMN_XP = "xp"
        private const val COLUMN_LVL = "lvl"
        private const val COLUMN_ATK = "atk"
        private const val COLUMN_GOLD = "gold"
    }

    override fun onCreate(db: SQLiteDatabase?){
        val CREATE_PLAYER_TABLE = """
            CREATE TABLE $TABLE_PLAYER (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_HP INTEGER,
                $COLUMN_XP INTEGER,
                $COLUMN_LVL INTEGER,
                $COLUMN_ATK INTEGER,
                $COLUMN_GOLD INTEGER
            )
        """
        db?.execSQL(CREATE_PLAYER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PLAYER")
        onCreate(db)
    }

    fun savePlayer(player: Player) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, player.name)
            put(COLUMN_HP, player.hp)
            put(COLUMN_XP, player.xp)
            put(COLUMN_LVL, player.lvl)
            put(COLUMN_ATK, player.atk)
            put(COLUMN_GOLD, player.gold)
        }

        db.execSQL("REPLACE INTO $TABLE_PLAYER VALUES (1, ?, ?, ?, ?, ?, ?)",
            arrayOf(player.name, player.hp, player.xp, player.lvl, player.atk, player.gold)
        )

        db.close()
    }


    fun loadPLayer(): Player? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PLAYER,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if(cursor != null && cursor.moveToFirst()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val hp = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HP))
            val xp = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_XP))
            val lvl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LVL))
            val atk = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ATK))
            val gold = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GOLD))
            cursor.close()

            return Player(name, imgId = R.drawable.player_img).apply {
                this.hp = hp
                this.xp = xp
                this.lvl = lvl
                this.atk = atk
                this.gold = gold
            }
         }
        cursor.close()
        return null
    }
}