package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "USERS")
class User {

    @DatabaseField
    var id: Long = -1
    @DatabaseField
    var isBot: Boolean? = null
    @DatabaseField
    var userName: String? = null
    @DatabaseField
    var firstName: String? = null
    @DatabaseField
    var lastName: String? = null
    @DatabaseField
    var languageCode: String? = null
    @DatabaseField
    var canJoinGroups: Boolean? = null
    @DatabaseField
    var canReadAllGroupMessages: Boolean? = null
    @DatabaseField
    var supportInlineQueries: Boolean? = null

    @DatabaseField()
    var insert_date: Date? = null

    override fun toString() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
                .append("id=$id, ").append("isBot=$isBot, ").append("userName=$userName, ")
                .append("firstName=$firstName, ").append("lastName=$lastName, ").append("languageCode=$languageCode, ")
                .append("canJoinGroups=$canJoinGroups, ").append("canReadAllGroupMessages=$canReadAllGroupMessages, ")
                .append("supportInlineQueries=$supportInlineQueries, ").append("insert_date=$insert_date").appendLine("}")
        return strBuild.toString()
    }

    fun toStringWithOutEmpty() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
        strBuild.append("id=$id")
        if (isBot != null) strBuild.append(", isBot=$isBot")
        if (userName != null) strBuild.append(", userName=$userName")
        if (firstName != null) strBuild.append(", firstName=$firstName")
        if (lastName != null) strBuild.append(", lastName=$lastName")
        if (languageCode != null) strBuild.append(", languageCode=$languageCode")
        if (canJoinGroups != null) strBuild.append(", canJoinGroups=$canJoinGroups")
        if (canReadAllGroupMessages != null) strBuild.append(", canReadAllGroupMessages=$canReadAllGroupMessages")
        if (supportInlineQueries != null) strBuild.append(", supportInlineQueries=$supportInlineQueries")
        if (insert_date != null) strBuild.append(", insert_date=$insert_date")
        strBuild.append("}")
        return strBuild.toString()
    }
}