package com.terdev.colosseum.bd.dao.impl

import com.terdev.colosseum.bd.dao.PlayerDao
import com.terdev.colosseum.bd.model.Player
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException

class PlayerDaoImpl(connectionSource: ConnectionSource?) :
    BaseDaoImpl<Player, Long>(connectionSource, Player::class.java), PlayerDao {

    @Throws(SQLException::class)
    override fun findById(id: Long): Player? {
        val res = super.queryForEq("id", id)
        return if (res.isEmpty()) {
            null
        } else {
            res[0]
        }
    }
}