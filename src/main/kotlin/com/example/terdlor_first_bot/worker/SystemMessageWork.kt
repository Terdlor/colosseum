package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.ResponseHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import java.util.*
import kotlin.collections.ArrayList

open class SystemMessageWork(tgb_p : TelegramLongPollingBot) {

    var tgb : TelegramLongPollingBot
    var rshS : ResponseHelper
    var rshG : ResponseHelper
    var spamMap : Map<String, String>


    init {
        tgb = tgb_p
        rshS = SinglResponseHelper(tgb)
        rshG = GroupResponseHelper(tgb)
        spamMap = mapOf(
                "dubasit" to "ПОРА ЗАБИВАТЬ!!!",
                "СПААААМ" to "ПЕСПЕРЕПЕС",
                "srat_v_trubi" to "В ТРУБЫ...... НАСРААААЛИ",
                "ПИВА" to "ПИВА")
    }

    open fun work(msg : Message, msg_bd : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        if (msg.newChatMembers.isNotEmpty()) {
            for (newUser in msg.newChatMembers) {
                val userDao = DatabaseHelper.getUserDao()
                userDao.saveIfNotExist(newUser)
                rshG.sendSimpleNotification(msg.chat.id, "@" + newUser.userName + " Врывается в чат", msg.messageId)
            }
            return true
        }
        if (msg.leftChatMember != null) {
            rshG.sendSimpleNotification(msg.chat.id, "@" + msg.leftChatMember.userName + " Прощай", msg.messageId)
            return true
        }

        //пин+
        if (msg.text == null) {
            return true
        }

        var rshSystem  = if (msg.chat.id > 0) { rshS } else { rshG }

        if (LastSpamInfoWork(tgb, rshSystem).work(msg, msg_bd)) {
            return true
        }

        if (StateTestWork(tgb).work(msg, msg_bd)) {
            return true
        }

        return false
    }

    fun workSpam(msg : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        var isWork = false
        for (ent in spamMap) {
            if (msg.text != null && msg.text!!.contains(ent.key, true)) {
                val sendIds = ArrayList<Long>()
                for (chat in DatabaseHelper.getChatDao().getActive()) {
                    try {
                        if (chat.id > 0) {
                            rshS.sendSimpleNotification(chat.id, ent.value, msg.messageId)
                        } else {
                            rshG.sendSimpleNotification(chat.id, ent.value, msg.messageId)
                        }
                        sendIds.add(chat.id)
                    } catch (e : org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException){

                    }
                }
                msg.rs = spamMap.get(msg.text)
                msg.rs_chat_id = sendIds.toString()
                DatabaseHelper.getMessageDao().update(msg)

                println(msg.text + " отправил " + DatabaseHelper.getUserDao().findById(msg.from)?.userName + ", чат " + msg.chat + " в " + Date())
                LogHelper().saveLog(msg.text + " отправил " + DatabaseHelper.getUserDao().findById(msg.from)?.userName + ", чат " + msg.chat + " в " + Date(),
                        DatabaseHelper.getUserDao().findById(msg.from)?.userName!!)
                isWork = true
            }
        }
        return isWork
    }
}