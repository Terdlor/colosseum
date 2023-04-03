package com.terdev.colosseum.worker

import com.terdev.colosseum.BotApp
import com.terdev.colosseum.bd.DatabaseHelper
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.DocumentWork
import com.terdev.colosseum.common.TextException
import com.terdev.colosseum.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity
import java.io.File
import java.util.*

@Component("helpWork")
class HelpWork {

    @Autowired
    lateinit var rsSH: SinglResponseHelper

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

    fun commandWork(msgBd: com.terdev.colosseum.bd.system.model.Message) {

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
        println(
            msgBd.text + " отправил " + DatabaseHelper.getUserDao()
                .findById(msgBd.from)?.userName + ", чат " + msgBd.chat + " в " + Date()
        )
        println(strBuild.toString())

        // Выводим ответ в чат телеги
        sendNotification(msgBd.chat, strBuild.toString())

        // Логируем
        msgBd.rs = strBuild.toString()
        msgBd.rs_chat_id = msgBd.chat.toString()
        DatabaseHelper.getMessageDao().update(msgBd)
    }

    fun checkWork(msg: Message, msgBd: com.terdev.colosseum.bd.system.model.Message): Boolean {
        if (msg.entities == null) return false
        val entity: MessageEntity? =
            msg.entities.stream().filter { en ->
                en.type == "bot_command" &&
                        (en.text.equals("/$command") || en.text.equals("/$command@" + BotApp.foo))
            }.findAny().orElse(null)
        if (entity != null) {
            commandWork(msgBd)
            return true
        }
        return false
    }

    fun work(msg: Message, msgBd: com.terdev.colosseum.bd.system.model.Message): Boolean {
        try {
            return checkWork(msg, msgBd)
        } catch (ex: Exception) {
            return if (ex is TextException) {
                println("Нормальная ошибка - " + ex.msg)
                log.saveLog(ex.msg, "НОРМ_ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
                sendNotification(msg.chat.id, ex.msg)
                true
            } else {
                val str = Печататель().дайException(ex)
                println(str)
                log.saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
                sendNotification(msg.chat.id, str, msg.messageId)
                false
            }
        }
    }

    fun getParam(msg: String?): String {
        if (msg == null)
            return ""
        return msg.substringAfter("/$command@" + BotApp.foo).trim().substringAfter("/$command").trim()
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