package com.example.projetor.models

class Player(val name: String, imgId: Int){
    var hp: Int = 50
        private set
    var xp: Int = 0
        private set
    var atk: Int = 10
        private set
    var lvl: Int = 1
        private set
    var gold: Int = 0
        private set
    val potions = mutableListOf<Item>()

    fun gainXp(earnedXp: Int) {
        xp += earnedXp

        if(xp >= 15){
            val excess = xp - 15 //armazena o excesso de xp
            xp = excess // reseta o xp e deixa o restante da progressão
            lvl++
        }
    }

    fun takeDamage(damage: Int) {
        hp -= damage
        if(hp < 0) hp = 0
    }

    fun gainGold(receivedGold: Int){
        gold += receivedGold
    }

    fun addItem(potion: Item){
        potions.add(potion)
    }

}