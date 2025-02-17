package com.example.projetor.models

class Monster(val name:String, val imageResId: Int){

    var atk:Int = 15
    var hp:Int = 50
    var lvl: Int = 1

    val hpGap = generateSequence(50) { it + 5 }.take(10).toList()

    fun takeDamage(damage: Int){
        hp -= damage

        if(hp < 0) hp = 0
    }

    fun adjustStatus(player: Player){
        lvl = player.lvl //o nivel do monstro será escalado conforme o nivel do jogador
        atk = player.atk + 5 // o ataque do monstro será sempre 5 pontos a mais do ataque do jogador
        hp = hpGap[lvl] //a vida do monstro escala de acordo com nível
    }
}