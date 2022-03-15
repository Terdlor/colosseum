package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface MessageDao : Dao<Message, Long> {

    @Throws(SQLException::class)
    fun findById(id: Int): Message?

    fun saveIfNotExist(messageTG : org.telegram.telegrambots.meta.api.objects.Message?)
}