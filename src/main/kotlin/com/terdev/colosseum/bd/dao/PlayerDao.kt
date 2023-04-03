package com.terdev.colosseum.bd.dao

import com.terdev.colosseum.bd.model.Player
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface PlayerDao : Dao<Player, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Player?
}