package com.terdev.colosseum.classtypes

import com.terdev.colosseum.HeroBuilder
import com.terdev.colosseum.HeroClassType

class MonkBuilder : HeroBuilder() {
    override val classType = HeroClassType.MONK
    override val heroNameA = arrayOf("Аб", "Ан", "Ас", "Бе", "Бег", "Ва", "Ви", "Гган", "Клу", "Мен", "Па", "Рек", "Се", "Шин", "Эо")
    override val heroNameB = arrayOf("не", "га", "ра", "ни", "ди", "ре", "ко")
    override val heroNameC = arrayOf("бат", "рит", "кет", "дикт", "гар", "бунд", "ту", "ди", "ак", "кант", "исий", "луз", "фим", "ран", "мит")
    override val luckyName = arrayOf(" Праведный", " Путь Судьбы", " Предначертанный")
    override val primaryAttributeModifier = 6
    override val secondaryAttributeModifier = 4
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        dex = level * primaryAttributeModifier
        int = level * secondaryAttributeModifier
        str = level * tertiaryAttributeModifier
    }
}