package com.example.terdlor_first_bot.bd.system.impl

import com.example.terdlor_first_bot.bd.system.DbInfoDao
import com.example.terdlor_first_bot.bd.system.model.DbInfo
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException
import java.util.*

class DbInfoDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<DbInfo, Long>(connectionSource, DbInfo::class.java), DbInfoDao {

    @Throws(SQLException::class)
    override fun save(ver : Long) {
        val dbInfo = DbInfo()
        dbInfo.version = ver
        dbInfo.updateDate = Date()
        create(dbInfo)
    }

    @Throws(SQLException::class)
    override fun lastVersion() : Long {
        return try {
            super.queryRawValue("select max(version) from DB_INFO")
        } catch (ex : SQLException) {
            0
        }
    }
}