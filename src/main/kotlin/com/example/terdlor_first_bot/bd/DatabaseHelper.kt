package com.example.terdlor_first_bot.bd

import com.example.terdlor_first_bot.bd.dao.ChatDao
import com.example.terdlor_first_bot.bd.dao.DbInfoDao
import com.example.terdlor_first_bot.bd.dao.MessageDao
import com.example.terdlor_first_bot.bd.dao.UserDao
import com.example.terdlor_first_bot.bd.dao.impl.ChatDaoImpl
import com.example.terdlor_first_bot.bd.dao.impl.DbInfoDaoImpl
import com.example.terdlor_first_bot.bd.dao.impl.MessageDaoImpl
import com.example.terdlor_first_bot.bd.dao.impl.UserDaoImpl
import com.example.terdlor_first_bot.bd.model.*
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import org.springframework.beans.factory.annotation.Value


class DatabaseHelper private constructor(){

    companion object {
        const val DATABASE_VERSION = 0

        @Value("\${spring.datasource.username}")
        private val username: String = "sa"

        @Value("\${spring.datasource.password}")
        private val password: String = "111"

        @Value("\${spring.datasource.url}")
        private val url: String = "jdbc:h2:file:./data/testdb"

        var connectionSource : JdbcPooledConnectionSource? = null

        private fun instance() : ConnectionSource {
            //TODO проверка на пригодность
            if (connectionSource == null ||  !connectionSource?.isOpen("")!!) {
            //todo удаление из памяти старого конекта
                connectionSource = JdbcPooledConnectionSource(url, username, password)
                //todo сделать запрос ласт версии без падения (таблицы нет)
                TableUtils.createTableIfNotExists(connectionSource, DbInfo::class.java)
                when(DbInfoDaoImpl(connectionSource).lastVersion() ?: 0) {
                           0L -> {
                               TableUtils.createTableIfNotExists(connectionSource, User::class.java)
                               TableUtils.createTableIfNotExists(connectionSource, Chat::class.java)
                               TableUtils.createTableIfNotExists(connectionSource, Message::class.java)
                               DbInfoDaoImpl(connectionSource).save(0)
                           }
                    else -> {
                        println("----------------------------444444----------------")
                    }
                }
            }
            //TODO проверка на консистентность
            return connectionSource ?: JdbcPooledConnectionSource(url, username, password)
        }

        fun close() {
            connectionSource?.close()
        }

        fun getUserDao() : UserDao {
            return UserDaoImpl(instance())
        }

        fun getChatDao() : ChatDao {
            return ChatDaoImpl(instance())
        }

        fun getMessageDao() : MessageDao {
            return MessageDaoImpl(instance())
        }

        fun getDbInfoDao() : DbInfoDao {
            return DbInfoDaoImpl(instance())
        }
    }
}