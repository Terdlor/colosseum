package com.example.terdlor_first_bot.utils

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.bd.model.Message

class Печататель {

    fun дайMessage(msg : Message) : String {
        val strBuild = StringBuilder()
        strBuild.appendLine("сообщение:")
        strBuild.appendLine(msg.toStringWithOutEmpty())
        return strBuild.toString()
    }
    fun дайException(ex : Exception) : String {
        val strBuild = StringBuilder()
        strBuild.appendLine("ОШИБКА")
        strBuild.appendLine(ex.toString())
        for(exst in ex.stackTrace) {
            strBuild.appendLine( exst.className+"."+exst.methodName+":"+exst.lineNumber)
        }
        return strBuild.toString()
    }

    fun дайВсеUser() : String {
        val userDao = DatabaseHelper.getUserDao()
        val users = userDao.queryForAll()
        val strBuild = StringBuilder()
        strBuild.appendLine("пользователи в БД")
        for (user in users) {
            strBuild.appendLine(user.toStringWithOutEmpty())
        }
        return strBuild.toString()
    }

    fun дайВсеChat() : String {
        val chatDao = DatabaseHelper.getChatDao()
        val chats = chatDao.queryForAll()
        val strBuild = StringBuilder()
        strBuild.appendLine("чаты в БД")
        for (chat in chats) {
            strBuild.appendLine(chat.toStringWithOutEmpty())
        }
        return strBuild.toString()
    }


}