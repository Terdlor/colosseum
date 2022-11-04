package com.example.terdlor_first_bot.content

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.common.CommandWork
import com.google.gson.GsonBuilder
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*


@Component("getBrandWork")
class GetBrandWork : CommandWork() {

    override var command = "sys_get_brands"

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss")

    override fun commandWork(msgBd: com.example.terdlor_first_bot.bd.chat.model.Message) {

        val brandList = DatabaseHelper.getBrandDao().queryForAll()
        val gsonPretty = GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create()

        val fileName = "BRANDS-${dateFormat.format(Date())}.json"
        val wallpaperDirectory = File(Paths.get("").toAbsolutePath().toString()    + "\\tmp\\")
        wallpaperDirectory.mkdirs()

        val file = File(wallpaperDirectory, fileName)
        file.writeText(gsonPretty.toJson(brandList))

        sendFile(msgBd.chat, file)
    }
}