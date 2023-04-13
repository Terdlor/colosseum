package com.terdev.colosseum

import org.junit.Test


internal class RandomizerTest {

    @Test
    fun getDiceRoll() {
        println(Randomizer().getDiceRoll(4))
        println(Randomizer().getDiceRoll(6))
        println(Randomizer().getDiceRoll(8))
        println(Randomizer().getDiceRoll(10))
        println(Randomizer().getDiceRoll(12))
        println(Randomizer().getDiceRoll(20))
    }

    @Test
    fun getChanceRoll() {
        for (i in 1..100) {
            println(Randomizer().getChanceRoll(50))
        }
    }
}