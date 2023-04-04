package com.example.terdlor_first_bot.collosseum

import com.example.terdlor_first_bot.collosseum.classtypes.*
import java.util.*

abstract class HeroBuilder {

    abstract val classType: HeroClassType
    open val heroNameSyllables = Random().nextInt(2) + 2
    abstract val heroNameA: Array<String>
    abstract val heroNameB: Array<String>
    abstract val heroNameC: Array<String>
    abstract val luckyName: Array<String>
    abstract val primaryAttributeModifier: Int
    abstract val secondaryAttributeModifier: Int
    abstract val tertiaryAttributeModifier: Int
    var level = 0
    var str = 0
    var dex = 0
    var int = 0
    private val luck = getHeroLuck()
    private val criticalChance = luck + Randomizer().getDiceRoll(10)

    protected abstract fun setMainAttributes()

    //TODO: вынести цифры в параметр БД
    private fun getHeroLuck() : Int {
        return when (Randomizer().getPercentage()) {
            in 1..5 -> 4
            in 6..20 -> 3
            in 21..50 -> 2
            else -> 1
        }
    }

    private fun getAttributeModifier() {
        if (level % 2 == 0) {
            for (i in 1..level / 2) {
                when (Randomizer().getDiceRoll(3)) {
                    1 -> str += luck * 2
                    2 -> dex += luck * 2
                    else -> int += luck * 2
                }
            }
        }
    }

    fun getHero(level: Int) : Hero {
        this.level = level
        setMainAttributes()
        getAttributeModifier()
        val healthMax = (str * 2 + dex + int) * 4
        val damageMin = (str + dex + int) * 2
        val damageMax = damageMin + level * 4 + luck * 2
        var dodge = dex + luck * 2
        var defence = (int + dex) / 2 + luck * 2
        if (dodge > 80) {
            dodge = 80
        }
        if (defence > 80) {
            defence = 80
        }

        val hero = Hero(classType)
        if (luck == 4) {
            hero.name = getHeroName() + luckyName.random()
        }
        else {
            hero.name = getHeroName()
        }
        hero.level = level
        hero.strength = str
        hero.dexterity = dex
        hero.intelligence = int
        hero.luck = luck
        hero.healthMax = healthMax
        hero.health = healthMax
        hero.defence = defence
        hero.damageMin = damageMin
        hero.damageMax = damageMax
        hero.dodge = dodge
        hero.criticalChance = criticalChance

        return hero
    }

    protected open fun getHeroName() : String {
        return when (heroNameSyllables) {
            1 -> heroNameA.random()
            2 -> heroNameA.random() + heroNameC.random()
            3 -> heroNameA.random() + heroNameB.random() + heroNameC.random()
            else -> "Ошибкин"
        }
    }

    companion object {
        fun getHeroName(classType: HeroClassType): String {
            return when (classType) {
                HeroClassType.BARBARIAN -> BarbarianBuilder().getHeroName()
                HeroClassType.PALADIN -> PaladinBuilder().getHeroName()
                HeroClassType.WARRIOR -> WarriorBuilder().getHeroName()
                HeroClassType.ASSASSIN -> AssassinBuilder().getHeroName()
                HeroClassType.MONK -> MonkBuilder().getHeroName()
                HeroClassType.ROGUE -> RogueBuilder().getHeroName()
                HeroClassType.MAGE -> MageBuilder().getHeroName()
                HeroClassType.WARLOCK -> WarlockBuilder().getHeroName()
                HeroClassType.DRUID -> DruidBuilder().getHeroName()
            }
        }

        fun getHero(level: Int, classType: HeroClassType) : Hero {
            return when (classType) {
                HeroClassType.BARBARIAN -> BarbarianBuilder().getHero(level)
                HeroClassType.PALADIN -> PaladinBuilder().getHero(level)
                HeroClassType.WARRIOR -> WarriorBuilder().getHero(level)
                HeroClassType.ASSASSIN -> AssassinBuilder().getHero(level)
                HeroClassType.MONK -> MonkBuilder().getHero(level)
                HeroClassType.ROGUE -> RogueBuilder().getHero(level)
                HeroClassType.MAGE -> MageBuilder().getHero(level)
                HeroClassType.WARLOCK -> WarlockBuilder().getHero(level)
                HeroClassType.DRUID -> DruidBuilder().getHero(level)
            }
        }
    }
}