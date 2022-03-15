package com.example.terdlor_first_bot

import com.example.terdlor_first_bot.bd.DatabaseHelper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
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

        val messageDao = DatabaseHelper.getMessageDao()

        messageDao.saveIfNotExist(update.message)

        val text = update.message?.text

        val messageId = update.message.messageId
        //from
        val fromId = update.message.from.id
        val fromuserName = update.message.from.userName
        //chat
        val chatid = update.message.chat.id
        try {
            val strBuild = StringBuilder()
            strBuild.append(" <").append(sdfSSS.format(dateCurrentLocalStart)).appendLine(">")
            strBuild.appendLine()
            strBuild.appendLine(sdf.format(Date(update.message.date.toLong()*1000)))
            strBuild.append("messageId = ").appendLine(messageId)
            strBuild.append("text = ").appendLine(text)
            strBuild.appendLine()
            strBuild.appendLine("FROM")
            strBuild.append("fromId = ").appendLine(fromId)
            strBuild.append("fromfirstName = ").appendLine(update.message.from.firstName)
            strBuild.append("fromlastName = ").appendLine(update.message.from.lastName)
            strBuild.append("fromuserName = ").appendLine(update.message.from.userName)
            strBuild.appendLine()
            strBuild.appendLine("CHAT")
            strBuild.append("chatid = ").appendLine(chatid)
            strBuild.append("chatfirstName = ").appendLine(update.message.chat.firstName)
            strBuild.append("chatlastName = ").appendLine(update.message.chat.lastName)
            strBuild.append("chatuserName = ").appendLine(update.message.chat.userName)
            val dateCurrentLocalEnd = Date()
            strBuild.appendLine()
            strBuild.append(" <").append(sdfSSS.format(dateCurrentLocalEnd)).appendLine(">")

            val userDao = DatabaseHelper.getUserDao()
            val chatDao = DatabaseHelper.getChatDao()

            val sendIdList = HashMap<Long, Long>()

            val users = userDao.queryForAll()
            strBuild.appendLine()
            strBuild.appendLine("пользователи в БД")
            for (user in users) {
                strBuild.append(user.toStringWithOutEmpty())
                sendIdList.put(user.id, user.id)
            }

            val chats = chatDao.queryForAll()
            strBuild.appendLine()
            strBuild.appendLine("чаты в БД")
            for (chat in chats) {
                strBuild.append(chat.toStringWithOutEmpty())
                sendIdList.put(chat.id, chat.id)
            }


            println(strBuild.toString())

            val filename = sdfFile.format(dateCurrentLocalStart) + "-$fromuserName"
            saveLog(filename, strBuild.toString(), update.toString())


            if (update.message.newChatMembers.isNotEmpty()) {
                for (newUser in update.message.newChatMembers) {
                    userDao.saveIfNotExist(newUser)
                    sendSimpleNotification(chatid, "@" + newUser.userName + " Врывается в чат", messageId)
                }
                return
            }
            if (update.message.leftChatMember != null) {
                sendSimpleNotification(chatid, "@" + update.message.leftChatMember.userName + " Прощай", messageId)
                return
            }

            //пин+
            if (text == null) {
                return
            }

            if (text.equals("/dubasit")) {
                for (chatId in sendIdList.values) {
                    try {
                        if (chatId > 0) {
                            sendSimpleNotificationWithButton(chatId, "ПОРА ЗАБИВАТЬ!!!", messageId)
                        } else {
                            sendSimpleNotification(chatId, "ПОРА ЗАБИВАТЬ!!!", messageId)
                        }
                    } catch (e : org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException){

                    }
                }
                return
            }

            if (text.equals("СПААААМ")) {
                for (chatId in sendIdList.values) {
                    try {
                        if (chatId > 0) {
                            sendSimpleNotificationWithButton(chatId, "ПЕСПЕРЕПЕС", messageId)
                        } else {
                            sendSimpleNotification(chatId, "ПЕСПЕРЕПЕС", messageId)
                        }
                    } catch (e : org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException){

                    }
                }
                return
            }

            if (chatid == fromId) {
                sendSimpleNotificationWithButton(chatid, strBuild.toString(), messageId)
            }

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
            sendSimpleNotification(chatid, strBuild.toString(), messageId)

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

    private fun sendSimpleNotification(chatId: Long, responseText: String, num:  Int) {
        val out : String = responseText
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`")
        val responseMessage = SendMessage(chatId.toString(), "$num  -  $out")
        responseMessage.enableMarkdown(true)
        responseMessage.replyMarkup = getReplyRemove()
        execute(responseMessage)
    }

    private fun sendSimpleNotificationWithButton(chatId: Long, responseText: String, num:  Int) {
        val out : String = responseText
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`")
        val responseMessage = SendMessage(chatId.toString(), "$num  -  $out")
        responseMessage.enableMarkdown(true)
        // добавляем 4 кнопки
        responseMessage.replyMarkup = getReplyMarkup(
                listOf(
                        listOf("СПААААМ")
                        //  listOf("Кнопка 1", "Кнопка 2"),
                        //  listOf("Кнопка 3", "Кнопка 4")
                )
        )
        execute(responseMessage)
    }

    private fun getReplyRemove(): ReplyKeyboardRemove {
        val markup = ReplyKeyboardRemove()
        markup.removeKeyboard = true
        return markup
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