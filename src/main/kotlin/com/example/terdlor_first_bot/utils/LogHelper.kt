package com.example.terdlor_first_bot.utils

import java.io.File
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

class LogHelper {

    private val sdfFile = SimpleDateFormat("yyyy.MM.dd_HH.mm.ss")

    fun saveLog(responseText: String, partName : String) {
        val dateCurrentLocalStart = Date()
        val filename = sdfFile.format(dateCurrentLocalStart) + "-$partName"
        val wallpaperDirectory = File(Paths.get("").toAbsolutePath().toString()    + "\\log\\")
        wallpaperDirectory.mkdirs()
        val outputFile = File(wallpaperDirectory, "[$filename].txt")
        outputFile.printWriter().use { out ->
            for(str in responseText) {
                out.print(str)
            }
        }
    }
}