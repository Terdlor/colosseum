package com.example.terdlor_first_bot.bd

import com.example.terdlor_first_bot.bd.model.*
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import org.springframework.beans.factory.annotation.Value


class DatabaseHelper private constructor(){

    companion object {
        const val DATABASE_VERSION = 1

        @Value("\${spring.datasource.username}")
        private val username: String = "sa"

        @Value("\${spring.datasource.password}")
        private val password: String = "111"

        @Value("\${spring.datasource.url}")
        private val url: String = "jdbc:h2:file:./data/testdb"

        var connectionSource : JdbcPooledConnectionSource = JdbcPooledConnectionSource(url, username, password)

        private fun instance() : ConnectionSource {
            //TODO проверка на пригодность
            if (!connectionSource.isOpen("")) {
            //todo удаление из памяти старого конекта
                connectionSource = JdbcPooledConnectionSource(url, username, password)
                TableUtils.createTableIfNotExists(connectionSource, User::class.java)
                TableUtils.createTableIfNotExists(connectionSource, Chat::class.java)
                TableUtils.createTableIfNotExists(connectionSource, Message::class.java)
            }
            //TODO проверка на консистентность
            return connectionSource
        }

        fun close() {
            connectionSource.close()
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
    }
}