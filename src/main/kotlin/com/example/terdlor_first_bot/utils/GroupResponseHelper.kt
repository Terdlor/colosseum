package com.example.terdlor_first_bot.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class GroupResponseHelper(tgbParam : TelegramLongPollingBot) : ResponseHelper(tgbParam) {

    override fun sendSimpleNotification(chatId: Long, responseText: String) {
        sendSimpleNotification(chatId, responseText, 0)
    }

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
        val massage: String = if (num != 0) {
            "$num - $out"
        }
        else {
            out
        }
        val responseMessage = SendMessage(chatId.toString(), massage)
        responseMessage.enableMarkdown(true)
        responseMessage.replyMarkup = getReplyRemove()
        tgb.execute(responseMessage)
    }
}