package com.terdev.colosseum.bd.system.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "CHATS")
class Chat {

    @DatabaseField(generatedId = false, id = true)
    var id: Long = -1

    @DatabaseField
    var type: String? = null

    @DatabaseField
    var title: String? = null

    @DatabaseField
    var firstName: String? = null

    @DatabaseField
    var lastName: String? = null

    @DatabaseField
    var userName: String? = null

    @DatabaseField
    var allMembersAreAdministrators: Boolean? = null

    @DatabaseField
    var photoId: Long? = null //ChatPhoto? = null

    @DatabaseField
    var description: String? = null

    @DatabaseField
    var inviteLink: String? = null

    //Message
    @DatabaseField
    var pinnedMessageId: Long? = null

    @DatabaseField
    var stickerSetName: String? = null

    @DatabaseField
    var canSetStickerSet: Boolean? = null

    var permissionsCanSendMessages: Boolean? = null

    @DatabaseField
    var permissionsCanSendMediaMessages: Boolean? = null

    @DatabaseField
    var permissionsCanSendPolls: Boolean? = null

    @DatabaseField
    var permissionsCanSendOtherMessages: Boolean? = null

    @DatabaseField
    var permissionsCanAddWebPagePreviews: Boolean? = null

    @DatabaseField
    var permissionsCanChangeInfo: Boolean? = null

    @DatabaseField
    var permissionsCanInviteUsers: Boolean? = null

    @DatabaseField
    var permissionsCanPinMessages: Boolean? = null

    @DatabaseField
    var slowModeDelay: Int? = null

    @DatabaseField
    var bio: String? = null

    @DatabaseField
    var linkedChatId: Long? = null

    //@DatabaseField
    //var location: ChatLocation? = null
    @DatabaseField
    var messageAutoDeleteTime: Int? = null

    @DatabaseField()
    var insert_date: Date? = null

    override fun toString(): String {
        val strBuild = StringBuilder()
        strBuild.append("{")
            .append("id=$id, ").append("type=$type, ").append("title=$title, ")
            .append("firstName=$firstName, ").append("lastName=$lastName, ").append("userName=$userName, ")
            .append("allMembersAreAdministrators=$allMembersAreAdministrators, ").append("photoId=$photoId, ")
            .append("description=$description").append("inviteLink=$inviteLink, ")
            .append("pinnedMessageId=$pinnedMessageId, ")
            .append("stickerSetName=$stickerSetName").append("canSetStickerSet=$canSetStickerSet, ")
            .append("permissionsCanSendMessages=$permissionsCanSendMessages")
            .append("permissionsCanSendMediaMessages=$permissionsCanSendMediaMessages, ")
            .append("permissionsCanSendPolls=$permissionsCanSendPolls, ")
            .append("permissionsCanSendOtherMessages=$permissionsCanSendOtherMessages")
            .append("permissionsCanAddWebPagePreviews=$permissionsCanAddWebPagePreviews, ")
            .append("permissionsCanChangeInfo=$permissionsCanChangeInfo, ")
            .append("permissionsCanInviteUsers=$permissionsCanInviteUsers")
            .append("permissionsCanPinMessages=$permissionsCanPinMessages, ")
            .append("slowModeDelay=$slowModeDelay").append("bio=$bio, ").append("linkedChatId=$linkedChatId, ")
            .append("messageAutoDeleteTime=$messageAutoDeleteTime, ").append("insert_date=$insert_date").appendLine("}")
        return strBuild.toString()
    }

    fun toStringWithOutEmpty(): String {
        val strBuild = StringBuilder()
        strBuild.append("{")
        strBuild.append("id=$id")
        if (type != null) strBuild.append(", type=$type")
        if (title != null) strBuild.append(", title=$title")
        if (userName != null) strBuild.append(", userName=$userName")
        if (firstName != null) strBuild.append(", firstName=$firstName")
        if (lastName != null) strBuild.append(", lastName=$lastName")
        if (allMembersAreAdministrators != null) strBuild.append(", allMembersAreAdministrators=$allMembersAreAdministrators")
        if (photoId != null) strBuild.append(", photoId=$photoId")
        if (description != null) strBuild.append(", description=$description")
        if (inviteLink != null) strBuild.append(", inviteLink=$inviteLink")
        if (pinnedMessageId != null) strBuild.append(", pinnedMessageId=$pinnedMessageId")
        if (stickerSetName != null) strBuild.append(", stickerSetName=$stickerSetName")
        if (canSetStickerSet != null) strBuild.append(", canSetStickerSet=$canSetStickerSet")
        if (permissionsCanSendMessages != null) strBuild.append(", permissionsCanSendMessages=$permissionsCanSendMessages")
        if (permissionsCanSendMediaMessages != null) strBuild.append(", permissionsCanSendMediaMessages=$permissionsCanSendMediaMessages")
        if (permissionsCanSendPolls != null) strBuild.append(", permissionsCanSendPolls=$permissionsCanSendPolls")
        if (permissionsCanSendOtherMessages != null) strBuild.append(", permissionsCanSendOtherMessages=$permissionsCanSendOtherMessages")
        if (permissionsCanAddWebPagePreviews != null) strBuild.append(", permissionsCanAddWebPagePreviews=$permissionsCanAddWebPagePreviews")
        if (permissionsCanChangeInfo != null) strBuild.append(", permissionsCanChangeInfo=$permissionsCanChangeInfo")
        if (permissionsCanInviteUsers != null) strBuild.append(", permissionsCanInviteUsers=$permissionsCanInviteUsers")
        if (permissionsCanPinMessages != null) strBuild.append(", permissionsCanPinMessages=$permissionsCanPinMessages")
        if (slowModeDelay != null) strBuild.append(", slowModeDelay=$slowModeDelay")
        if (bio != null) strBuild.append(", bio=$bio")
        if (linkedChatId != null) strBuild.append(", linkedChatId=$linkedChatId")
        if (messageAutoDeleteTime != null) strBuild.append(", messageAutoDeleteTime=$messageAutoDeleteTime")
        if (insert_date != null) strBuild.append(", insert_date=$insert_date")
        strBuild.append("}")
        return strBuild.toString()
    }
}