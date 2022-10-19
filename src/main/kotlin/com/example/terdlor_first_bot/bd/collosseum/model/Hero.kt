package com.example.terdlor_first_bot.bd.collosseum.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "COL_HERO")
class Hero {

    @DatabaseField(generatedId = true)
    var id: Int = 1
    @DatabaseField
    var idPlayer: Long = -1
    @DatabaseField
    var name: String? = null
    @DatabaseField
    var title: String? = null
    @DatabaseField
    var level: Long = 1
    @DatabaseField
    var clas: String? = null
    @DatabaseField
    var experience: Long = 0
    @DatabaseField
    var unspent_points: Long = 0
    @DatabaseField
    var strength: Long = 0
    @DatabaseField
    var dexterity: Long = 0
    @DatabaseField
    var intelligence: Long = 0
    @DatabaseField
    var luck: Long = 0
    @DatabaseField
    var max_base_health: Long = 0
    @DatabaseField
    var base_health: Long = 0
    @DatabaseField
    var base_defence: Long = 0
    @DatabaseField
    var min_base_damage: Long = 0
    @DatabaseField
    var max_base_damage: Long = 0
    @DatabaseField
    var base_dodge_chance: Long = 0
    @DatabaseField
    var base_critical_chance: Long = 0
    @DatabaseField
    var date_available_for_duel: Date? = null
    @DatabaseField
    var date_of_creation: Date? = null
    @DatabaseField
    var date_of_death: Date? = null
    @DatabaseField
    var killed_by: Int? = null
    @DatabaseField
    var monsters_killed: Int = 0
    @DatabaseField
    var heroes_killed: Int = 0
}