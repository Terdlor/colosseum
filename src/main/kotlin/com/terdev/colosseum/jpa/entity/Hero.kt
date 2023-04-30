package com.terdev.colosseum.jpa.entity

import javax.persistence.*

@Entity
@Table(name = "heroes")
class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false)
    var id: Long? = null

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var userLink: User? = null

    @Column(name = "is_alive", nullable = false)
    var isAlive: Boolean? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "class_type", nullable = false)
    var classType: String? = null

    @Column(name = "level", nullable = false)
    var level: Int? = null

    @Column(name = "experience", nullable = false)
    var experience: Int? = null

    @Column(name = "strength", nullable = false)
    var strength: Int? = null

    @Column(name = "dexterity", nullable = false)
    var dexterity: Int? = null

    @Column(name = "intelligence", nullable = false)
    var intelligence: Int? = null

    @Column(name = "luck", nullable = false)
    var luck: Int? = null

    @Column(name = "health_max", nullable = false)
    var healthMax: Int? = null

    @Column(name = "health", nullable = false)
    var health: Int? = null

    @Column(name = "defence", nullable = false)
    var defence: Int? = null

    @Column(name = "damage_min", nullable = false)
    var damageMin: Int? = null

    @Column(name = "damage_max", nullable = false)
    var damageMax: Int? = null

    @Column(name = "dodge", nullable = false)
    var dodge: Int? = null

    @Column(name = "critical_chance", nullable = false)
    var criticalChance: Int? = null

    @Column(name = "battles_won", nullable = false)
    var battlesWon: Int? = null
}