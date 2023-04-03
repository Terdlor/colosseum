package com.terdev.colosseum.bd.system

import com.terdev.colosseum.bd.system.model.DbInfo
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface DbInfoDao : Dao<DbInfo, Long> {

    @Throws(SQLException::class)
    fun save(ver: Long)

    @Throws(SQLException::class)
    fun lastVersion(): Long
}