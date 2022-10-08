package com.example.terdlor_first_bot.bd.dao.impl

import com.example.terdlor_first_bot.bd.dao.ChatDao
import com.example.terdlor_first_bot.bd.model.Chat
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException
import java.util.*

class ChatDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Chat, Long>(connectionSource, Chat::class.java), ChatDao {

    @Throws(SQLException::class)
    override fun findById(id: Long): Chat?{
        val res = super.queryForEq("id", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }

    override fun saveIfNotExist(chatTG: org.telegram.telegrambots.meta.api.objects.Chat?) {
        if (chatTG != null)
            if  (findById(chatTG.id) == null ) {
                val chat = Chat()
                chat.id = chatTG.id
                chat.type = chatTG.type
                chat.title = chatTG.title
                chat.userName = chatTG.userName
                chat.firstName = chatTG.firstName
                chat.lastName = chatTG.lastName
                chat.allMembersAreAdministrators = chatTG.allMembersAreAdministrators
                //chat.photoId = chatTG.photo
                chat.description = chatTG.description
                chat.inviteLink = chatTG.inviteLink
                //chat.pinnedMessageId = chatTG.pinnedMessage
                chat.stickerSetName = chatTG.stickerSetName
                chat.canSetStickerSet = chatTG.canSetStickerSet
                if (chatTG.permissions != null) {
                    chat.permissionsCanSendMessages = chatTG.permissions.canSendMessages
                    chat.permissionsCanSendMediaMessages = chatTG.permissions.canSendMediaMessages
                    chat.permissionsCanSendPolls = chatTG.permissions.canSendPolls
                    chat.permissionsCanSendOtherMessages = chatTG.permissions.canSendOtherMessages
                    chat.permissionsCanAddWebPagePreviews = chatTG.permissions.canAddWebPagePreviews
                    chat.permissionsCanChangeInfo = chatTG.permissions.canChangeInfo
                    chat.permissionsCanInviteUsers = chatTG.permissions.canInviteUsers
                    chat.permissionsCanPinMessages = chatTG.permissions.canPinMessages
                }
                chat.slowModeDelay = chatTG.slowModeDelay
                chat.messageAutoDeleteTime = chatTG.messageAutoDeleteTime
                chat.insert_date = Date()
                create(chat)
            }
    }

    override fun getActive() : List<Chat> {
        return super.queryForAll()
    }
}