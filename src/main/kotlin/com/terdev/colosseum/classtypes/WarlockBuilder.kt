package com.example.terdlor_first_bot.collosseum.classtypes

import com.example.terdlor_first_bot.collosseum.HeroBuilder
import com.example.terdlor_first_bot.collosseum.HeroClassType

class WarlockBuilder : HeroBuilder() {
    override val classType = HeroClassType.WARLOCK
    override val heroNameA = arrayOf("Ал", "Гул\'", "Д\'ур", "Ил", "Йукс", "Ка", "Мо", "Мха", "Нул", "Нур", "Ньял\'", "Траг", "Шаб\'", "Экх", "Ям")
    override val heroNameB = arrayOf("ла", "гу", "толь", "аат", "ра", "аг\'", "га", "го", "по", "са", "хо", "тот", "ходх")
    override val heroNameC = arrayOf("хор", "дан", "рук", "польбх", "ресс", "мор", "рок", "нульб", "ток", "рог", "теп", "тох", "тек", "лу", "льек")
    override val luckyName = arrayOf(" Глаз Космоса", " Древнее Знание", " Голос Безумия")
    override val primaryAttributeModifier = 6
    override val secondaryAttributeModifier = 4
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        int = level * primaryAttributeModifier
        str = level * secondaryAttributeModifier
        dex = level * tertiaryAttributeModifier
    }
}