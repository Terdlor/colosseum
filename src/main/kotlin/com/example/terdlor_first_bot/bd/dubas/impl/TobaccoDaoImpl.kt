package com.example.terdlor_first_bot.bd.dubas.impl

import com.example.terdlor_first_bot.bd.dubas.TobaccoDao
import com.example.terdlor_first_bot.bd.dubas.model.Tobacco
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class TobaccoDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Tobacco, Long>(connectionSource, Tobacco::class.java), TobaccoDao {

}