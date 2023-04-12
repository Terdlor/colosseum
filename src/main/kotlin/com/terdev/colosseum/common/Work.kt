package com.terdev.colosseum.common

import com.terdev.colosseum.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File


abstract class Work {

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

    abstract var command: String
    abstract var commandDesc: String

    abstract fun checkWork(msg: Message): Boolean

    fun work(msg: Message): Boolean {
        try {
            return checkWork(msg)
        } catch (ex: Exception) {
            return if (ex is TextException) {
                println("Нормальная ошибка - " + ex.msg)
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

    fun sendReplyNotification(id: Long, msg: String, replyId: Int) {
        if (id > 0) {
            rsSH.sendReplyNotification(id, msg, replyId)
        } else {
            rsGH.sendReplyNotification(id, msg, replyId)
        }
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