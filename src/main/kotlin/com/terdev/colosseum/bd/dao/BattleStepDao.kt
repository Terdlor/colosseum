package com.terdev.colosseum.bd.dao

import com.terdev.colosseum.bd.model.BattleStep
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface BattleStepDao : Dao<BattleStep, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): BattleStep?
}