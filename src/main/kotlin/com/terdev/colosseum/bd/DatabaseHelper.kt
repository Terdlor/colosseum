package com.terdev.colosseum.bd

import com.terdev.colosseum.bd.system.impl.ChatDaoImpl
import com.terdev.colosseum.bd.system.impl.MessageDaoImpl
import com.terdev.colosseum.bd.system.impl.UserDaoImpl
import com.terdev.colosseum.bd.system.model.Chat
import com.terdev.colosseum.bd.system.model.Message
import com.terdev.colosseum.bd.system.model.User
import com.terdev.colosseum.bd.dao.impl.BattleDaoImpl
import com.terdev.colosseum.bd.dao.impl.BattleStepDaoImpl
import com.terdev.colosseum.bd.dao.impl.HeroDaoImpl
import com.terdev.colosseum.bd.dao.impl.PlayerDaoImpl
import com.terdev.colosseum.bd.model.Battle
import com.terdev.colosseum.bd.model.BattleStep
import com.terdev.colosseum.bd.model.Hero
import com.terdev.colosseum.bd.model.Player
import com.terdev.colosseum.bd.system.impl.DbInfoDaoImpl
import com.terdev.colosseum.bd.system.model.DbInfo
import com.terdev.colosseum.utils.println
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
        private val url: String = "jdbc:h2:file:./data/colosseumDb"

        private var connectionSource: JdbcPooledConnectionSource? = null

        private fun instance(): ConnectionSource {
            if (connectionSource == null || !connectionSource?.isOpen("")!!) {
                connectionSource = JdbcPooledConnectionSource(url, username, password)
                //truncateAll()
                when (DbInfoDaoImpl(connectionSource).lastVersion()) {
                    0L -> update1()
                    1L -> update2()
                    2L -> update3()
                    3L -> update4()
                    else -> println("---действий по изменению бд не требуется---")
                }
            }
            return connectionSource!!
        }

        fun close() {
            connectionSource?.close()
        }

        fun getUserDao() = UserDaoImpl(instance())
        fun getChatDao() = ChatDaoImpl(instance())
        fun getMessageDao() = MessageDaoImpl(instance())

        fun getHeroDao() = HeroDaoImpl(instance())
        fun getPlayerDao() = PlayerDaoImpl(instance())
        fun getBattleDao() = BattleDaoImpl(instance())
        fun getBattleStepDao() = BattleStepDaoImpl(instance())

        private fun update1() {
            TableUtils.createTableIfNotExists(connectionSource, DbInfo::class.java)
            TableUtils.createTableIfNotExists(connectionSource, User::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Chat::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Message::class.java)
            DbInfoDaoImpl(connectionSource).save(1)
            println("---обновление бд до v1---")
            update2()
        }

        private fun update2() {
            val dao = DbInfoDaoImpl(connectionSource)
            dao.executeRaw("ALTER TABLE `USERS` ADD COLUMN IF NOT EXISTS insert_date TIMESTAMP;")
            dao.executeRaw("ALTER TABLE `CHATS` ADD COLUMN IF NOT EXISTS insert_date TIMESTAMP;")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS insert_date TIMESTAMP;")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS rq VARCHAR2(4096);")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS rs VARCHAR2(4096);")
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS rs_chat_id VARCHAR(255);")
            dao.save(2)
            println("---обновление бд до v2---")
            update3()
        }

        private fun update3() {
            TableUtils.createTableIfNotExists(connectionSource, Player::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Hero::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Battle::class.java)
            TableUtils.createTableIfNotExists(connectionSource, BattleStep::class.java)

            val dao = DbInfoDaoImpl(connectionSource)
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS caption VARCHAR(255);")

            DbInfoDaoImpl(connectionSource).save(3)
            println("---обновление бд до v3---")
            update4()
        }

        private fun update4() {
            val dao = DbInfoDaoImpl(connectionSource)
            dao.executeRaw("ALTER TABLE `MESSAGE` ADD COLUMN IF NOT EXISTS replyMessageId NUMBER;")

            DbInfoDaoImpl(connectionSource).save(4)
            println("---обновление бд до v4---")
        }


        private fun truncateAll() {
            TableUtils.dropTable(DbInfoDaoImpl(connectionSource), true)
            TableUtils.dropTable(getUserDao(), true)
            TableUtils.dropTable(getChatDao(), true)
            TableUtils.dropTable(getMessageDao(), true)
            TableUtils.dropTable(getHeroDao(), true)
            TableUtils.dropTable(getPlayerDao(), true)
            TableUtils.dropTable(getBattleDao(), true)
            TableUtils.dropTable(getBattleStepDao(), true)
            println("---бд очищена---")
        }
    }
}