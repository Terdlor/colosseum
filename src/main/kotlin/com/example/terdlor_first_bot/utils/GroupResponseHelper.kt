package com.example.terdlor_first_bot.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class GroupResponseHelper(tgbParam : TelegramLongPollingBot) : ResponseHelper(tgbParam) {

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
        responseMessage.replyMarkup = getReplyRemove()
        tgb.execute(responseMessage)
    }
}