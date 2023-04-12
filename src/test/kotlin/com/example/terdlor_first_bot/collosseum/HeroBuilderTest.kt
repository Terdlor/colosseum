package com.example.terdlor_first_bot.collosseum

import org.junit.Test

internal class HeroBuilderTest {

    @Test
    fun getHeroName() {
        HeroClassType.values().forEach {
            println(HeroBuilder.getHeroName(it))
        }

        for (i in 1..100) {
            println(HeroBuilder.getHeroName(HeroClassType.BARBARIAN))
        }
    }

    @Test
    fun getHero() {
        HeroClassType.values().forEach {
            println(HeroBuilder.getHero(10, it))
            println()
        }

        /*
        for (i in 1..100) {
            println(HeroBuilder.getHero(i, HeroClassType.PALADIN))
            println()
        }
        */
    }

    @Test
    fun getAttributeModifier() {
        val level = 7
        val luck = 1
        var str = 0
        var dex = 0
        var int = 0
        if (level % 2 == 0) {
            for (i in 1..(level / 2)) {
                when (Randomizer().getDiceRoll(3)) {
                    1 -> str += luck * 2
                    2 -> dex += luck * 2
                    else -> int += luck * 2
                }
            }
        }
        println("str = $str")
        println("dex = $dex")
        println("int = $int")
    }

    @Test
    fun getHeroLuck() {
        var luck: Int
        for (i in 1..100) {
            when (Randomizer().getPercentage()) {
                in 1..5 -> luck = 4
                in 6..20 -> luck = 3
                in 21..50 -> luck = 2
                else -> luck = 1
            }
            println("luck = $luck")
        }
    }
}