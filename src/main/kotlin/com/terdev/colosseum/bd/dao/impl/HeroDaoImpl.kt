package com.terdev.colosseum.bd.dao.impl

import com.terdev.colosseum.bd.dao.HeroDao
import com.terdev.colosseum.bd.model.Hero
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException

class HeroDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Hero, Long>(connectionSource, Hero::class.java),
    HeroDao {

    @Throws(SQLException::class)
    override fun findById(id: Long): Hero? {
        val res = super.queryForEq("id", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }
}