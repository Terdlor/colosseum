package com.example.terdlor_first_bot.bd.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "USERS")
class User {

    @DatabaseField
    var id: Long? = null
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

    override fun toString() : String {
        val strBuild = StringBuilder()
        strBuild.append("{")
                .append("id=$id, ").append("isBot=$isBot, ").append("userName=$userName, ")
                .append("firstName=$firstName, ").append("lastName=$lastName, ").append("languageCode=$languageCode, ")
                .append("canJoinGroups=$canJoinGroups, ").append("canReadAllGroupMessages=$canReadAllGroupMessages, ")
                .append("supportInlineQueries=$supportInlineQueries").appendLine("}")
        return strBuild.toString()
    }
}