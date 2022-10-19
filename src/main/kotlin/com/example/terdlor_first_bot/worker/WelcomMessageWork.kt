package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.GroupResponseHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component("welcomMessageWork")
class WelcomMessageWork {

    @Autowired
    private lateinit var rshG: GroupResponseHelper


    fun work(msg : Message, msgBD : com.example.terdlor_first_bot.bd.chat.model.Message) : Boolean {
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
        return false
    }
}