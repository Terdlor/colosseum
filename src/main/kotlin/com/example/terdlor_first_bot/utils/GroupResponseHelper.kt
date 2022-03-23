package com.example.terdlor_first_bot.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class GroupResponseHelper(tgb_p : TelegramLongPollingBot) : ResponseHelper(tgb_p) {

    override fun sendSimpleNotification(chatId: Long, responseText: String, num:  Int) {
        val out : String
        if (responseText.count() > 4098) {
            out = "сообщение больше 4098 символов, слать не умею"
        } else {
            out = responseText
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