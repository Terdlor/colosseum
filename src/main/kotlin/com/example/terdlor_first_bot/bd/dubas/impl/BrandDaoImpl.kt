package com.example.terdlor_first_bot.bd.dubas.impl

import com.example.terdlor_first_bot.bd.dubas.BrandDao
import com.example.terdlor_first_bot.bd.dubas.model.Brand
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class BrandDaoImpl(connectionSource: ConnectionSource?) : BaseDaoImpl<Brand, Long>(connectionSource, Brand::class.java), BrandDao {

}