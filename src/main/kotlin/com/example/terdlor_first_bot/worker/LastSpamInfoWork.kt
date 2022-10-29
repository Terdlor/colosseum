package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import common.CommandWork
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import java.util.*

@Component("lastSpamInfoWork")
class LastSpamInfoWork : CommandWork() {

    override var command = "last_spam"

    override fun commandWork(msg : Message, msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) {
        val delay = EditValueHelper().strToIntDef(getParam(msg), 60)

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

        sendNotification(msg.chat.id, strBuild.toString(), msg.messageId)

        msgBd.rs = strBuild.toString()
        msgBd.rs_chat_id = msg.chat.id.toString()
        DatabaseHelper.getMessageDao().update(msgBd)
    }
}