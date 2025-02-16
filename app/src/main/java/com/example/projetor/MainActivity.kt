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
import android.os.Handler
import android.os.Looper


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

        val playerName = intent.getStringExtra("playerName") ?: "Jogador"
        val player = Player(playerName, R.drawable.player_img)
        playerController = PlayerController(this, player)

        var monster = drawMonster(monsters)
        val battleButton = binding.battleButton
        val diceArea = binding.diceArea
        val potionButton = binding.potionButton
        val potionCount = binding.potionBadge
        diceArea.visibility = View.INVISIBLE
        potionCount.text = "${player.potions.size}"

        updatePlayerData(player)

        updateEnemyData(monster)

        battleButton.setOnClickListener {
            battleButton.visibility = View.GONE
            val dice1 = Random.nextInt(1, 6)
            val dice2 = Random.nextInt(1, 6)

            // Exibe os dados e aguarda 1.5 segundos antes de continuar
            diceArea.visibility = View.VISIBLE
            diceArea.text = "Dado 1: $dice1 \nDado 2: $dice2"

            Handler(Looper.getMainLooper()).postDelayed({
                diceArea.visibility = View.GONE
                playerController.attackEnemy(monster, dice1, dice2)

                if (monster.hp <= 0) {
                    playerController.earnBattleXp(monster)
                    monster = drawMonster(monsters)
                    monster.adjustStatus(player)
                }

                updatePlayerData(player)
                updateEnemyData(monster)

                // Torna o botão visível novamente após um atraso
                Handler(Looper.getMainLooper()).postDelayed({
                    battleButton.visibility = View.VISIBLE
                }, 1000) // 1 segundo de atraso para reativar o botão
            }, 1500) // 1.5 segundos de atraso para mostrar os dados antes da batalha continuar
        }

        potionButton.setOnClickListener{
            playerController.usePotion(player.potions[0])
            potionCount.text = "${player.potions.size}"
            val playerHp = binding.playerHp
            playerHp.text = "${player.hp}"
        }
    }

    fun drawMonster(monsterList: List<Monster>): Monster{
        return monsterList.random()
    }

    @SuppressLint("SetTextI18n")
    fun updatePlayerData(player: Player){
        val sideBar: TextView = binding.sideBar
        val playerHp: TextView = binding.playerHp
        val playerXp: TextView = binding.xpBar
        val maxHp = player.hp
        sideBar.text = " ${player.name}\n " +
                "Level: ${player.lvl}\n " +
                "Atk: ${player.atk} \n " +
                "Gold: ${player.gold}"
        playerXp.text = "${player.xp}/ 15"
        playerHp.text = player.hp.toString()
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
        monsterHp.text = monster.hp.toString()
        monsterLvl.text = "Lvl: ${monster.lvl}"
    }
}