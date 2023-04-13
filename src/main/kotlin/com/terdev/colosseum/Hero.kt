package com.terdev.colosseum

class Hero(val classType: HeroClassType) {

    var uuid = null
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
        /*
        return  "[ $classType ] $name" +
                "\nУРОВЕНЬ: $level" +
                "\nСИЛА: $strength" +
                "\nЛОВК: $dexterity" +
                "\nИНТЛ: $intelligence" +
                "\nУДАЧ: $luck" +
                "\nЗДОР: $health из $healthMax" +
                "\nУРОН: $damageMin - $damageMax" +
                "\nЗАЩИТА: $defence" +
                "\nУВОРОТ: $dodge" +
                "\nКРИТ ШАНС: $criticalChance"

        return  "\nHero(uuid=$uuid, " +
                "\nisAlive=$isAlive, " +
                "\nname='$name', " +
                "\nheroClass='$classType', " +
                "\nlevel=$level, " +
                "\nexperience=$experience, " +
                "\nunspentPoints=$unspentPoints, " +
                "\nstrength=$strength, " +
                "\ndexterity=$dexterity, " +
                "\nintelligence=$intelligence, " +
                "\nluck=$luck, " +
                "\nhealthMax=$healthMax, " +
                "\nhealth=$health, " +
                "\ndefence=$defence, " +
                "\ndamageMin=$damageMin, " +
                "\ndamageMax=$damageMax, " +
                "\ndodge=$dodge, " +
                "\ncriticalChance=$criticalChance, " +
                "\nbattlesWon=$battlesWon)"
        */
    }
}