package com.example.terdlor_first_bot.utils

class EditValueHelper {

    fun strToIntDef(text: String, def : Int) : Int {
        return try {
            text.toInt()
        } catch (e : Exception) {
            def
        }
    }
}