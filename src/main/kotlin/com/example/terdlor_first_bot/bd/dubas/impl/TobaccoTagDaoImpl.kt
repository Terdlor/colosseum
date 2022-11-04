package com.example.terdlor_first_bot.bd.dubas.impl

import com.example.terdlor_first_bot.bd.dubas.TobaccoTagDao
import com.example.terdlor_first_bot.bd.dubas.model.TobaccoTag
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class TobaccoTagDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<TobaccoTag, Long>(connectionSource, TobaccoTag::class.java), TobaccoTagDao {

}