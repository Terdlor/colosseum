package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import com.example.terdlor_first_bot.utils.println
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList

@Component("spamMessageWork")
class SpamMessageWork {

    companion object {
        var spamMap = mapOf(
                "dubasit" to "ПОРА ЗАБИВАТЬ!!!",
                "СПААААМ" to "ПЕСПЕРЕПЕС",
                "srat_v_trubi" to "В ТРУБЫ...... НАСРААААЛИ",
                "ПИВА" to "ПИВА")
    }

    @Autowired
    private lateinit var rshS: SinglResponseHelper
    @Autowired
    private lateinit var rshG: GroupResponseHelper
    @Autowired
    private lateinit var log : LogHelper

    fun work(msg : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
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
                    } catch (e : org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException) { }
                }
                if (StringUtils.isEmpty(msg.rs)) {
                    msg.rs = ent.value
                } else {
                    msg.rs = msg.rs + "-Мультиспамм-" +ent.value
                }
                msg.rs_chat_id = sendIds.toString()
                DatabaseHelper.getMessageDao().update(msg)

                println(msg.text + " отправил " + DatabaseHelper.getUserDao().findById(msg.from)?.userName + ", чат " + msg.chat + " в " + Date())
                log.saveLog(msg.text + " отправил " + DatabaseHelper.getUserDao().findById(msg.from)?.userName + ", чат " + msg.chat + " в " + Date(),
                        DatabaseHelper.getUserDao().findById(msg.from)?.userName!!)
                isWork = true
            }
        }
        return isWork
    }
}