package com.example.terdlor_first_bot.bd.dubas.impl

import com.example.terdlor_first_bot.bd.dubas.LineDao
import com.example.terdlor_first_bot.bd.dubas.model.Line
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class LineDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Line, Long>(connectionSource, Line::class.java), LineDao {

}