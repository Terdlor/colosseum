package com.terdev.colosseum.bd.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "COL_PLAYER")
class Player {

    @DatabaseField(generatedId = true)
    var id: Int = 1

    @DatabaseField
    var idUser: Long = -1

    @DatabaseField
    var name: String? = null

    @DatabaseField
    var experience: Long = 0
}