package com.example.terdlor_first_bot

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import com.example.terdlor_first_bot.utils.Печататель
import com.example.terdlor_first_bot.worker.*
import com.example.terdlor_first_bot.common.CommandWork
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

        foo = botName

        if (update.message == null) {
            //TODO
            return
        }

        try {
            val msg = DatabaseHelper.getMessageDao().save(update.message)

            if (context.getBean("welcomMessageWork", WelcomMessageWork::class.java).work(update.message, msg)) {
                return
            }
            if (context.getBean("spamMessageWork", SpamMessageWork::class.java).work(msg)) {
                return
            }

            val commandWorkers = context.getBean("commandWorkers")
            if (commandWorkers is List<*>) {
                for (commandWork in commandWorkers) {
                    if (commandWork is CommandWork && commandWork.work(update.message, msg)) return
                }
            }

            if (update.message.chat.id > 0 &&
                    context.getBean("singlMessageWorkBean", SinglMessageWork::class.java).work(msg)) {
                return
            }
            if (update.message.chat.id < 0 &&
                    context.getBean("groupMessageWorkBean", GroupMessageWork::class.java).work(msg)) {
                return
            }
        } catch (ex : Exception) {
            val str = Печататель().дайException(ex)
            com.example.terdlor_first_bot.utils.println(str)
            LogHelper().saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(update.message.from.id)?.userName!!)
            SinglResponseHelper(this).sendSimpleNotification(update.message.chat.id, str, update.message.messageId)
            return
        }

        val dateCurrentLocalEnd = Date()
    }
}