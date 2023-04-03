package com.terdev.colosseum.bd.system.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "DB_INFO")
class DbInfo {

    @DatabaseField
    var version: Long = 0

    @DatabaseField()
    var updateDate: Date = Date()
}