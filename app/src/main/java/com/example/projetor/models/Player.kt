package com.example.projetor.models

import kotlin.random.Random

class Player(val name: String){
    private var hp: Int = 50
    private var xp: Int = 0
    private var atk: Int = 10
    private var lvl: Int = 1
    private var gold: Int = 0
    private val inventory = mutableListOf<Item>()
    private val equipedItems = mutableListOf<Item>()

    fun gainXp(earnedXp: Int) {
        xp += earnedXp

        if(xp >= 15){
            val excess = xp - 15 //armazena o excesso de xp
            xp = excess // reseta o xp e deixa o restante da progressão
            lvl++
        }
    }

    fun addItemToInventory(item: Item){
        inventory.add(item)
    }

    fun attackEnemy(enemy: Monster){
        val dice1 = Random.nextInt(1,6)
        val dice2 = Random.nextInt(1,6)

        atk += dice1 + dice2

        if(atk > enemy.atk){
            enemy.hp -= atk
        } else if(atk < enemy.atk){
            hp -= enemy.atk
        } else {
            return
        }
    }

    fun addItemStatus(item: Item){
        hp += item.extraHp
        atk += item.atk
    }

    fun gainGold(receivedGold: Int){
        gold += receivedGold
    }
}