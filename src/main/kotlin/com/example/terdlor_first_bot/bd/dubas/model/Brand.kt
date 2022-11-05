package com.example.terdlor_first_bot.bd.dubas.model

import com.google.gson.annotations.Expose
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "DBS_BRAND")
class Brand {

    @Expose
    @DatabaseField(generatedId = true)
    var  id: Int = 1
    @Expose
    @DatabaseField
    var name: String? = null
    @Expose
    @DatabaseField
    var hasLines: Boolean = false
    @Expose
    @DatabaseField
    var insert_date: Date? = null
}