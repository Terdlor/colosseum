package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface UserDao : Dao<User, Long> {
    @Throws(SQLException::class)
    fun findByName(name: String?): List<User>?

    fun saveIfNotExist(userTG : org.telegram.telegrambots.meta.api.objects.User)
}