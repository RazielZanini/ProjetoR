package com.example.projetor

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projetor.databinding.ActivityMenuBinding
import androidx.compose.material3.AlertDialog
import com.example.projetor.database.DatabaseHelper

class MenuActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

       try{
           mediaPlayer = MediaPlayer.create(this, R.raw.menu_music)?.apply {
               isLooping = true
               start()
           }
       } catch(e: Exception){
           e.printStackTrace()
       }

        val newGameButton: Button = binding.newGame
        val loadGameButton: Button = binding.loadGame
        val quitButton: Button = binding.quitApp

        if(isDatabaseEmpty()){
            loadGameButton.isEnabled = false
        } else {
            loadGameButton.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("LOAD_GAME", true)
                startActivity(intent)
            }
        }

        newGameButton.setOnClickListener{
            showNameInputDialog()
        }

        quitButton.setOnClickListener{
            finishAffinity()
        }

    }

    //função que mostra o input de nome do jogador e envia a informação para outra tela
    private fun showNameInputDialog(){
        val dialogView = layoutInflater.inflate(R.layout.new_game, null)

        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Nome do jogador")
            .setView(dialogView)
            .setPositiveButton("Ok") {dialog, _ ->
                val playerName = dialogView.findViewById<EditText>(R.id.enterPlayerName).text.toString()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("playerName", playerName)
                startActivity(intent)

                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") {dialog, _ ->
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    private fun isDatabaseEmpty(): Boolean {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM player", null)

        var isEmpty = true
        if(cursor.moveToFirst()){
            isEmpty = cursor.getInt(0) == 0
        }

        cursor.close()
        db.close()
        return isEmpty
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.release()
        mediaPlayer = null
    }
}