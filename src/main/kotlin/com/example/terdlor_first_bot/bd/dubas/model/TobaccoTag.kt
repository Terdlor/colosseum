package com.example.terdlor_first_bot.bd.dubas.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "DBS_TOBACCO_TAG")
class TobaccoTag {

    @DatabaseField(generatedId = true)
    var id: Int = 1
    @DatabaseField
    var idTobacco: Int = 1
    @DatabaseField
    var idTag: Int = 1
    @DatabaseField
    var insert_date: Date? = null
}