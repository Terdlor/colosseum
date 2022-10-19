package com.example.terdlor_first_bot.bd.collosseum

import com.example.terdlor_first_bot.bd.collosseum.model.Battle
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface BattleDao : Dao<Battle, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Battle?
}