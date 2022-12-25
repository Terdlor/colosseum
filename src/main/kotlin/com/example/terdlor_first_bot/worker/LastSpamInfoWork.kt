package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import com.example.terdlor_first_bot.common.CommandWork
import com.google.common.collect.ImmutableList
import org.springframework.stereotype.Component
import java.util.*

@Component("lastSpamInfoWork")
class LastSpamInfoWork : CommandWork() {

    override var command = "last_spam"

    var commandSpamList = hashSetOf<String> (
            // Можно вводить любые команды
            // "probitie"
            )

    override fun commandWork(msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) {
        val delay = EditValueHelper().strToIntDef(getParam(msgBd.text), 60)

        val strBuild = StringBuilder()
        strBuild.appendLine("СПАММ за последние $delay мин.")

        commandSpamList.addAll(SpamMessageWork.spamMap.keys)

        val lastSpamMap : Map<String, Int> =
                DatabaseHelper.getMessageDao().getLastSpamCount(commandSpamList, delay)
        if (lastSpamMap.isEmpty()) {
            strBuild.append("не обнаружено")
        } else {
            for (spamE in lastSpamMap) {
                strBuild.appendLine(spamE.key + " - " + spamE.value)
            }
        }

        println(msgBd.text + " отправил " + DatabaseHelper.getUserDao().findById(msgBd.from)?.userName + ", чат " + msgBd.chat + " в " + Date())
        println(strBuild.toString())

        sendNotification(msgBd.chat, strBuild.toString(), msgBd.messageId)

        msgBd.rs = strBuild.toString()
        msgBd.rs_chat_id = msgBd.chat.toString()
        DatabaseHelper.getMessageDao().update(msgBd)
    }
}