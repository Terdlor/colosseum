package com.terdev.colosseum.utils

import com.terdev.colosseum.bd.DatabaseHelper
import com.terdev.colosseum.bd.system.model.Message
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

class Печататель {

    fun дайMessage(msg: Message): String {
        val strBuild = StringBuilder()
        strBuild.appendLine("сообщение:")
        strBuild.appendLine(msg.toStringWithOutEmpty())
        return strBuild.toString()
    }

    fun дайException(ex: Exception): String {
        val strBuild = StringBuilder()
        strBuild.appendLine("ОШИБКА")
        strBuild.appendLine(ex.toString())
        for (exst in ex.stackTrace) {
            strBuild.appendLine(exst.className + "." + exst.methodName + ":" + exst.lineNumber)
        }
        return strBuild.toString()
    }

    fun дайВсеUser(): String {
        val userDao = DatabaseHelper.getUserDao()
        val users = userDao.queryForAll()
        val strBuild = StringBuilder()
        strBuild.appendLine("пользователи в БД")
        for (user in users) {
            strBuild.appendLine(user.toStringWithOutEmpty())
        }
        return strBuild.toString()
    }

    fun дайВсеChat(): String {
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

//JEP 400: UTF-8 по умолчанию, Стандартизация UTF-8 во всех стандартных Java API, за исключением консольного ввода-вывода.
fun println(p: Any?) {
    PrintStream(FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8.toString()).println(p)
}