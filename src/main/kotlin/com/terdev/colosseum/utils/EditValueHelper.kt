package com.terdev.colosseum.utils

class EditValueHelper {

    fun strToIntDef(text: String, def: Int): Int {
        return try {
            text.toInt()
        } catch (e: Exception) {
            def
        }
    }

    fun nick(text: String): String {
        return text.substringAfter("@").substringBefore(" ")
    }
}