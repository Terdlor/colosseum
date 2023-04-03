package com.terdev.colosseum.bd.system.impl

import com.terdev.colosseum.bd.system.UserDao
import com.terdev.colosseum.bd.system.model.User
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException
import java.util.*


class UserDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<User, Long>(connectionSource, User::class.java),
    UserDao {

    @Throws(SQLException::class)
    override fun findByName(name: String?): User? {
        val res = super.queryForEq("userName", name)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }

    @Throws(SQLException::class)
    override fun findById(id: Long): User? {
        val res = super.queryForEq("id", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }

    override fun saveIfNotExist(userTG: org.telegram.telegrambots.meta.api.objects.User?) {
        if (userTG != null && findById(userTG.id) == null) {
            val user = User()
            user.id = userTG.id
            user.isBot = userTG.isBot
            user.userName = userTG.userName
            user.firstName = userTG.firstName
            user.lastName = userTG.lastName
            user.languageCode = userTG.languageCode
            user.canJoinGroups = userTG.canJoinGroups
            user.canReadAllGroupMessages = userTG.canReadAllGroupMessages
            user.supportInlineQueries = userTG.supportInlineQueries
            user.insert_date = Date()
            create(user)
        }
    }
}