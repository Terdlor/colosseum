package com.terdev.colosseum

import com.terdev.colosseum.bd.DatabaseHelper
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.DocumentWork
import com.terdev.colosseum.utils.LogHelper
import com.terdev.colosseum.utils.SinglResponseHelper
import com.terdev.colosseum.utils.Печататель
import com.terdev.colosseum.worker.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*

@Service
class BotApp : TelegramLongPollingBot() {

    @Autowired
    private lateinit var context: ApplicationContext

    @Value("\${telegram.botName}")
    private val botName: String = ""

    @Value("\${telegram.token}")
    private val token: String = ""

    companion object {
        var foo: String = "botName"
    }

    override fun getBotUsername(): String = botName

    override fun getBotToken(): String = token

    override fun onUpdateReceived(update: Update) {
        val dateCurrentLocalStart = Date()

        com.terdev.colosseum.BotApp.Companion.foo = botName

        if (update.message == null) {
            //TODO
            return
        }

        try {
            val msg = DatabaseHelper.getMessageDao().save(update.message)

            if (context.getBean("helpWork", HelpWork::class.java).work(update.message, msg)) {
                return
            }

            val commandWorkers = context.getBean("commandWorkers")
            if (commandWorkers is List<*>) {
                for (commandWork in commandWorkers) {
                    if (commandWork is CommandWork && commandWork.work(update.message, msg)) return
                }
            }
            val documentWorkers = context.getBean("documentWorkers")
            if (documentWorkers is List<*>) {
                for (documentWork in documentWorkers) {
                    if (documentWork is DocumentWork && documentWork.work(update.message, msg)) return
                }
            }
        } catch (ex: Exception) {
            val str = Печататель().дайException(ex)
            com.terdev.colosseum.utils.println(str)
            LogHelper().saveLog(
                str,
                "ОШИБКА-" + DatabaseHelper.getUserDao().findById(update.message.from.id)?.userName!!
            )
            SinglResponseHelper(this).sendSimpleNotification(update.message.chat.id, str, update.message.messageId)
            return
        }

        val dateCurrentLocalEnd = Date()
    }
}