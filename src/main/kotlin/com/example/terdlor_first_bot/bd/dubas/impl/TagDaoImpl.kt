package com.example.terdlor_first_bot.bd.dubas.impl

import com.example.terdlor_first_bot.bd.dubas.TagDao
import com.example.terdlor_first_bot.bd.dubas.model.Tag
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class TagDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Tag, Long>(connectionSource, Tag::class.java), TagDao {

}