package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.BotApp
import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity
import java.util.*

@Component("lastSpamInfoWork")
class LastSpamInfoWork {

    @Autowired
    private lateinit var rshS: SinglResponseHelper
    @Autowired
    private lateinit var rshG: GroupResponseHelper
    @Autowired
    private lateinit var log : LogHelper

    fun work(msg : Message, msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) : Boolean {
        try {
            if (msg.entities == null) return false
            val entity : MessageEntity? =
                    msg.entities.stream().filter{ en -> en.type == "bot_command" &&
                            (en.text.equals("/last_spam") || en.text.equals("/last_spam@" + BotApp.foo)) }.findAny().orElse(null)
            if (entity != null) {
                val delay = EditValueHelper().strToIntDef(msg.text.substringAfter("/last_spam").trim(), 60)

                val strBuild = StringBuilder()
                strBuild.appendLine("СПАММ за последние $delay мин.")

                val lastSpamMap : Map<String, Int> =
                        DatabaseHelper.getMessageDao().getLastSpamCount(SpamMessageWork.spamMap.keys, delay)
                if (lastSpamMap.isEmpty()) {
                    strBuild.append("не обнаружено")
                } else {
                    for (spamE in lastSpamMap) {
                        strBuild.appendLine(spamE.key + " - " + spamE.value)
                    }
                }

                println(msg.text + " отправил " + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName + ", чат " + msg.chat + " в " + Date())
                println(strBuild.toString())

                if (msg.chat.id > 0) {
                    rshS.sendSimpleNotification(msg.chat.id, strBuild.toString(), msg.messageId)
                } else {
                    rshG.sendSimpleNotification(msg.chat.id, strBuild.toString(), msg.messageId)
                }
                msgBd.rs = strBuild.toString()
                msgBd.rs_chat_id = msg.chat.id.toString()
                DatabaseHelper.getMessageDao().update(msgBd)
                return true
            }
            return false
        } catch (ex : Exception) {
            val str =Печататель().дайException(ex)
            println(str)
            log.saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
            if (msg.chat.id > 0) {
                rshS.sendSimpleNotification(msg.chat.id, str, msg.messageId)
            } else {
                rshG.sendSimpleNotification(msg.chat.id, str, msg.messageId)
            }
            return false
        }
    }
}