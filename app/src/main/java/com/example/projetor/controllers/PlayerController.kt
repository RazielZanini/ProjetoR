package com.example.projetor.controllers

import com.example.projetor.models.Item
import com.example.projetor.models.Monster
import com.example.projetor.models.Player
import kotlin.random.Random

class PlayerController(private val player: Player) {

    fun attackEnemy(enemy: Monster, dice1: Int, dice2: Int){
        val totalAtk = player.atk + dice1 + dice2

        if(player.atk > enemy.atk){
            enemy.takeDamage(totalAtk)
        } else if(player.atk < enemy.atk){
            player.takeDamage(enemy.atk)
        }
    }
}