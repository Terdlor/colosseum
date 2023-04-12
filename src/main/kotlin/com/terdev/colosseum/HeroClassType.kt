package com.example.terdlor_first_bot.collosseum

enum class HeroClassType(var classType: String, var isPureClass: Boolean = false) {

    BARBARIAN("Варвар", true),
    PALADIN("Паладин"),
    WARRIOR("Воин"),
    ASSASSIN("Асассин", true),
    MONK("Монах"),
    ROGUE("Разбойник"),
    MAGE("Маг", true),
    WARLOCK("Варлок"),
    DRUID("Друид")
}