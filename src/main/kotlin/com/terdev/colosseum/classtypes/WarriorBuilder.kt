package com.example.terdlor_first_bot.collosseum.classtypes

import com.example.terdlor_first_bot.collosseum.HeroBuilder
import com.example.terdlor_first_bot.collosseum.HeroClassType

class WarriorBuilder : HeroBuilder() {
    override val classType = HeroClassType.WARRIOR
    override val heroNameA = arrayOf("А", "Ар", "Ба", "Бон", "Ва", "Гла", "Ду", "Ка", "Кин", "Лек", "Мак", "Ми", "Ран", "Сва", "Тор", "Эл")
    override val heroNameB = arrayOf("ра", "го", "тин", "дю", "ли", "ди", "ро", "лим", "тал", "сан", "си", "ти", "лу")
    override val heroNameC = arrayOf("горн", "нот", "трор", "ран", "нор", "ум", "тар", "дан", "килн", "дил", "мус", "трил", "эль", "рог", "гунд", "ронд")
    override val luckyName = arrayOf(" Удачливый", " Везучий")
    override val primaryAttributeModifier = 6
    override val secondaryAttributeModifier = 4
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        str = level * primaryAttributeModifier
        dex = level * secondaryAttributeModifier
        int = level * tertiaryAttributeModifier
    }
}