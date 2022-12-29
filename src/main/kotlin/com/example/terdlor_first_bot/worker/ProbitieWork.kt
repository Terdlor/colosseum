package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import com.example.terdlor_first_bot.common.CommandWork
import com.example.terdlor_first_bot.common.ProbitieMap
import com.example.terdlor_first_bot.common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component("probitieWork")
class ProbitieWork : CommandWork() {

    @Autowired
    private lateinit var probitieMap: ProbitieMap

    override var command = "probitie"
    override var commandDesc = "Пробить"

    override fun commandWork(msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) {

        val strBuild = StringBuilder()
        val user1 = DatabaseHelper.getUserDao().findById(msgBd.from) ?: throw TextException("ОТПРАВЛЯЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")

        strBuild.append(probitieMap.getProbitie(user1))

        // Выводим ответ в консоль
        println(msgBd.text + " отправил " + DatabaseHelper.getUserDao().findById(msgBd.from)?.userName + ", чат " + msgBd.chat + " в " + Date())
        println(strBuild.toString())

        // Выводим ответ в чат телеги
        sendReplyNotification(msgBd.chat, strBuild.toString(), msgBd.messageId)

        // Логируем
        msgBd.rs = strBuild.toString()
        msgBd.rs_chat_id = msgBd.chat.toString()
        DatabaseHelper.getMessageDao().update(msgBd)
    }
}