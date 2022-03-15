package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.field.DatabaseField

class Message {

    @DatabaseField
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

    override fun toString() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
                .append("messageId=$messageId, ").append("from=$from, ").append("date=$date, ")
                .append("chat=$chat, ").append("forwardFrom=$forwardFrom, ").append("forwardFromChat=$forwardFromChat, ")
                .append("forwardDate=$forwardDate, ").append("text=$text").appendLine("}")
        return strBuild.toString()
    }

    fun toStringWithOutEmpty() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
        strBuild.append("messageId=$messageId")
        strBuild.append(", from=$from")
        if (date != null) strBuild.append(", date=$date")
        strBuild.append(", chat=$chat")
        if (forwardFrom != null) strBuild.append(", forwardFrom=$forwardFrom")
        if (forwardFromChat != null) strBuild.append(", forwardFromChat=$forwardFromChat")
        if (forwardDate != null) strBuild.append(", forwardDate=$forwardDate")
        if (text != null) strBuild.append(", text=$text")
        strBuild.appendLine("}")
        return strBuild.toString()
    }
}