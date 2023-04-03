package com.terdev.colosseum.bd.system

import com.terdev.colosseum.bd.system.model.Chat
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface ChatDao : Dao<Chat, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Chat?

    fun saveIfNotExist(chatTG: org.telegram.telegrambots.meta.api.objects.Chat?)

    fun getActive(): List<Chat>
}