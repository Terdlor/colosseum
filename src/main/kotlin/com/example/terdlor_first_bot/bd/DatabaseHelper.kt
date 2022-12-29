package com.example.terdlor_first_bot.bd

import com.example.terdlor_first_bot.bd.chat.model.Chat
import com.example.terdlor_first_bot.bd.chat.model.Message
import com.example.terdlor_first_bot.bd.chat.model.User
import com.example.terdlor_first_bot.bd.collosseum.impl.HeroDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.impl.PlayerDaoImpl
import com.example.terdlor_first_bot.bd.chat.impl.ChatDaoImpl
import com.example.terdlor_first_bot.bd.system.impl.DbInfoDaoImpl
import com.example.terdlor_first_bot.bd.chat.impl.MessageDaoImpl
import com.example.terdlor_first_bot.bd.chat.impl.UserDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.impl.BattleDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.impl.BattleStepDaoImpl
import com.example.terdlor_first_bot.bd.collosseum.model.Battle
import com.example.terdlor_first_bot.bd.collosseum.model.BattleStep
import com.example.terdlor_first_bot.bd.collosseum.model.Hero
import com.example.terdlor_first_bot.bd.collosseum.model.Player
import com.example.terdlor_first_bot.bd.dubas.impl.BrandDaoImpl
import com.example.terdlor_first_bot.bd.dubas.impl.LineDaoImpl
import com.example.terdlor_first_bot.bd.dubas.impl.TagDaoImpl
import com.example.terdlor_first_bot.bd.dubas.impl.TobaccoDaoImpl
import com.example.terdlor_first_bot.bd.dubas.impl.TobaccoTagDaoImpl
import com.example.terdlor_first_bot.bd.dubas.model.Brand
import com.example.terdlor_first_bot.bd.dubas.model.Line
import com.example.terdlor_first_bot.bd.dubas.model.Tag
import com.example.terdlor_first_bot.bd.dubas.model.Tobacco
import com.example.terdlor_first_bot.bd.dubas.model.TobaccoTag
import com.example.terdlor_first_bot.bd.system.model.DbInfo
import com.example.terdlor_first_bot.utils.println
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

        fun getBrandDao() = BrandDaoImpl(instance())
        fun getLineDao() = LineDaoImpl(instance())
        fun getTagDao() = TagDaoImpl(instance())
        fun getTobaccoDao() = TobaccoDaoImpl(instance())
        fun getTobaccoTagDao() = TobaccoTagDaoImpl(instance())

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

            TableUtils.createTableIfNotExists(connectionSource, Brand::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Line::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Tag::class.java)
            TableUtils.createTableIfNotExists(connectionSource, Tobacco::class.java)
            TableUtils.createTableIfNotExists(connectionSource, TobaccoTag::class.java)

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
            TableUtils.dropTable(getBrandDao(), true)
            TableUtils.dropTable(getLineDao(), true)
            TableUtils.dropTable(getTagDao(), true)
            TableUtils.dropTable(getTobaccoDao(), true)
            TableUtils.dropTable(getTobaccoTagDao(), true)
            println("---бд очищена---")
        }
    }
}