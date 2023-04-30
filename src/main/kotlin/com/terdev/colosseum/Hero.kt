package com.terdev.colosseum

import com.terdev.colosseum.HeroClassType.*
import com.terdev.colosseum.jpa.entity.Hero

class Hero(val classType: HeroClassType) {

    var isAlive = true
    var name = ""
    var level = 1
    var experience = 0
    var strength = 1
    var dexterity = 1
    var intelligence = 1
    var luck = 1
    var healthMax = 10
    var health = 10
    var defence = 5
    var damageMin = 6
    var damageMax = 7
    var dodge = 3
    var criticalChance = 3
    var battlesWon = 0

    override fun toString(): String {
        return "[ $classType ] $name" +
                "\nУРОВЕНЬ: $level" +
                "\nЗДОР: $health из $healthMax" +
                "\nУРОН: $damageMin - $damageMax" +
                "\nЗАЩИТА: $defence" +
                "\nУВОРОТ: $dodge" +
                "\nКРИТ ШАНС: $criticalChance\n"
    }

    fun mapHeroFromDatabase(heroEntity: Hero): com.terdev.colosseum.Hero {
        val hero = com.terdev.colosseum.Hero(valueOf(heroEntity.classType!!))
        hero.isAlive = heroEntity.isAlive!!
        hero.name = heroEntity.name!!
        hero.level = heroEntity.level!!
        hero.experience = heroEntity.experience!!
        hero.strength = heroEntity.strength!!
        hero.dexterity = heroEntity.dexterity!!
        hero.intelligence = heroEntity.intelligence!!
        hero.luck = heroEntity.luck!!
        hero.healthMax = heroEntity.healthMax!!
        hero.health = heroEntity.health!!
        hero.defence = heroEntity.defence!!
        hero.damageMin = heroEntity.damageMin!!
        hero.damageMax = heroEntity.damageMax!!
        hero.dodge = heroEntity.dodge!!
        hero.criticalChance = heroEntity.criticalChance!!
        hero.battlesWon = heroEntity.battlesWon!!

        return hero
    }
}