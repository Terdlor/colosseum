package com.example.terdlor_first_bot.collosseum.classtypes

import com.example.terdlor_first_bot.collosseum.HeroBuilder
import com.example.terdlor_first_bot.collosseum.HeroClassType
import java.util.*

class RogueBuilder : HeroBuilder() {
    override val classType = HeroClassType.ROGUE
    override val heroNameSyllables = 2
    override val heroNameA = getHeroNameA()
    override val heroNameB = arrayOf("")
    override val heroNameC = getHeroNameC()
    override val luckyName = arrayOf(" Ловкая Рука", " Хитрец")
    override val primaryAttributeModifier = 6
    override val secondaryAttributeModifier = 4
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        dex = level * primaryAttributeModifier
        str = level * secondaryAttributeModifier
        int = level * tertiaryAttributeModifier
    }

    private companion object {
        val isVariant = Random().nextBoolean()
        fun getHeroNameA() : Array<String> {
            if (isVariant) {
                return arrayOf("Й", "И", "О", "У", "Гии", "Лу", "Э", "Ле", "Ми", "Де", "Са", "Би")
            } else {
                return arrayOf("Ф", "Б", "В", "М", "З", "Г", "Др", "Мл", "Д")
            }
        }
        fun getHeroNameC() : Array<String> {
            if (isVariant) {
                return arrayOf("сс", "вв", "нкф", "ртм", "мд", "ндж", "льв", "ртк", "ндрв", "ндрф", "рр")
            } else {
                return arrayOf("ортд", "инл", "ом", "амд", "ов")
            }
        }
    }
}