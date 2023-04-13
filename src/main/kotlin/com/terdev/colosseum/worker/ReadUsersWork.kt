package com.terdev.colosseum.worker

import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.jpa.dao.UserRepository
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import java.text.SimpleDateFormat

@Component
class ReadUsersWork : CommandWork() {

    override var command = "getUsers"
    override var commandDesc = "Дать список пользователей"

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss")

    @Autowired
    lateinit var userRepository: UserRepository

    override fun commandWork(msg: Message) {
        val usersList = userRepository.findAll()
        val strBuild = StringBuilder()
        if (msg.from.id == ***) {
            usersList.forEach {
                strBuild.appendLine("\n${it.uuid} | ${it.telegramId} | ${it.telegramHandle} | ${it.isBot} | ${it.firstName} | ${it.lastName} | ${it.insertDate} | ${it.battleState}")
            }
        }
        else {
            strBuild.appendLine("\nНедостаточно прав для вызова команды")
        }
        rsSH.sendSimpleNotification(msg.chatId, strBuild.toString())
    }
}