package com.example.terdlor_first_bot.bd.collosseum

import com.example.terdlor_first_bot.bd.collosseum.model.Hero
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface HeroDao : Dao<Hero, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Hero?
}