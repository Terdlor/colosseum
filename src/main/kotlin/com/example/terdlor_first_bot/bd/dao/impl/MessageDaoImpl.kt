package com.example.terdlor_first_bot.bd.dao.impl

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.bd.dao.MessageDao
import com.example.terdlor_first_bot.bd.model.Message
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.GenericRawResults
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException
import java.util.*
import kotlin.collections.HashMap


class MessageDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Message, Long>(connectionSource, Message::class.java), MessageDao {

    @Throws(SQLException::class)
    override fun findById(id: Int): Message?{
        val res = super.queryForEq("messageId", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }

    override fun saveIfNotExist(messageTG : org.telegram.telegrambots.meta.api.objects.Message?) {
        if (messageTG != null)
            if  (findById(messageTG.messageId) == null) {
                save(messageTG)
            } else {
                com.example.terdlor_first_bot.utils.println("------------------------------KAK--------------------------")
            }
    }

    override fun save(messageTG : org.telegram.telegrambots.meta.api.objects.Message) : Message {
        val message = Message()
        message.messageId = messageTG.messageId

        val userDao = DatabaseHelper.getUserDao()
        userDao.saveIfNotExist(messageTG.from)
        message.from = messageTG.from.id

        message.date = messageTG.date

        val chatDao = DatabaseHelper.getChatDao()
        chatDao.saveIfNotExist(messageTG.chat)
        message.chat = messageTG.chat.id

        if (messageTG.forwardFrom != null) {
            userDao.saveIfNotExist(messageTG.forwardFrom)
            message.forwardFrom = messageTG.forwardFrom.id
        }

        if (messageTG.forwardFromChat != null) {
            chatDao.saveIfNotExist(messageTG.forwardFromChat)
            message.forwardFromChat = messageTG.forwardFromChat.id
        }

        message.forwardDate = messageTG.forwardDate
        message.text = messageTG.text
        message.insert_date = Date()
        message.rq = messageTG.toString()
        create(message)
        return message
    }

    override fun getLastSpamCount(keyWord : Set<String>, delay : Int) : Map<String, Int> {
        val strBuild = StringBuilder()
        strBuild.append("(")
        val iter = keyWord.iterator()
        if (iter.hasNext()) {
            strBuild.append("UPPER(TEXT) LIKE UPPER('%${iter.next()}%')")
        }
        while (iter.hasNext()) {
            strBuild.append(" or UPPER(TEXT) LIKE UPPER('%${iter.next()}%')")
        }
        strBuild.append(")")

        val results = HashMap<String, Int>()
        val resultArray : GenericRawResults<Array<String>> =
                super.queryRaw("select \"FROM\", count(*) from MESSAGE where " +
                        strBuild.toString() +
                        " and INSERT_DATE > CURRENT_TIMESTAMP() - INTERVAL $delay MINUTE " +
                        " group by \"FROM\"")
        for (result in resultArray) {
            val userDao = DatabaseHelper.getUserDao()
            results["@" + userDao.findById(result[0].toLong())?.userName] = result[1].toInt()
        }
        return results
    }
}