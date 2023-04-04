package com.example.terdlor_first_bot.collosseum

import kotlin.math.round

class BattleCycle {
    private var battleComplete = false
    private var turn = 1
    private var damage = 0
    private var heal = 0
    private val playerA: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(9, 10), HeroClassType.values().random())
    private val playerB: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(8, 10), HeroClassType.values().random())

    fun runBattle() : String {
        val result = StringBuilder()
        result.appendLine("Могучие герои призваны на поле битвы!" +
                "\n\nВЫ:" +
                "\n$playerA" +
                "\nВРАГ:" +
                "\n$playerB")

        while (!battleComplete) {
            if (turn % 2 != 0) {
                // Нечетные ходы совершает playerA
                if (!Randomizer().getChanceRoll(playerB.dodge)) {
                    damage = Randomizer().getRandomFromRange(playerA.damageMin, playerA.damageMax)
                    damage -= round(((damage / 100) * playerB.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(playerA.criticalChance)) {
                        damage *= 2
                        result.appendLine("[ КРИТИЧЕСКИЙ УДАР! ]")
                    }
                    playerB.health -= damage
                    result.appendLine("[ВЫ] ${playerA.name} нанес $damage урона врагу.")
                }
                else {
                    result.appendLine("[ВРАГ] ${playerB.name} увернулся от атаки!")
                }
                result.appendLine("[ВРАГ] ${playerB.name} ЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
            }
            else {
                // Нечетные ходы совершает playerB
                if (!Randomizer().getChanceRoll(playerA.dodge)) {
                    damage = Randomizer().getRandomFromRange(playerB.damageMin, playerB.damageMax)
                    damage -= round(((damage / 100) * playerA.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(playerB.criticalChance)) {
                        damage *= 2
                        result.appendLine("[ КРИТИЧЕСКИЙ УДАР! ]")
                    }
                    playerA.health -= damage
                    result.appendLine("[ВРАГ] ${playerB.name} нанес $damage урона врагу.")
                }
                else {
                    result.appendLine("[ВЫ] ${playerA.name} увернулся от атаки!")
                }
                result.appendLine("[ВЫ] ${playerA.name} ЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
            }

            if (playerA.health <= 0) {
                heal = Randomizer().getRandomFromRange(playerB.level * 15, playerB.healthMax)
                if (playerB.health + heal > playerB.healthMax) {
                    playerB.health = playerB.healthMax
                }
                else {
                    playerB.health += heal
                }
                result.appendLine("[ВЫ потерпел поражение в битве с ИГРОКОМ 2]" +
                        "\n[ВЫ] ${playerA.name} [${playerA.classType}] ${playerA.level} уровня пал в битве!" +
                        "\n[ВРАГ] ${playerB.name} отдыхает и восстанавливает $heal здоровья." +
                        "\n[ВРАГ] ${playerB.name} ЗДОРОВЬЕ: ${playerB.health} / ${playerB.healthMax}.")
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
                result.appendLine("[ВРАГ потерпел поражение в битве с ИГРОКОМ 1]" +
                        "\n[ВРАГ] ${playerB.name} [${playerB.classType}] ${playerB.level} уровня пал в битве!" +
                        "\n[ВЫ] ${playerA.name} отдыхает и восстанавливает $heal здоровья." +
                        "\n[ВЫ] ${playerA.name} ЗДОРОВЬЕ: ${playerA.health} / ${playerA.healthMax}.")
                battleComplete = true
            }

            turn ++
        }

        return result.toString()
    }
}