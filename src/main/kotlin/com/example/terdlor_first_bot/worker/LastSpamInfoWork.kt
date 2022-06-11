package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity

class LastSpamInfoWork(tgb_p : TelegramLongPollingBot, rshS_p : ResponseHelper){

    private var tgb : TelegramLongPollingBot
    private var rsh : ResponseHelper

    init {
        tgb = tgb_p
        rsh = rshS_p
    }

    fun work(msg : Message, msg_bd : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        try {
            if (msg.entities == null) return false

            val entity : MessageEntity =
            msg.entities.stream().filter{ en -> en.type.equals("bot_command") && en.text.equals("/last_spam") }.findAny().orElse(null)
            if (entity != null) {
                val delay = EditValueHelper().strToIntDef(msg.text.substringAfter("/last_spam").trim(), 60)

                val strBuild = StringBuilder()
                strBuild.appendLine("СПАММ за последние " + delay + "миинут")


                //DatabaseHelper.getMessageDao().getLastSpamCount(SystemMessageWork(tgb).spamMap.keys)


                rsh.sendSimpleNotification(msg.chat.id, strBuild.toString(), msg.messageId)
                msg_bd.rs = strBuild.toString()
                msg_bd.rs_chat_id = msg.chat.id.toString()
                DatabaseHelper.getMessageDao().update(msg_bd)
                return true
            }
            return false
        } catch (ex : Exception) {
            val str =Печататель().дайException(ex)
            println(str)
            LogHelper().saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
            GroupResponseHelper(tgb).sendSimpleNotification(msg.chat.id, str, msg.messageId)
            return false
        }
    }
}