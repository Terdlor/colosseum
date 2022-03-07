package com.example.terdlor_first_bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.io.File
import java.nio.file.Paths
import java.util.*
import java.text.SimpleDateFormat

@Service
class BotApp : TelegramLongPollingBot() {

    @Value("\${telegram.botName}")
    private val botName: String = ""

    @Value("\${telegram.token}")
    private val token: String = ""

    override fun getBotUsername(): String = botName

    override fun getBotToken(): String = token

    private val sdfSSS = SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS")
    private val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
    private val sdfFile = SimpleDateFormat("yyyy.MM.dd_HH.mm.ss")

    override fun onUpdateReceived(update: Update) {
        val dateCurrentLocalStart = Date()

        val text = update.message.text
        val messageId = update.message.messageId
        val dateMsg = Date(update.message.date.toLong()*1000)
        val date = sdf.format(dateMsg)
        //from
        val fromId = update.message.from.id
        val fromfirstName = update.message.from.firstName
        val fromlastName = update.message.from.lastName
        val fromuserName = update.message.from.userName
        //chat
        val chatid = update.message.chat.id
        val chatfirstName = update.message.chat.firstName
        val chatlastName = update.message.chat.lastName
        val chatuserName = update.message.chat.userName
        try {
            val strBuild = StringBuilder()
            strBuild.append(" <").append(sdfSSS.format(dateCurrentLocalStart)).appendLine(">")
            strBuild.appendLine()
            strBuild.appendLine(date)
            strBuild.append("messageId = ").appendLine(messageId)
            strBuild.append("text = ").appendLine(text)
            strBuild.appendLine()
            strBuild.appendLine("FROM")
            strBuild.append("fromId = ").appendLine(fromId)
            strBuild.append("fromfirstName = ").appendLine(fromfirstName)
            strBuild.append("fromlastName = ").appendLine(fromlastName)
            strBuild.append("fromuserName = ").appendLine(fromuserName)
            strBuild.appendLine()
            strBuild.appendLine("CHAT")
            strBuild.append("chatid = ").appendLine(chatid)
            strBuild.append("chatfirstName = ").appendLine(chatfirstName)
            strBuild.append("chatlastName = ").appendLine(chatlastName)
            strBuild.append("chatuserName = ").appendLine(chatuserName)
            val dateCurrentLocalEnd = Date()
            strBuild.appendLine()
            strBuild.append(" <").append(sdfSSS.format(dateCurrentLocalEnd)).appendLine(">")

            println(strBuild.toString())
            val filename = sdfFile.format(dateCurrentLocalStart) + "-$fromuserName"
            saveLog(filename, strBuild.toString(), update.toString())
            sendSimpleNotification(chatid, strBuild.toString())

//        if (update.hasMessage()) {
//            val message = update.message
//            val chatId = message.chatId
//            val responseText = if (message.hasText()) {
//                val messageText = message.text
//                when {
//                    messageText == "/start" -> "Добро пожаловать!"
//                    messageText.startsWith("Кнопка ") -> "Вы нажали кнопку" // обработка нажатия кнопки
//                    else -> "Вы написали: *$messageText*"
//                }
//            } else {
//                "Я понимаю только текст"
//            }
//            sendNotification2(chatId, responseText)
//        }
        } catch (ex : Exception){
            val strBuild = StringBuilder()
            strBuild.append(" <").append(sdfSSS.format(dateCurrentLocalStart)).appendLine(">")
            strBuild.appendLine()
            strBuild.appendLine("ОШИБКА")
            strBuild.appendLine()
            strBuild.appendLine(ex.toString())
            strBuild.appendLine()
            for(exst in ex.stackTrace){
                strBuild.appendLine( exst.className+"."+exst.methodName+":"+exst.lineNumber)
            }
            val dateCurrentLocalEnd = Date()
            strBuild.appendLine()
            strBuild.append(" <").append(sdfSSS.format(dateCurrentLocalEnd)).appendLine(">")

            println(strBuild.toString())
            val filename = sdfFile.format(dateCurrentLocalStart) + "-$fromuserName"
            saveLog(filename, strBuild.toString(), update.toString())
            sendSimpleNotification(chatid, strBuild.toString())

            throw ex
        }
    }

    private fun saveLog(file: String, vararg responseText: String) {
        val wallpaperDirectory = File(Paths.get("").toAbsolutePath().toString()    + "\\log\\")
        wallpaperDirectory.mkdirs()
        val outputFile = File(wallpaperDirectory, "[$file].txt")
        outputFile.printWriter().use { out ->
            for(str in responseText){
                out.println(str)
            }
        }
    }



    private fun sendSimpleNotification(chatId: Long, responseText: String) {
        val out : String = responseText
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`")
        val responseMessage = SendMessage(chatId.toString(), out)
        responseMessage.enableMarkdown(true)
        execute(responseMessage)
    }

    private fun sendNotification2(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdown(true)
        // добавляем 4 кнопки
        responseMessage.replyMarkup = getReplyMarkup(
            listOf(
                listOf("Кнопка 1", "Кнопка 2"),
                listOf("Кнопка 3", "Кнопка 4")
            )
        )
        execute(responseMessage)
    }
    private fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboardMarkup {
        val markup = ReplyKeyboardMarkup()
        markup.keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        return markup
    }
}