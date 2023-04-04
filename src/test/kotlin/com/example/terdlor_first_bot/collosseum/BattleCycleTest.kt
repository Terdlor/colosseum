package com.example.terdlor_first_bot.collosseum

import org.junit.Test
import kotlin.math.round

internal class BattleCycleTest {

    private var battleComplete = false
    private var turn = 1
    private var damage = 0
    private var heal = 0
    private val playerA: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(8, 10), HeroClassType.values().random())
    private val playerB: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(7, 9), HeroClassType.values().random())

    @Test
    fun runBattle() {
        println("\nМогучие герои призваны на поле битвы!\n" +
                "\nИГРОК 1:" +
                "\n$playerA" +
                "\nИГРОК 2:" +
                "\n$playerB")

        while (!battleComplete) {
            if (turn % 2 != 0) {
                // Нечетные ходы совершает playerA
                if (!Randomizer().getChanceRoll(playerB.dodge)) {
                    damage = Randomizer().getRandomFromRange(playerA.damageMin, playerA.damageMax)
                    damage -= round(((damage / 100) * playerB.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(playerA.criticalChance)) {
                        damage *= 2
                        println("[ КРИТИЧЕСКИЙ УДАР! ]")
                    }
                    playerB.health -= damage
                    println("[ИГРОК 1] ${playerA.name} нанес $damage урона врагу.")
                }
                else {
                    println("[ИГРОК 2] ${playerB.name} увернулся от атаки!")
                }
                println("[ИГРОК 2] ${playerB.name} ЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
            }
            else {
                // Нечетные ходы совершает playerB
                if (!Randomizer().getChanceRoll(playerA.dodge)) {
                    damage = Randomizer().getRandomFromRange(playerB.damageMin, playerB.damageMax)
                    damage -= round(((damage / 100) * playerA.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(playerB.criticalChance)) {
                        damage *= 2
                        println("[ КРИТИЧЕСКИЙ УДАР! ]")
                    }
                    playerA.health -= damage
                    println("[ИГРОК 2] ${playerB.name} нанес $damage урона врагу.")
                }
                else {
                    println("[ИГРОК 1] ${playerA.name} увернулся от атаки!")
                }
                println("[ИГРОК 1] ${playerA.name} ЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
            }

            if (playerA.health <= 0) {
                heal = Randomizer().getRandomFromRange(playerB.level * 15, playerB.healthMax)
                if (playerB.health + heal > playerB.healthMax) {
                    playerB.health = playerB.healthMax
                }
                else {
                    playerB.health += heal
                }
                println("[ИГРОК 1 потерпел поражение в битве с ИГРОКОМ 2]" +
                        "\n[ИГРОК 1] ${playerA.name} [${playerA.classType}] ${playerA.level} уровня пал в битве!" +
                        "\n[ИГРОК 2] ${playerB.name} отдыхает и восстанавливает $heal здоровья." +
                        "\n[ИГРОК 2] ${playerB.name} ЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
                battleComplete = true
            }

            if (playerB.health <= 0) {
                heal = Randomizer().getRandomFromRange(playerA.level * 15, playerA.healthMax)
                if (playerA.health + heal > playerA.healthMax) {
                    playerA.health = playerA.healthMax
                }
                else {
                    playerA.health += heal
                }
                println("[ИГРОК 2 потерпел поражение в битве с ИГРОКОМ 1]" +
                        "\n[ИГРОК 2] ${playerB.name} [${playerB.classType}] ${playerB.level} уровня пал в битве!" +
                        "\n[ИГРОК 1] ${playerA.name} отдыхает и восстанавливает $heal здоровья." +
                        "\n[ИГРОК 1] ${playerA.name} ЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
                battleComplete = true
            }

            turn ++
        }
    }
}