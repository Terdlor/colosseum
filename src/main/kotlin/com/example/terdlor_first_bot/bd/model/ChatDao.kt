package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface ChatDao : Dao<Chat, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Chat?

    fun saveIfNotExist(chatTG : org.telegram.telegrambots.meta.api.objects.Chat?)
}