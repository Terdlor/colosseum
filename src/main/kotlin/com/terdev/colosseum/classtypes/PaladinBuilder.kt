package com.example.terdlor_first_bot.collosseum.classtypes

import com.example.terdlor_first_bot.collosseum.HeroBuilder
import com.example.terdlor_first_bot.collosseum.HeroClassType

class PaladinBuilder : HeroBuilder() {
    override val classType = HeroClassType.PALADIN
    override val heroNameA = arrayOf("Ар", "Берт", "Гель", "Жал", "Ка", "Лан", "Мел", "Мен", "На", "Оз", "Ред", "Ут", "Фран", "Хелль", "Чан")
    override val heroNameB = arrayOf("ге", "ро", "ле", "де", "тана", "ти")
    override val heroNameC = arrayOf("тас", "ран", "мут", "вон", "лус", "дерс", "виль", "тил", "эль", "винд", "вальд", "тер", "сег", "мет", "лер")
    override val luckyName = arrayOf(" Судьбоносный", " Парагон")
    override val primaryAttributeModifier = 6
    override val secondaryAttributeModifier = 4
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        str = level * primaryAttributeModifier
        int = level * secondaryAttributeModifier
        dex = level * tertiaryAttributeModifier
    }
}