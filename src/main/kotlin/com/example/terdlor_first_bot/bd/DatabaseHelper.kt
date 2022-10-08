package com.example.terdlor_first_bot.bd

import com.example.terdlor_first_bot.bd.dao.ChatDao
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


class DatabaseHelper private constructor() {

    companion object {

        @Value("\${spring.datasource.username}")
        private val username: String = "sa"

        @Value("\${spring.datasource.password}")
        private val password: String = "111"

        @Value("\${spring.datasource.url}")
        private val url: String = "jdbc:h2:file:./data/testdb"

        var connectionSource : JdbcPooledConnectionSource? = null

        private fun instance() : ConnectionSource {
            if (connectionSource == null ||  !connectionSource?.isOpen("")!!) {
                connectionSource = JdbcPooledConnectionSource(url, username, password)
                when(DbInfoDaoImpl(connectionSource).lastVersion()) {
                    0L -> update1()
                    1L -> update2()
                    else -> println("---действий по изменению бд не требуется---")
                }
            }
            return connectionSource!!
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

        fun update1() {
            TableUtils.createTableIfNotExists(connectionSource, DbInfo::class.java)
            TableUtils.createTableIfNotExists(connectionSource, User::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Chat::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Message::class.java)
            DbInfoDaoImpl(connectionSource).save(1)
            println("---обновление бд до 1---")
            update2()
        }

        fun update2() {
            val dao = DbInfoDaoImpl(connectionSource)
            dao.executeRaw("ALTER TABLE `USERS` ADD COLUMN IF NOT EXISTS insert_date TIMESTAMP;")
            dao.executeRaw("ALTER TABLE `CHATS` ADD COLUMN IF NOT EXISTS insert_date TIMESTAMP;")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS insert_date TIMESTAMP;")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS rq VARCHAR2(4096);")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS rs VARCHAR2(4096);")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS rs_chat_id VARCHAR(255);")
            dao.save(2)
            println("---обновление бд до 2---")
        }
    }
}