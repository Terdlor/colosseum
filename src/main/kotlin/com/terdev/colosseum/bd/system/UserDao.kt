package com.terdev.colosseum.bd.system

import com.terdev.colosseum.bd.system.model.User
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface UserDao : Dao<User, Long> {

    @Throws(SQLException::class)
    fun findByName(name: String?): User?

    @Throws(SQLException::class)
    fun findById(id: Long): User?

    fun saveIfNotExist(userTG: org.telegram.telegrambots.meta.api.objects.User?)
}