package com.example.projetor.controllers

import android.content.Context
import android.widget.Toast
import com.example.projetor.models.Item
import com.example.projetor.models.Monster
import com.example.projetor.models.Player

class PlayerController(private val context: Context, private val player: Player) {

    //Função de atacar inimigo
    fun attackEnemy(enemy: Monster, dice1: Int, dice2: Int){
        val totalAtk = player.atk + dice1 + dice2

        if(totalAtk > enemy.atk){
            enemy.takeDamage(totalAtk)
            Toast.makeText(context, "Inimigo sofreu $totalAtk de dano", Toast.LENGTH_SHORT).show()
        } else if(totalAtk < enemy.atk){
            player.takeDamage(enemy.atk - totalAtk)
            Toast.makeText(context, "Jogador sofreu ${enemy.atk - totalAtk} de dano", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Empate",Toast.LENGTH_SHORT).show()
        }
    }

    //função que atribui o XP para o jogador
    fun earnBattleXp(monster: Monster){
        val gainedXp = monster.lvl * 3

        player.gainXp(gainedXp)

        Toast.makeText(context, "Jogador recebeu $gainedXp de experiência", Toast.LENGTH_SHORT).show()
    }

    //função para beber poção
    fun usePotion(potion: Item){

        if(player.hp == player.hpGap[player.lvl]){
            Toast.makeText(context, "Sua vida já está cheia", Toast.LENGTH_SHORT).show()
        } else {
            player.usePotion(potion)
        }

    }
}