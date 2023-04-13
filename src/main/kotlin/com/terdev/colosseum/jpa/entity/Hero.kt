package com.terdev.colosseum.jpa.entity

import javax.persistence.*

@Entity
@Table(name = "heroes")
class Hero {

    companion object {
        const val ID: String = "id"
        const val UUID: String = "uuid"
        const val USER_UUID: String = "user_uuid"
        const val IS_ALIVE: String = "is_alive"
        const val NAME: String = "name"
        const val CLASS_TYPE: String = "class_type"
        const val LEVEL: String = "level"
        const val EXPERIENCE: String = "experience"
        const val STRENGTH: String = "strength"
        const val DEXTERITY: String = "dexterity"
        const val INTELLIGENCE: String = "intelligence"
        const val LUCK: String = "luck"
        const val HEALTH_MAX: String = "health_max"
        const val HEALTH: String = "health"
        const val DEFENCE: String = "defence"
        const val DAMAGE_MIN: String = "damage_min"
        const val DAMAGE_MAX: String = "damage_max"
        const val DODGE: String = "dodge"
        const val CRITICAL_CHANCE: String = "crit_chance"
        const val BATTLES_WON: String = "battles_won"
    }

    @Column(name = ID, insertable = false)
    var id: Long? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UUID, nullable = false)
    var uuid: String? = null

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = USER_UUID, referencedColumnName = User.UUID)
    var userUuid: User? = null

    @Column(name = IS_ALIVE, nullable = false)
    var isAlive: Boolean? = null

    @Column(name = NAME, nullable = false)
    var name: String? = null

    @Column(name = CLASS_TYPE, nullable = false)
    var classType: String? = null

    @Column(name = LEVEL, nullable = false)
    var level: Long? = null

    @Column(name = EXPERIENCE, nullable = false)
    var experience: Long? = null

    @Column(name = STRENGTH, insertable = false)
    var strength: Long? = null

    @Column(name = DEXTERITY, insertable = false)
    var dexterity: Long? = null

    @Column(name = INTELLIGENCE, insertable = false)
    var intelligence: Long? = null

    @Column(name = LUCK, insertable = false)
    var luck: Long? = null

    @Column(name = HEALTH_MAX, insertable = false)
    var healthMax: Long? = null

    @Column(name = HEALTH, insertable = false)
    var health: Long? = null

    @Column(name = DEFENCE, insertable = false)
    var defence: Long? = null

    @Column(name = DAMAGE_MIN, insertable = false)
    var damageMin: Long? = null

    @Column(name = DAMAGE_MAX, insertable = false)
    var damageMax: Long? = null

    @Column(name = DODGE, insertable = false)
    var dodge: Long? = null

    @Column(name = CRITICAL_CHANCE, insertable = false)
    var criticalChance: Long? = null

    @Column(name = BATTLES_WON, insertable = false)
    var battlesWon: Long? = null
}