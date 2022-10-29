package com.example.terdlor_first_bot.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

abstract class ResponseHelper(tgbParam : TelegramLongPollingBot) {

    var tgb : TelegramLongPollingBot

    init {
        tgb = tgbParam
    }

    abstract fun sendSimpleNotification(chatId: Long, responseText: String)

    abstract fun sendSimpleNotification(chatId: Long, responseText: String, num:  Int)

    fun getReplyRemove(): ReplyKeyboardRemove {
        val markup = ReplyKeyboardRemove()
        markup.removeKeyboard = true
        return markup
    }

    fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboardMarkup {
        val markup = ReplyKeyboardMarkup()
        markup.keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        return markup
    }
}