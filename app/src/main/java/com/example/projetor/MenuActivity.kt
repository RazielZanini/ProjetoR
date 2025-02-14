package com.example.projetor

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.AlertDialog
import com.example.projetor.databinding.ActivityMenuBinding
import com.example.projetor.models.Player

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

        newGameButton.setOnClickListener{
            showNameInputDialog()
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

                onDestroy()
            }
            .setNegativeButton("Cancelar") {dialog, _ ->
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.release()
        mediaPlayer = null
    }
}