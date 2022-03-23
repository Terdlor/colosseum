package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.Печататель
import org.telegram.telegrambots.bots.TelegramLongPollingBot

class GroupMessageWork(tgb_p : TelegramLongPollingBot) : SystemMessageWork(tgb_p) {

    fun work(msg : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        try {
            if (workSpam(msg)) {
                return true
            }

            return true
        } catch (ex : Exception){
            val str =Печататель().дайException(ex)
            println(str)
            LogHelper().saveLog(str, "ОШИБКА-Group-" + DatabaseHelper.getUserDao().findById(msg.from)?.userName!!)
            GroupResponseHelper(tgb).sendSimpleNotification(msg.chat, str, msg.messageId)
            return false
        }
    }
}