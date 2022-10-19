package com.example.terdlor_first_bot.bd

import com.example.terdlor_first_bot.bd.chat.ChatDao
import com.example.terdlor_first_bot.bd.chat.MessageDao
import com.example.terdlor_first_bot.bd.chat.UserDao
import com.example.terdlor_first_bot.bd.chat.model.Chat
import com.example.terdlor_first_bot.bd.chat.model.Message
import com.example.terdlor_first_bot.bd.chat.model.User
import com.example.terdlor_first_bot.bd.collosseum.HeroDao
import com.example.terdlor_first_bot.bd.collosseum.PlayerDao
import com.example.terdlor_first_bot.bd.collosseum.impl.HeroDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.impl.PlayerDaoImpl
import com.example.terdlor_first_bot.bd.chat.impl.ChatDaoImpl
import com.example.terdlor_first_bot.bd.system.impl.DbInfoDaoImpl
import com.example.terdlor_first_bot.bd.chat.impl.MessageDaoImpl
import com.example.terdlor_first_bot.bd.chat.impl.UserDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.BattleDao
import com.example.terdlor_first_bot.bd.collosseum.BattleStepDao
import com.example.terdlor_first_bot.bd.collosseum.impl.BattleDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.impl.BattleStepDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.model.Battle
import com.example.terdlor_first_bot.bd.collosseum.model.BattleStep
import com.example.terdlor_first_bot.bd.collosseum.model.Hero
import com.example.terdlor_first_bot.bd.collosseum.model.Player
import com.example.terdlor_first_bot.bd.system.model.DbInfo
import com.example.terdlor_first_bot.utils.println
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import org.springframework.beans.factory.annotation.Value

object DatabaseHelper {

    @Value("\${spring.datasource.username}")
    private val username: String = "sa"

    @Value("\${spring.datasource.password}")
    private val password: String = "111"

    @Value("\${spring.datasource.url}")
    private val url: String = "jdbc:h2:file:./data/testdb"

    private var connectionSource: JdbcPooledConnectionSource? = null

    private fun instance(): ConnectionSource {
        if (connectionSource == null || !connectionSource?.isOpen("")!!) {
            connectionSource = JdbcPooledConnectionSource(url, username, password)
            //truncateAll()
            when (DbInfoDaoImpl(connectionSource).lastVersion()) {
                0L -> update1()
                1L -> update2()
                2L -> update3()
                else -> println("---действий по изменению бд не требуется---")
            }
        }
        return connectionSource!!
    }

    fun close() {
        connectionSource?.close()
    }

    fun getUserDao(): UserDao {
        return UserDaoImpl(instance())
    }

    fun getChatDao(): ChatDao {
        return ChatDaoImpl(instance())
    }

    fun getMessageDao(): MessageDao {
        return MessageDaoImpl(instance())
    }

    fun getHeroDao(): HeroDao {
        return HeroDaoImpl(instance())
    }

    fun getPlayerDao(): PlayerDao {
        return PlayerDaoImpl(instance())
    }

    fun getBattleDao(): BattleDao {
        return BattleDaoImpl(instance())
    }

    fun getBattleStepDao(): BattleStepDao {
        return BattleStepDaoImpl(instance())
    }

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
        DbInfoDaoImpl(connectionSource).save(3)
        println("---обновление бд до v3---")
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
    }
}