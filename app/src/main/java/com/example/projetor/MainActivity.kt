package com.example.projetor

import android.annotation.SuppressLint
import android.content.Intent
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
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.projetor.database.DatabaseHelper


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var playerController: PlayerController
    private val monsters = listOfNotNull(
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

        val dbHelper = DatabaseHelper(this)
        val loadGame = intent.getBooleanExtra("LOAD_GAME", false)

        //verifica qual a intent recebida
        val player: Player = if (loadGame) {
            dbHelper.loadPLayer() ?: Player("Jogador", R.drawable.player_img)
        } else {
            Player(intent.getStringExtra("playerName") ?: "Jogador", R.drawable.player_img)
        }

        playerController = PlayerController(this, player)

        var monster: Monster = drawMonster(monsters, player)
        val battleButton: ImageButton = binding.battleButton
        val diceArea: TextView = binding.diceArea
        val potionButton: ImageButton = binding.potionButton
        val potionCount: TextView = binding.potionBadge
        val saveButton: Button = binding.save
        val quitButton: Button = binding.quit

        diceArea.visibility = View.INVISIBLE
        potionCount.text = "${player.potions.size}"

        updatePlayerData(player)

        updateEnemyData(monster)

        //Evento ao clicar no botão de batalha
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
                    monster = drawMonster(monsters, player)
                }

                updatePlayerData(player)
                updateEnemyData(monster)

                // Torna o botão visível novamente após um atraso
                Handler(Looper.getMainLooper()).postDelayed({
                    battleButton.visibility = View.VISIBLE
                }, 1000) // 1 segundo de atraso para reativar o botão
            }, 1500) // 1.5 segundos de atraso para mostrar os dados antes da batalha continuar
        }

        //Evento ao clicar no botão de poção
        potionButton.setOnClickListener{
           if(player.potions.size > 0){
               playerController.usePotion(player.potions[0])
               potionCount.text = "${player.potions.size}"
               val playerHp = binding.playerHp
               playerHp.text = "${player.hp}"
           } else{
               Toast.makeText(this, "Você não possui mais poções", Toast.LENGTH_SHORT).show()
           }
        }

        //Evento ao clicar no botão de salvar
        saveButton.setOnClickListener{
            try{
                dbHelper.savePlayer(player)
                Toast.makeText(this, "Jogo salvo com sucesso!", Toast.LENGTH_SHORT).show()
            } catch(e: Exception){
                Toast.makeText(this, "Erro ao salvar progresso!", Toast.LENGTH_SHORT).show()
            }
        }

        //Evento ao clicar no botão de sait
        quitButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    //Sorteia um novo monstro e ajusta os status do mesmo para o nivel atual do jogador
    fun drawMonster(monsterList: List<Monster>, player: Player): Monster{
        val monster: Monster = monsterList.random()
        monster.adjustStatus(player)
        return monster
    }

    //atualiza as informações do jogador na UI
    @SuppressLint("SetTextI18n")
    fun updatePlayerData(player: Player){
        val sideBar: TextView = binding.sideBar
        val playerHp: TextView = binding.playerHp
        val playerXp: TextView = binding.xpBar
        sideBar.text = " ${player.name}\n " +
                "Level: ${player.lvl}\n " +
                "Atk: ${player.atk} \n " +
                "Gold: ${player.gold}"
        playerXp.text = "${player.xp}/ 15"
        playerHp.text = player.hp.toString()
    }

    //atualiza as informações do monstro na UI
    @SuppressLint("SetTextI18n")
    fun updateEnemyData(monster: Monster){
        val monsterName = binding.monsterName
        val monsterImage = binding.monster
        val monsterLvl = binding.monsterLvl
        val monsterHp = binding.monsterHp
        val monsterAtk = binding.monsterAtk
        monsterImage.setImageResource(monster.imageResId)
        monsterName.text = monster.name
        monsterHp.text = monster.hp.toString()
        monsterLvl.text = "Lvl: ${monster.lvl}"
        monsterAtk.text = "Atk: ${monster.atk}"
    }
}