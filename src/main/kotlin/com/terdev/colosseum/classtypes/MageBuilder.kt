package com.example.terdlor_first_bot.collosseum.classtypes

import com.example.terdlor_first_bot.collosseum.HeroBuilder
import com.example.terdlor_first_bot.collosseum.HeroClassType

class MageBuilder : HeroBuilder() {
    override val classType = HeroClassType.MAGE
    override val heroNameA = arrayOf("Ген", "Кор", "Теран", "Ун", "Ин", "Тас", "Эн", "Уво", "Эве", "О", "Уль", "Мель", "Эл", "Ту", "Эо")
    override val heroNameB = arrayOf("фен", "ким", "деви", "тали", "голи", "са", "ди", "ле", "ри", "го", "хи", "лад", "фи", "ки")
    override val heroNameC = arrayOf("дальф", "рик", "эл", "ант", "род", "дум", "монар", "лунд", "ус", "банн", "рим", "ор", "рин", "ар", "охр")
    override val luckyName = arrayOf(" Белой Башни", " Белой Башни", " Большая Шляпа", " Зачарованный", " Зачарованный")
    override val primaryAttributeModifier = 8
    override val secondaryAttributeModifier = 2
    override val tertiaryAttributeModifier = 2

    override fun setMainAttributes() {
        int = level * primaryAttributeModifier
        dex = level * secondaryAttributeModifier
        str = level * tertiaryAttributeModifier
    }
}