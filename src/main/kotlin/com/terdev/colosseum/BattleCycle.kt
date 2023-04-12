package com.example.terdlor_first_bot.collosseum

import kotlin.math.round

class BattleCycle {
    private var battleComplete = false
    private var turn = 1
    private var damage = 0
    private var heal = 0
    private val playerA: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(9, 10), HeroClassType.values().random())
    private val playerB: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(8, 10), HeroClassType.values().random())

    fun runBattle(): String {
        val result = StringBuilder()
        val playerAShorthand = "${playerA.name} [${playerA.classType.classType} ${playerA.level} уровня]"
        val playerBShorthand = "${playerB.name} [${playerB.classType.classType} ${playerB.level} уровня]"

        result.appendLine(
                "Могучие герои призваны на поле битвы!" +
                        "\n\n[Я] $playerAShorthand" +
                        "\nЗДОР: ${playerA.health} / ${playerA.healthMax}   УРОН: ${playerA.damageMin} - ${playerA.damageMax}" +
                        "\nКРИТ: ${playerA.criticalChance}%   ЗАЩИТА: ${playerA.defence}%   УВОРОТ: ${playerA.dodge}%" +
                        "\n\n[ВРАГ] $playerBShorthand" +
                        "\nЗДОР: ${playerB.health} / ${playerB.healthMax}   УРОН: ${playerB.damageMin} - ${playerB.damageMax}" +
                        "\nКРИТ: ${playerB.criticalChance}%   ЗАЩИТА: ${playerB.defence}%   УВОРОТ: ${playerB.dodge}%"
        )

        while (!battleComplete) {
            if (turn % 2 != 0) {
                result.appendLine("\n[ХОД $turn]\n")
                // Нечетные ходы совершает playerA
                if (!Randomizer().getChanceRoll(playerB.dodge)) {
                    damage = Randomizer().getRandomFromRange(playerA.damageMin, playerA.damageMax)
                    damage -= round(((damage / 100) * playerB.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(playerA.criticalChance)) {
                        damage *= 2
                        result.appendLine("[Я] [КРИТИЧЕСКИЙ УДАР!] $playerAShorthand нанес $damage урона врагу!")
                    } else {
                        result.appendLine("[Я] $playerAShorthand нанес $damage урона врагу.")
                    }
                    playerB.health -= damage
                    result.appendLine("[ВРАГ] $playerBShorthand \nЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
                } else {
                    result.appendLine("[ВРАГ] $playerBShorthand увернулся от атаки! " +
                            "\nЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
                }
            } else {
                result.appendLine("\n[ХОД $turn]\n")
                // Нечетные ходы совершает playerB
                if (!Randomizer().getChanceRoll(playerA.dodge)) {
                    damage = Randomizer().getRandomFromRange(playerB.damageMin, playerB.damageMax)
                    damage -= round(((damage / 100) * playerA.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(playerB.criticalChance)) {
                        damage *= 2
                        result.appendLine("[ВРАГ] [КРИТИЧЕСКИЙ УДАР!] $playerBShorthand нанес $damage урона герою!")
                    } else {
                        result.appendLine("[ВРАГ] $playerBShorthand нанес $damage урона герою.")
                    }
                    playerA.health -= damage
                    result.appendLine("[Я] $playerAShorthand \nЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
                } else {
                    result.appendLine("[Я] $playerAShorthand увернулся от атаки! " +
                            "\nЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
                }
            }

            if (playerA.health <= 0) {
                heal = Randomizer().getRandomFromRange(playerB.level * 15, playerB.healthMax)
                if (playerB.health + heal > playerB.healthMax) {
                    playerB.health = playerB.healthMax
                } else {
                    playerB.health += heal
                }
                result.appendLine("\n[ПОРАЖЕНИЕ!]\n" +
                        "\n[Я] $playerAShorthand пал в битве!" +
                        "\n\n[ВРАГ] $playerBShorthand отдохнул и восстановил $heal здоровья." +
                        "\nЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
                battleComplete = true
            }

            if (playerB.health <= 0) {
                heal = Randomizer().getRandomFromRange(playerA.level * 15, playerA.healthMax)
                if (playerA.health + heal > playerA.healthMax) {
                    playerA.health = playerA.healthMax
                } else {
                    playerA.health += heal
                }
                result.appendLine("\n[ПОБЕДА!]\n" +
                        "\n[ВРАГ] $playerBShorthand уровня пал в битве!" +
                        "\n\n[Я] $playerAShorthand отдохнул и восстановил $heal здоровья." +
                        "\nЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
                battleComplete = true
            }

            turn++
        }

        return result.toString()
    }
}