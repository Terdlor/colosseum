package com.terdev.colosseum.jpa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User {

    companion object {
        const val ID: String = "id"
        const val UUID: String = "uuid"
        const val TELEGRAM_ID: String = "telegram_id"
        const val TELEGRAM_HANDLE: String = "telegram_handle"
        const val IS_BOT: String = "is_bot"
        const val FIRST_NAME: String = "first_name"
        const val LAST_NAME: String = "last_name"
        const val INSERT_DATE: String = "insert_date"
        const val BATTLE_STATE: String = "battle_state"
    }

    @Column(name = ID, insertable = false)
    var id: Long? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UUID, nullable = false)
    var uuid: String? = null

    @Column(name = TELEGRAM_ID, nullable = false)
    var telegramId: Long? = null

    @Column(name = TELEGRAM_HANDLE)
    var telegramHandle: String? = null

    @Column(name = IS_BOT, nullable = false)
    var isBot: Boolean? = null

    @Column(name = FIRST_NAME)
    var firstName: String? = null

    @Column(name = LAST_NAME)
    var lastName: String? = null

    @Column(name = INSERT_DATE, insertable = false)
    var insertDate: Date? = null

    @Column(name = BATTLE_STATE, nullable = false)
    var battleState: String? = "FREE"

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userUuid", cascade = arrayOf(CascadeType.ALL))
    var heroes: List<Hero>? = null
}