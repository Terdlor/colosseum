package com.example.terdlor_first_bot.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.io.File

abstract class ResponseHelper(val tgbParam : TelegramLongPollingBot) {

    abstract fun sendSimpleNotification(chatId: Long, responseText: String)

    abstract fun sendSimpleNotification(chatId: Long, responseText: String, num:  Int)

    abstract fun sendReplyNotification(chatId: Long, responseText: String, replyId : Int)

    abstract fun sendSimpleFile(chatId: Long, file: File)

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