package com.example.projetor.models

class Player(val name: String, imgId: Int){
    var hp: Int = 50
    var xp: Int = 0
    var atk: Int = 10
    var lvl: Int = 1
    var gold: Int = 0
    //O jogador começa com duas poções na bolsa
    val potions = mutableListOf<Item>(
        Item("Poção de vida", 10),
        Item("Poção de vida", 10)
    )

    //gap de xp gerados automaticamente
    val xpGap = generateSequence(0) { it + 5 }.take(10).toList()
    val hpGap = generateSequence(50) { it + 5 }.take(10).toList()

    fun gainXp(earnedXp: Int) {
        xp += earnedXp

        if(xp >= xpGap[lvl]){
            levelUp()
        }
    }

    private fun levelUp(){
        val excess = xp - xpGap[lvl] //armazena o excesso de xp
        xp = excess // reseta o xp e deixa o restante da progressão
        lvl++
        atk+=2
        hp = hpGap[lvl]
    }

    fun takeDamage(damage: Int) {
        hp -= damage
        if(hp < 0) hp = 0
    }

    fun gainGold(receivedGold: Int){
        gold += receivedGold
    }

    fun usePotion(potion: Item){
        if(hp + potion.lifeRegen > hpGap[lvl]){
            hp = hpGap[lvl]
            potions.remove(potion)
        } else{
            hp += potion.lifeRegen
            potions.remove(potion)
        }
    }

}