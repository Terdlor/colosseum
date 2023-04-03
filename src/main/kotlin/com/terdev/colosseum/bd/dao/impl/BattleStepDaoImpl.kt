package com.terdev.colosseum.bd.dao.impl

import com.terdev.colosseum.bd.dao.BattleStepDao
import com.terdev.colosseum.bd.model.BattleStep
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException

class BattleStepDaoImpl(connectionSource: ConnectionSource?) :
    BaseDaoImpl<BattleStep, Long>(connectionSource, BattleStep::class.java), BattleStepDao {

    @Throws(SQLException::class)
    override fun findById(id: Long): BattleStep? {
        val res = super.queryForEq("id", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }
}