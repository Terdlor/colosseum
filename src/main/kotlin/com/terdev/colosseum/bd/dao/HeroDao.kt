package com.terdev.colosseum.bd.dao

import com.terdev.colosseum.bd.model.Hero
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

interface HeroDao : Dao<Hero, Long> {

    @Throws(SQLException::class)
    fun findById(id: Long): Hero?
}