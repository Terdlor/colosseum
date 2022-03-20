package com.example.terdlor_first_bot.bd.dao

import com.example.terdlor_first_bot.bd.model.DbInfo
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface DbInfoDao  : Dao<DbInfo, Long> {

    @Throws(SQLException::class)
    fun save(ver : Long)

    @Throws(SQLException::class)
    fun lastVersion() : Long
}