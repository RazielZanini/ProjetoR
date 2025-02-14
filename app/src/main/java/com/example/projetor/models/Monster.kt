package com.example.projetor.models

class Monster(val name:String, val atk:Int, val def:Int, var hp:Int, val lvl: Int){

    fun takeDamage(damage: Int){
        hp -= damage

        if(hp < 0) hp = 0
    }
}