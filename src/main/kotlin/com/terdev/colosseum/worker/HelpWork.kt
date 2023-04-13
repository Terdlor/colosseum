package com.terdev.colosseum.worker

import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.DocumentWork
import com.terdev.colosseum.common.TextException
import com.terdev.colosseum.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity
import java.io.File

@Component
class HelpWork {

    @Autowired
    private lateinit var env: Environment

    @Autowired
    lateinit var rsSH: SingleResponseHelper

    @Autowired
    lateinit var rsGH: GroupResponseHelper

    @Autowired
    lateinit var log: LogHelper

    @Autowired
    lateinit var rqH: RequestHelper

    @Autowired
    @Qualifier("commandWorkers")
    private lateinit var commandWorkers: List<CommandWork>

    @Autowired
    @Qualifier("documentWorkers")
    private lateinit var documentWorkers: List<DocumentWork>

    var command = "help"
    var commandDesc = "Список команд"

    fun commandWork() {

        val strBuild = StringBuilder()
        strBuild.appendLine("Список команд:")

        strBuild.appendLine("Для сообщения:")
        for (commandWork in commandWorkers) {
            strBuild.appendLine("/" + commandWork.command + " - " + commandWork.commandDesc)
        }
        strBuild.appendLine("/$command - $commandDesc")

        strBuild.appendLine("Для файла:")
        for (documentWork in documentWorkers) {
            strBuild.appendLine("/" + documentWork.command + " - " + documentWork.commandDesc)
        }

        // Выводим ответ в консоль
        kotlin.io.println(strBuild.toString())
    }

    fun checkWork(msg: Message): Boolean {
        if (msg.entities == null) return false
        val entity: MessageEntity? =
                msg.entities.stream().filter { en ->
                    en.type == "bot_command" &&
                            (en.text.equals("/$command") || en.text.equals("/$command@" + env.getProperty("telegram.botName")))
                }.findAny().orElse(null)
        if (entity != null) {
            commandWork()
            return true
        }
        return false
    }

    fun work(msg: Message): Boolean {
        try {
            return checkWork(msg)
        } catch (ex: Exception) {
            return if (ex is TextException) {
                kotlin.io.println("Нормальная ошибка - " + ex.msg)
                sendNotification(msg.chat.id, ex.msg)
                true
            } else {
                val str = Печататель().дайException(ex)
                println(str)
                sendNotification(msg.chat.id, str, msg.messageId)
                false
            }
        }
    }

    fun getParam(msg: String?): String {
        if (msg == null)
            return ""
        return msg.substringAfter("/$command@" + env.getProperty("telegram.botName")).trim().substringAfter("/$command").trim()
    }

    fun sendNotification(id: Long, msg: String, msgId: Int) {
        if (id > 0) {
            rsSH.sendSimpleNotification(id, msg, msgId)
        } else {
            rsGH.sendSimpleNotification(id, msg, msgId)
        }
    }

    fun sendNotification(id: Long, msg: String) {
        if (id > 0) {
            rsSH.sendSimpleNotification(id, msg)
        } else {
            rsGH.sendSimpleNotification(id, msg)
        }
    }

    fun sendFile(id: Long, file: File) {
        if (id > 0) {
            rsSH.sendSimpleFile(id, file)
        } else {
            rsGH.sendSimpleFile(id, file)
        }
    }

    fun getFile(fileId: String) = rqH.getFile(fileId)
}