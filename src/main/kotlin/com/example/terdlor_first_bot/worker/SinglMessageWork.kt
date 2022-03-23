package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import com.example.terdlor_first_bot.utils.Печататель
import org.telegram.telegrambots.bots.TelegramLongPollingBot

class SinglMessageWork(tgb_p : TelegramLongPollingBot) : SystemMessageWork(tgb_p) {



    fun work(msg : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        try {
            if (workSpam(msg)) {
                return true
            }

            val strBuild = StringBuilder()
            strBuild.appendLine(Печататель().дайMessage(msg))
            strBuild.appendLine()
            strBuild.appendLine(Печататель().дайВсеUser())
            strBuild.appendLine()
            strBuild.appendLine(Печататель().дайВсеChat())

            println(strBuild.toString())

            SinglResponseHelper(tgb).sendSimpleNotification(msg.chat, strBuild.toString(), msg.messageId)

            LogHelper().saveLog(strBuild.toString(), "Singl-" + DatabaseHelper.getUserDao().findById(msg.from)?.userName!!)

            msg.rs = strBuild.toString()
            msg.rs_chat_id = msg.chat.toString()
            DatabaseHelper.getMessageDao().update(msg)

            return true
        } catch (ex : Exception){
            val str =Печататель().дайException(ex)
            println(str)
            LogHelper().saveLog(str, "ОШИБКА-Singl-" + DatabaseHelper.getUserDao().findById(msg.from)?.userName!!)
            SinglResponseHelper(tgb).sendSimpleNotification(msg.chat, str, msg.messageId)
            return false
        }
    }
}