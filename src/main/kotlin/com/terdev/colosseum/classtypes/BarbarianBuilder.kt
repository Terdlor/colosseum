package com.example.terdlor_first_bot.collosseum.classtypes

import com.example.terdlor_first_bot.collosseum.HeroBuilder
import com.example.terdlor_first_bot.collosseum.HeroClassType
import com.example.terdlor_first_bot.collosseum.Randomizer

class BarbarianBuilder : HeroBuilder() {
    override val classType = HeroClassType.BARBARIAN
    override val heroNameA = arrayOf("Бар", "Дум", "Гурн", "Игг", "Йотм", "Мог", "Нан", "Норт", "Скар", "Скйалл", "Трогг", "У", "Унг", "Хрод", "Эй")
    override val heroNameB = arrayOf("")
    override val heroNameC = arrayOf("бар", "морн", "трад", "дрим", "ганд", "рон", "дер", "гундр", "норт", "рук", "дабар", "гир", "рикр")
    override val heroNameSyllables = getNameSyllableCount()
    override val luckyName = arrayOf(" Кулак Судьбы", " Гнев Богов")
    override val primaryAttributeModifier = 8
    override val secondaryAttributeModifier = 2
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        str = level * primaryAttributeModifier
        dex = level * secondaryAttributeModifier
        int = level * tertiaryAttributeModifier
    }

    private fun getNameSyllableCount(): Int {
        if (Randomizer().getChanceRoll(15)) {
            return 1
        } else {
            return 2
        }
    }
}