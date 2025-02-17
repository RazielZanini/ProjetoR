package com.example.projetor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projetor.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playAgainButton: Button = binding.playAgain
        val quitButton: Button = binding.quit
        val playerName = intent.getStringExtra("playerName")

        playAgainButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("playerName", playerName)
            startActivity(intent)
        }

        quitButton.setOnClickListener{
            finishAffinity()
        }
    }
}