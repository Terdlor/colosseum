package com.example.terdlor_first_bot

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.worker.GroupMessageWork
import com.example.terdlor_first_bot.worker.SinglMessageWork
import com.example.terdlor_first_bot.worker.SystemMessageWork
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*

@Service
class BotApp : TelegramLongPollingBot() {

    @Value("\${telegram.botName}")
    private val botName: String = ""

    @Value("\${telegram.token}")
    private val token: String = ""

    override fun getBotUsername(): String = botName

    override fun getBotToken(): String = token

    override fun onUpdateReceived(update: Update) {
        val dateCurrentLocalStart = Date()

        val msg = DatabaseHelper.getMessageDao().save(update.message)

        if (update.message == null) {
            //TODO
            return
        }
        if (SystemMessageWork(this).work(update.message, msg)) {
            return
        }
        if (update.message.chat.id > 0) {
            SinglMessageWork(this).work(msg)
            return
        }
        if (update.message.chat.id < 0) {
            GroupMessageWork(this).work(msg)
            return
        }

        val dateCurrentLocalEnd = Date()

//        if (update.hasMessage()) {
//            val message = update.message
//            val chatId = message.chatId
//            val responseText = if (message.hasText()) {
//                val messageText = message.text
//                when {
//                    messageText == "/start" -> "Добро пожаловать!"
//                    messageText.startsWith("Кнопка ") -> "Вы нажали кнопку" // обработка нажатия кнопки
//                    else -> "Вы написали: *$messageText*"
//                }
//            } else {
//                "Я понимаю только текст"
//            }
//            sendNotification2(chatId, responseText)
//        }
    }
}