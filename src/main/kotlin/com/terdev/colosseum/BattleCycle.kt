package com.terdev.colosseum

import kotlin.math.round

class BattleCycle {
    private var battleComplete = false
    private var turn = 1
    private var damage = 0
    private var heal = 0
    private val caller: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(9, 10), HeroClassType.values().random())
    private val responder: Hero = HeroBuilder.getHero(Randomizer().getRandomFromRange(8, 10), HeroClassType.values().random())

    fun runBattle(caller: Hero, responder: Hero) : String {
        val result = StringBuilder()
        val callerShorthand = "${caller.name} [${caller.classType.classType} ${caller.level} уровня]"
        val responderShorthand = "${responder.name} [${responder.classType.classType} ${responder.level} уровня]"

        result.appendLine(
                "Могучие герои призваны на поле битвы!" +
                        "\n\n[Я] $callerShorthand" +
                        "\nЗДОР: ${caller.health} / ${caller.healthMax}   УРОН: ${caller.damageMin} - ${caller.damageMax}" +
                        "\nКРИТ: ${caller.criticalChance}%   ЗАЩИТА: ${caller.defence}%   УВОРОТ: ${caller.dodge}%" +
                        "\n\n[ВРАГ] $responderShorthand" +
                        "\nЗДОР: ${responder.health} / ${responder.healthMax}   УРОН: ${responder.damageMin} - ${responder.damageMax}" +
                        "\nКРИТ: ${responder.criticalChance}%   ЗАЩИТА: ${responder.defence}%   УВОРОТ: ${responder.dodge}%"
        )

        while (!battleComplete) {
            if (turn % 2 != 0) {
                result.appendLine("\n[ХОД $turn]\n")
                // Нечетные ходы совершает caller
                if (!Randomizer().getChanceRoll(responder.dodge)) {
                    damage = Randomizer().getRandomFromRange(caller.damageMin, caller.damageMax)
                    damage -= round(((damage / 100) * responder.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(caller.criticalChance)) {
                        damage *= 2
                        result.appendLine("[Я] [КРИТИЧЕСКИЙ УДАР!] $callerShorthand нанес $damage урона врагу!")
                    } else {
                        result.appendLine("[Я] $callerShorthand нанес $damage урона врагу.")
                    }
                    responder.health -= damage
                    result.appendLine("[ВРАГ] $responderShorthand \nЗДОРОВЬЕ: ${responder.health} / ${responder.healthMax}.")
                } else {
                    result.appendLine("[ВРАГ] $responderShorthand увернулся от атаки! " +
                            "\nЗДОРОВЬЕ: ${responder.health} / ${responder.healthMax}.")
                }
            } else {
                result.appendLine("\n[ХОД $turn]\n")
                // Нечетные ходы совершает responder
                if (!Randomizer().getChanceRoll(caller.dodge)) {
                    damage = Randomizer().getRandomFromRange(responder.damageMin, responder.damageMax)
                    damage -= round(((damage / 100) * caller.defence).toDouble()).toInt()
                    if (Randomizer().getChanceRoll(responder.criticalChance)) {
                        damage *= 2
                        result.appendLine("[ВРАГ] [КРИТИЧЕСКИЙ УДАР!] $responderShorthand нанес $damage урона герою!")
                    } else {
                        result.appendLine("[ВРАГ] $responderShorthand нанес $damage урона герою.")
                    }
                    caller.health -= damage
                    result.appendLine("[Я] $callerShorthand \nЗДОРОВЬЕ: ${caller.health} / ${caller.healthMax}.")
                } else {
                    result.appendLine("[Я] $callerShorthand увернулся от атаки! " +
                            "\nЗДОРОВЬЕ: ${caller.health} / ${caller.healthMax}.")
                }
            }

            if (caller.health <= 0) {
                heal = Randomizer().getRandomFromRange(responder.level * 15, responder.healthMax)
                if (responder.health + heal > responder.healthMax) {
                    responder.health = responder.healthMax
                } else {
                    responder.health += heal
                }
                result.appendLine("\n[ПОРАЖЕНИЕ!]\n" +
                        "\n[Я] $callerShorthand пал в битве!" +
                        "\n\n[ВРАГ] $responderShorthand отдохнул и восстановил $heal здоровья." +
                        "\nЗДОРОВЬЕ: ${responder.health} / ${responder.healthMax}.")
                battleComplete = true
            }

            if (responder.health <= 0) {
                heal = Randomizer().getRandomFromRange(caller.level * 15, caller.healthMax)
                if (caller.health + heal > caller.healthMax) {
                    caller.health = caller.healthMax
                } else {
                    caller.health += heal
                }
                result.appendLine("\n[ПОБЕДА!]\n" +
                        "\n[ВРАГ] $responderShorthand пал в битве!" +
                        "\n\n[Я] $callerShorthand отдохнул и восстановил $heal здоровья." +
                        "\nЗДОРОВЬЕ: ${caller.health} / ${caller.healthMax}.")
                battleComplete = true
            }

            turn++
        }

        return result.toString()
    }
}