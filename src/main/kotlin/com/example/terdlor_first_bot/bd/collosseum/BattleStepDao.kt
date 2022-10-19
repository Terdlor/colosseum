package com.example.terdlor_first_bot.bd.collosseum

import com.example.terdlor_first_bot.bd.collosseum.model.BattleStep
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface BattleStepDao : Dao<BattleStep, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): BattleStep?
}