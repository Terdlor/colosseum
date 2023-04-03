package com.terdev.colosseum.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import java.io.File

class GroupResponseHelper(tgbParam: TelegramLongPollingBot) : ResponseHelper(tgbParam) {

    override fun sendSimpleNotification(chatId: Long, responseText: String) {
        sendSimpleNotification(chatId, responseText, 0)
    }

    override fun sendSimpleNotification(chatId: Long, responseText: String, num: Int) {
        val out: String = if (responseText.count() > 4098) {
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
        } else {
            out
        }
        val responseMessage = SendMessage(chatId.toString(), massage)
        responseMessage.enableMarkdown(true)
        responseMessage.replyMarkup = getReplyRemove()
        tgbParam.execute(responseMessage)
    }

    override fun sendReplyNotification(chatId: Long, responseText: String, replyId: Int) {

        val out: String = if (responseText.count() > 4098) {
            "сообщение больше 4098 символов, слать не умею"
        } else {
            responseText
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`")
        }
        val massage: String = out

        val responseMessage = SendMessage(chatId.toString(), massage)
        responseMessage.enableMarkdown(true)
        responseMessage.replyMarkup = getReplyRemove()
        responseMessage.replyToMessageId = replyId

        tgbParam.execute(responseMessage)
    }


    override fun sendSimpleFile(chatId: Long, file: File) {
        val input = InputFile(file)
        val doc = SendDocument(chatId.toString(), input)
        doc.replyMarkup = getReplyRemove()
        tgbParam.execute(doc)
    }
}