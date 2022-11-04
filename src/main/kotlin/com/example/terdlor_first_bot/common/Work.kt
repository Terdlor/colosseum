package com.example.terdlor_first_bot.common

import com.example.terdlor_first_bot.BotApp
import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File


abstract class Work {

    @Autowired
    lateinit var rsSH: SinglResponseHelper
    @Autowired
    lateinit var rsGH: GroupResponseHelper
    @Autowired
    lateinit var log : LogHelper
    @Autowired
    lateinit var rqH: RequestHelper

    abstract var command : String

    abstract fun checkWork(msg : Message, msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) : Boolean

    fun work(msg : Message, msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) : Boolean {
        try {
            return checkWork(msg, msgBd)
        } catch (ex : Exception) {
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

    fun getParam(msg : String?) : String {
        if (msg == null)
            return ""
        return msg.substringAfter("/$command@" + BotApp.foo).trim().substringAfter("/$command").trim()
    }

    fun sendNotification(id : Long, msg : String, msgId : Int) {
        if (id > 0) {
            rsSH.sendSimpleNotification(id, msg, msgId)
        } else {
            rsGH.sendSimpleNotification(id, msg, msgId)
        }
    }

    fun sendNotification(id : Long, msg : String) {
        if (id > 0) {
            rsSH.sendSimpleNotification(id, msg)
        } else {
            rsGH.sendSimpleNotification(id, msg)
        }
    }

    fun sendFile(id : Long, file: File) {
        if (id > 0) {
            rsSH.sendSimpleFile(id, file)
        } else {
            rsGH.sendSimpleFile(id, file)
        }
    }

    fun getFile(fileId : String) = rqH.getFile(fileId)
}