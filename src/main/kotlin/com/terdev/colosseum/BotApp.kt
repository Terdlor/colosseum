package com.terdev.colosseum

import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.ForwardWork
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

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
        val commandWorkers = context.getBean("commandWorkers")
        if (commandWorkers is List<*>) {
            for (commandWork in commandWorkers) {
                if (commandWork is CommandWork && commandWork.work(update.message)) return
            }
        }

        val forwardWorkers = context.getBean("forwardWorkers")
        if (forwardWorkers is List<*>) {
            for (forwardWork in forwardWorkers) {
                if (forwardWork is ForwardWork && forwardWork.work(update.message)) return
            }
        }
    }
}