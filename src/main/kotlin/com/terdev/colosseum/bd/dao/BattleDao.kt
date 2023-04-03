package com.terdev.colosseum.bd.dao

import com.terdev.colosseum.bd.model.Battle
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface BattleDao : Dao<Battle, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Battle?
}