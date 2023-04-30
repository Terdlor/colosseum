package com.terdev.colosseum.common

import com.terdev.colosseum.Admin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity


abstract class CommandWork : Work() {

    @Autowired
    private lateinit var env: Environment

    abstract fun commandWork(msg: Message)

    override fun checkWork(msg: Message): Boolean {
        if (msg.entities == null) return false
        val entity: MessageEntity? =
                msg.entities.stream().filter { en ->
                    en.type == "bot_command" &&
                            (en.text.equals("/$command") || en.text.equals("/$command@" + env.getProperty("telegram.botName")))
                }.findAny().orElse(null)
        if (entity != null) {
            commandWork(msg)
            return true
        }
        return false
    }
}