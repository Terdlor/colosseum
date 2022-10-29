package common

import com.example.terdlor_first_bot.BotApp
import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity


abstract class CommandWork {

    @Autowired
    lateinit var rshS: SinglResponseHelper
    @Autowired
    lateinit var rshG: GroupResponseHelper
    @Autowired
    private lateinit var log : LogHelper

    abstract var command : String

    abstract fun commandWork(msg : Message, msgBd : com.example.terdlor_first_bot.bd.chat.model.Message)

    fun work(msg : Message, msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) : Boolean {
        try {
            if (msg.entities == null) return false
            val entity : MessageEntity? =
                    msg.entities.stream().filter{ en -> en.type == "bot_command" &&
                            (en.text.equals("/$command") || en.text.equals("/$command@" + BotApp.foo)) }.findAny().orElse(null)
            if (entity != null) {
                commandWork(msg, msgBd)
                return true
            }
            return false
        } catch (ex : Exception) {
            return if (ex is TextException) {
                println("Нормальная ошибка - " + ex.msg)
                log.saveLog(ex.msg, "НОРМ_ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
                sendNotification(msg.chat.id, ex.msg)
                false
            } else {
                val str = Печататель().дайException(ex)
                println(str)
                log.saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
                sendNotification(msg.chat.id, str, msg.messageId)
                false
            }
        }
    }

    fun getParam(msg : Message) : String {
        return msg.text.substringAfter("/$command@" + BotApp.foo).trim().substringAfter("/$command").trim()
    }

    fun sendNotification(id : Long, msg : String, msgId : Int) {
        if (id > 0) {
            rshS.sendSimpleNotification(id, msg, msgId)
        } else {
            rshG.sendSimpleNotification(id, msg, msgId)
        }
    }

    fun sendNotification(id : Long, msg : String) {
        if (id > 0) {
            rshS.sendSimpleNotification(id, msg)
        } else {
            rshG.sendSimpleNotification(id, msg)
        }
    }
}