package com.terdev.colosseum.classtypes

import com.terdev.colosseum.HeroBuilder
import com.terdev.colosseum.HeroClassType

class DruidBuilder : HeroBuilder() {
    override val classType = HeroClassType.DRUID
    override val heroNameA = arrayOf("Ра", "Бри", "Бе", "Кай", "Као", "Мал", "Нди", "Фо", "И", "Дри", "Пто", "Тра", "Эо", "До", "Феар")
    override val heroNameB = arrayOf("да", "анн", "ор", "фхи", "лиф", "ла", "алл", "ло", "ур", "а", "ле", "во", "гана", "ла", "фур")
    override val heroNameC = arrayOf("гхас", "гаст", "таср", "рон", "лин", "фион", "идр", "гасс", "ган", "нан", "данх", "мей", "кур", "ид", "идх", "ион")
    override val luckyName = arrayOf(" Древо Жизни", " Северного Леса")
    override val primaryAttributeModifier = 6
    override val secondaryAttributeModifier = 4
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        int = level * primaryAttributeModifier
        dex = level * secondaryAttributeModifier
        str = level * tertiaryAttributeModifier
    }
}