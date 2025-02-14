package com.example.projetor.controllers

import com.example.projetor.models.Item
import com.example.projetor.models.Monster
import com.example.projetor.models.Player
import kotlin.random.Random

class PlayerController(private val player: Player) {

    fun attackEnemy(enemy: Monster){
        val dice1 = Random.nextInt(1,6)
        val dice2 = Random.nextInt(1,6)
        val totalAtk = player.atk + dice1 + dice2

        if(player.atk > enemy.atk){
            enemy.takeDamage(totalAtk)
        } else if(player.atk < enemy.atk){
            player.takeDamage(enemy.atk)
        }
    }

    fun equipItem(item: Item, itemIndex: Int){
        player.equipedItems[itemIndex]?.let { player.removeItemStatus(it) }
        player.equipedItems[itemIndex] = item
        player.addItemStatus(item)
    }


}