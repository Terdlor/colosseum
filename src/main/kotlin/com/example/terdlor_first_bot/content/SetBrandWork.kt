package com.example.terdlor_first_bot.content

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.bd.dubas.model.Brand
import com.example.terdlor_first_bot.common.DocumentWork
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Document
import java.util.*


@Component("setBrandWork")
class SetBrandWork : DocumentWork() {

    override var command = "sys_set_brands"
    override var commandDesc = "Запись брендов(content)"

    override fun commandWork(msgBd: com.example.terdlor_first_bot.bd.chat.model.Message, doc : Document) {

        DatabaseHelper.getBrandDao().removeAll {true}
        sendNotification(msgBd.chat, "ВСЕ УДАЛЕНО")

        val file =  getFile(doc.fileId)

        val res = file.inputStream().bufferedReader().use { it.readText() }

        val arrayBrendType = object : TypeToken<Array<Brand>>() {}.type

        val brends: Array<Brand> = Gson().fromJson(res, arrayBrendType)
        brends.forEach { it.insert_date = Date();  DatabaseHelper.getBrandDao().create(it) }

        sendNotification(msgBd.chat, "чот загружено")


    }
}