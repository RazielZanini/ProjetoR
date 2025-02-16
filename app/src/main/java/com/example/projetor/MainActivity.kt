package com.example.projetor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.projetor.controllers.PlayerController
import com.example.projetor.databinding.ActivityMainBinding
import com.example.projetor.models.Monster
import com.example.projetor.models.Player
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var playerController: PlayerController
    val monsters = listOfNotNull(
        Monster("Weird Dwarf",R.drawable.monster1),
        Monster("Big Eye", R.drawable.monster2),
        Monster("Weird Snake", R.drawable.monster3),
        Monster("Weird Bat", R.drawable.monster4),
        Monster("Unknow", R.drawable.monster5),
        Monster("Weird Slime", R.drawable.monster6)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.getStringExtra("playerName")?.let { Player(it, R.drawable.player_img) }
        playerController = player?.let { PlayerController(it) }!!
        val monster = drawMonster(monsters)
        val battleButton = binding.battleButton
        val diceArea = binding.diceArea
        diceArea.visibility = View.INVISIBLE

        updatePlayerData(player)

        updateEnemyData(monster)

        battleButton.setOnClickListener{
            battleButton.visibility = View.GONE
            val dice1 = Random.nextInt(1, 6)
            val dice2 = Random.nextInt(1, 6)
            diceArea.visibility = View.VISIBLE
            diceArea.text = "Dado 1: $dice1 \n" +
                    "Dado 2: $dice2"
            playerController.attackEnemy(monster, dice1, dice2)
            diceArea.visibility = View.GONE
            updatePlayerData(player)
            updateEnemyData(monster)
        }
    }

    fun drawMonster(monsterList: List<Monster>): Monster{
        return monsterList.get(Random.nextInt(0,6))
    }

    @SuppressLint("SetTextI18n")
    fun updatePlayerData(player: Player){
        val sideBar: TextView = binding.sideBar
        val playerHp: TextView = binding.playerHp
        val maxHp = player.hp
        val playerXp: TextView = binding.xpBar
        sideBar.text = " ${player.name}\n " +
                "Level: ${player.lvl}\n " +
                "Atk: ${player.atk} \n " +
                "Gold: ${player.gold}"
        playerXp.text = "${player.xp}/ 15"
        playerHp.text = "${player.hp} / $maxHp"
    }

    @SuppressLint("SetTextI18n")
    fun updateEnemyData(monster: Monster){
        val monsterName = binding.monsterName
        val monsterImage = binding.monster
        val monsterLvl = binding.monsterLvl
        val monsterHp = binding.monsterHp
        val monsterMaxHP = monster.hp
        monsterImage.setImageResource(monster.imageResId)
        monsterName.text = monster.name
        monsterHp.text = "${monster.hp} / $monsterMaxHP"
        monsterLvl.text = "Lvl: ${monster.lvl}"
    }
}