package com.terdev.colosseum.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.telegram.telegrambots.meta.api.objects.Document
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity


abstract class DocumentWork : Work() {

    @Autowired
    private lateinit var env: Environment

    abstract fun commandWork(doc: Document)

    override fun checkWork(msg: Message): Boolean {
        if (msg.document == null) return false
        if (msg.captionEntities == null) return false
        val entity: MessageEntity? =
                msg.captionEntities.stream().filter { en ->
                    en.type == "bot_command" &&
                            (en.text.equals("/$command") || en.text.equals("/$command@" + env.getProperty("telegram.botName")))
                }.findAny().orElse(null)
        if (entity != null) {
            commandWork(msg.document)
            return true
        }
        return false
    }
}