package com.terdev.colosseum.classtypes

import com.terdev.colosseum.HeroBuilder
import com.terdev.colosseum.HeroClassType

class AssassinBuilder : HeroBuilder() {
    override val classType = HeroClassType.ASSASSIN
    override val heroNameA = arrayOf("Бра", "Бха", "Век", "Вул", "Га", "Дзир", "Дхир", "Ир", "Кон", "На", "Син", "Ур", "Фар", "Фе", "Эр")
    override val heroNameB = arrayOf("ир", "на", "ну", "ту", "ит", "зо", "ри", "джа", "ра", "ле", "до")
    override val heroNameC = arrayOf("ру", "зар", "ка", "нат", "рад", "урт", "дре", "эн", "зокх", "рех", "хту", "ни", "рох", "бри", "рукх", "урден")
    override val luckyName = arrayOf(" Смертельный", " Клинок Богов", " Разящий")
    override val primaryAttributeModifier = 8
    override val secondaryAttributeModifier = 2
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        dex = level * primaryAttributeModifier
        str = level * secondaryAttributeModifier
        int = level * tertiaryAttributeModifier
    }
}