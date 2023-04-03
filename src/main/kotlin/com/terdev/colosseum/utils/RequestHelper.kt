package com.terdev.colosseum.utils

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.GetFile
import java.io.File

class RequestHelper(tgbParam: TelegramLongPollingBot) {

    var tgb: TelegramLongPollingBot

    init {
        tgb = tgbParam
    }

    fun getFile(fileId: String): File {
        val getFileMethod = GetFile()
        getFileMethod.setFileId(fileId)
        val file = tgb.execute(getFileMethod)
        return tgb.downloadFile(file.getFilePath())
    }
}