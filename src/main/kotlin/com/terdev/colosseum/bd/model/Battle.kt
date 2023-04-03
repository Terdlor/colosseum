package com.terdev.colosseum.bd.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "COL_BATTLE")
class Battle {

    @DatabaseField(generatedId = true)
    var id: Int = 1

    @DatabaseField
    var idPlayer1: Long = -1

    @DatabaseField
    var idUser1: Long = -1

    @DatabaseField
    var idPlayer2: Long = -1

    @DatabaseField
    var idUser2: Long = -1

    @DatabaseField
    var date_of_battle: Date? = null

    @DatabaseField
    var result: String? = null
}