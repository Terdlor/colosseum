package com.example.terdlor_first_bot.bd.dao.impl

import com.example.terdlor_first_bot.bd.dao.UserDao
import com.example.terdlor_first_bot.bd.model.User
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException


class UserDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<User, Long>(connectionSource, User::class.java), UserDao {

    @Throws(SQLException::class)
    override fun findByName(name: String?): User? {
        val res = super.queryForEq("userName", name)
        if (res.isEmpty()){
            return null
        } else {
            return res[0]
        }
    }

    @Throws(SQLException::class)
    override fun findById(id: Long): User? {
        val res = super.queryForEq("id", id)
        if (res.isEmpty()){
            return null
        } else {
            return res[0]
        }
    }

    override fun saveIfNotExist(userTG: org.telegram.telegrambots.meta.api.objects.User?) {
        if (userTG != null)
        if  (findById(userTG.id) == null) {
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
            create(user)
        }
    }
}