package com.example.projetor

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.projetor.databinding.ActivityMainBinding
import com.example.projetor.models.Player


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.getStringExtra("playerName")?.let { Player(it) }

        val text: TextView = binding.text
        text.text = player?.name
    }
}