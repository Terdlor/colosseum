package com.terdev.colosseum.jpa.dao

import com.terdev.colosseum.jpa.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {

    fun countByTelegramId(telegramId: Long) : Long

    fun countByTelegramHandle(telegramHandle: String) : Long

    fun getByTelegramId(telegramId: Long) : User

    fun getByTelegramHandle(telegramHandle: String) : User

}