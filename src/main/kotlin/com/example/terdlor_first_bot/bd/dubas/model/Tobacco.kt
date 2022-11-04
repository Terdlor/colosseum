package com.example.terdlor_first_bot.bd.dubas.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "DBS_TOBACCO")
class Tobacco {

    @DatabaseField(generatedId = true)
    var id: Int = 1
    @DatabaseField
    var idBrand: Int = 1
    @DatabaseField
    var idLine: Int = 1
    @DatabaseField
    var name: String? = null
    @DatabaseField
    var insert_date: Date? = null
}