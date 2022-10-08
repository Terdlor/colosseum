package com.example.terdlor_first_bot.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class SinglResponseHelper(tgbParam : TelegramLongPollingBot) : ResponseHelper(tgbParam) {

    override fun sendSimpleNotification(chatId: Long, responseText: String, num:  Int) {
        val out : String = if (responseText.count() > 4098) {
            "сообщение больше 4098 символов, слать не умею"
        } else {
            responseText
                    .replace("_", "\\_")
                    .replace("*", "\\*")
                    .replace("[", "\\[")
                    .replace("`", "\\`")
        }
        val responseMessage = SendMessage(chatId.toString(), "$num  -  $out")
        responseMessage.enableMarkdown(true)
        // добавляем 4 кнопки
        responseMessage.replyMarkup = getReplyMarkup(
                listOf(
                        listOf("СПААААМ")
                        //  listOf("Кнопка 1", "Кнопка 2"),
                        //  listOf("Кнопка 3", "Кнопка 4")
                )
        )
        tgb.execute(responseMessage)
    }
}