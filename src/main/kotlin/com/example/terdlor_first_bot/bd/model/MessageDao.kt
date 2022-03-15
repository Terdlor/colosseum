package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.dao.Dao

interface MessageDao : Dao<Message, Long> {

    fun findById(id: Int): List<Message>?

    fun saveIfNotExist(messageTG : org.telegram.telegrambots.meta.api.objects.Message?)
}