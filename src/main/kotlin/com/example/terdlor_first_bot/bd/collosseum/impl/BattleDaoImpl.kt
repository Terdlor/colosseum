package com.example.terdlor_first_bot.bd.collosseum.impl

import com.example.terdlor_first_bot.bd.collosseum.BattleDao
import com.example.terdlor_first_bot.bd.collosseum.model.Battle
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException

class BattleDaoImpl (connectionSource: ConnectionSource?) : BaseDaoImpl<Battle, Long>(connectionSource, Battle::class.java), BattleDao {

    @Throws(SQLException::class)
    override fun findById(id: Long): Battle?{
        val res = super.queryForEq("id", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }
}