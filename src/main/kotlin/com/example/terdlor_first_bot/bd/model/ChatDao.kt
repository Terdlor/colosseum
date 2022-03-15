package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.dao.Dao

interface ChatDao : Dao<Chat, Long> {

    fun findById(id: Long): List<Chat>?

    fun saveIfNotExist(chatTG : org.telegram.telegrambots.meta.api.objects.Chat?)
}