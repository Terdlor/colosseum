package com.example.terdlor_first_bot.bd.model

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "MESSAGE")
class Message {

    @DatabaseField(generatedId = false, id = true)
    var messageId: Int = 1
    @DatabaseField
    var from: Long = -1
    @DatabaseField
    var date: Int? = null
    @DatabaseField
    var chat: Long = -1
    @DatabaseField
    var forwardFrom: Long? = null
    @DatabaseField
    var forwardFromChat: Long? = null
    @DatabaseField
    var forwardDate: Int? = null
    @DatabaseField
    var text: String? = null

    @DatabaseField
    var insert_date: Date? = null
    @DatabaseField(columnDefinition = "VARCHAR2(4098)")
    var rq: String? = null
    @DatabaseField(columnDefinition = "VARCHAR2(4098)")
    var rs: String? = null
    @DatabaseField
    var rs_chat_id: String? = null

    override fun toString() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
                .append("messageId=$messageId, ").append("from=$from, ").append("date=$date, ")
                .append("chat=$chat, ").append("forwardFrom=$forwardFrom, ").append("forwardFromChat=$forwardFromChat, ")
                .append("forwardDate=$forwardDate, ").append("text=$text, ")
                .append("insert_date=$insert_date, ").append("rq=$rq, ").append("rs=$rs, ").append("rs_chat_id=$rs_chat_id").appendLine("}")
        return strBuild.toString()
    }

    fun toStringWithOutEmpty() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
        strBuild.append("messageId=$messageId")
        strBuild.append(", from=$from=")
        val userDao = DatabaseHelper.getUserDao()
        strBuild.append(userDao.findById(from)?.toStringWithOutEmpty())
        if (date != null) strBuild.append(", date=$date")
        strBuild.append(", chat=$chat=")
        val chatDao = DatabaseHelper.getChatDao()
        strBuild.append(chatDao.findById(chat)?.toStringWithOutEmpty())
        if (forwardFrom != null) strBuild.append(", forwardFrom=$forwardFrom")
        if (forwardFromChat != null) strBuild.append(", forwardFromChat=$forwardFromChat")
        if (forwardDate != null) strBuild.append(", forwardDate=$forwardDate")
        if (text != null) strBuild.append(", text=$text")
        if (insert_date != null) strBuild.append(", insert_date=$insert_date")
        //if (rq != null) strBuild.append(", rq=$rq")
        //if (rs != null) strBuild.append(", rs=$rs")
        //if (rs_chat_id != null) strBuild.append(", rs_chat_id=$rs_chat_id")
        strBuild.append("}")
        return strBuild.toString()
    }
}