package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.BotApp
import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity
import java.util.*

class LastSpamInfoWork  constructor(private val rshParam : ResponseHelper) {


    fun work(msg : Message, msgBd : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        try {
            if (msg.entities == null) return false
            val entity : MessageEntity? =
                    msg.entities.stream().filter{ en -> en.type.equals("bot_command") &&
                            (en.text.equals("/last_spam") || en.text.equals("/last_spam@" + BotApp.foo)) }.findAny().orElse(null)
            if (entity != null) {
                val delay = EditValueHelper().strToIntDef(msg.text.substringAfter("/last_spam").trim(), 60)

                val strBuild = StringBuilder()
                strBuild.appendLine("СПАММ за последние $delay мин.")

                val lastSpamMap : Map<String, Int> =
                        DatabaseHelper.getMessageDao().getLastSpamCount(SystemMessageWork.spamMap.keys, delay)
                if (lastSpamMap.isEmpty()) {
                    strBuild.append("не обнаружено")
                } else {
                    for (spamE in lastSpamMap) {
                        strBuild.appendLine(spamE.key + " - " + spamE.value)
                    }
                }

                println(msg.text + " отправил " + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName + ", чат " + msg.chat + " в " + Date())
                println(strBuild.toString())

                rshParam.sendSimpleNotification(msg.chat.id, strBuild.toString(), msg.messageId)
                msgBd.rs = strBuild.toString()
                msgBd.rs_chat_id = msg.chat.id.toString()
                DatabaseHelper.getMessageDao().update(msgBd)
                return true
            }
            return false
        } catch (ex : Exception) {
            val str =Печататель().дайException(ex)
            println(str)
            LogHelper().saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
            rshParam.sendSimpleNotification(msg.chat.id, str, msg.messageId)
            return false
        }
    }
}