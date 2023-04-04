package com.example.terdlor_first_bot.collosseum

enum class HeroClassType(var classType: String, var isPureClass: Boolean = false) {

    BARBARIAN("ВАРВАР",true),
    PALADIN("ПАЛАДИН"),
    WARRIOR("ВОИН"),
    ASSASSIN("АСАССИН",true),
    MONK("МОНАХ"),
    ROGUE("РАЗБОЙНИК"),
    MAGE("МАГ",true),
    WARLOCK("ВАРЛОК"),
    DRUID("ДРУИД")
}