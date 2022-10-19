package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.bd.chat.model.Message
import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.Печататель
import com.example.terdlor_first_bot.utils.println
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("groupMessageWorkBean")
class GroupMessageWork {

    @Autowired
    private lateinit var rshG: GroupResponseHelper
    @Autowired
    private lateinit var log : LogHelper

    fun work(msg : Message) : Boolean {
        try {
            return true
        } catch (ex : Exception) {
            val str =Печататель().дайException(ex)
            println(str)
            log.saveLog(str, "ОШИБКА-Group-" + DatabaseHelper.getUserDao().findById(msg.from)?.userName!!)
            rshG.sendSimpleNotification(msg.chat, str, msg.messageId)
            return false
        }
    }
}