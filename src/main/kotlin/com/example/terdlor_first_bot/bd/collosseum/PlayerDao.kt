package com.example.terdlor_first_bot.bd.collosseum

import com.example.terdlor_first_bot.bd.collosseum.model.Player
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface PlayerDao  : Dao<Player, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Player?
}