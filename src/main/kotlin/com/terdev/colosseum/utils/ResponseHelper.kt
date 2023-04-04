package com.terdev.colosseum.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.io.File
import java.util.Arrays.asList

abstract class ResponseHelper(val tgbParam: TelegramLongPollingBot) {

    abstract fun sendSimpleNotification(chatId: Long, responseText: String)

    abstract fun sendSimpleNotification(chatId: Long, responseText: String, num: Int)

    abstract fun sendReplyNotification(chatId: Long, responseText: String, replyId: Int)

    abstract fun sendSimpleFile(chatId: Long, file: File)

    fun getReplyRemove(): ReplyKeyboardRemove {
        val markup = ReplyKeyboardRemove()
        markup.removeKeyboard = true
        return markup
    }

    fun getReplyMarkup(allButtons: List<String>): ReplyKeyboardMarkup {
        val markup = ReplyKeyboardMarkup()
        val row = KeyboardRow()
        allButtons.map { rowButtons ->
            row.add(rowButtons)
        }
        markup.keyboard = asList(row)
        markup.resizeKeyboard = true
        return markup
    }

    fun getReplyButton(button: String) : KeyboardButton {
        return KeyboardButton(button)
    }
}