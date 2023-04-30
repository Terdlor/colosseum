package com.terdev.colosseum.jpa.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false)
    var id: Long? = null

    @Column(name = "telegram_id", nullable = false, unique = true)
    var telegramId: Long? = null

    @Column(name = "telegram_handle")
    var telegramHandle: String? = null

    @Column(name = "is_bot", nullable = false)
    var isBot: Boolean? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "date_created", insertable = false)
    var dateCreated: Date? = null

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userLink", cascade = [CascadeType.ALL])
    var heroes: List<Hero>? = null
}