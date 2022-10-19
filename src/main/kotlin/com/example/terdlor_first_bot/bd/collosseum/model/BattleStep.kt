package com.example.terdlor_first_bot.bd.collosseum.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "COL_BATTLE_STEP")
class BattleStep {

    @DatabaseField(generatedId = true)
    var id: Int = 1
    @DatabaseField
    var idBattle: Long = -1
    @DatabaseField
    var step: Long = -1
    @DatabaseField
    var description: String? = null
}