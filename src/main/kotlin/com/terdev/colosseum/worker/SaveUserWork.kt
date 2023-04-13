package com.terdev.colosseum.worker

import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.jpa.dao.UserRepository
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import java.text.SimpleDateFormat

@Component
class SaveUserWork : CommandWork() {

    override var command = "saveMe"
    override var commandDesc = "Сохранение пользователя"

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss")

    @Autowired
    lateinit var userRepository: UserRepository

    override fun commandWork(msg: Message) {
        val strBuild = StringBuilder()

        if (!checkUserExists(msg)) {
            val user = User()
            user.telegramId = msg.from.id
            user.telegramHandle = msg.from.userName
            user.isBot = msg.from.isBot
            user.firstName = msg.from.firstName
            user.lastName = msg.from.lastName

            userRepository.save(user)
            strBuild.appendLine("Пользователь создан")
        } else {
            strBuild.appendLine("\nПользователь уже существует")
        }

        rsSH.sendSimpleNotification(msg.chatId, strBuild.toString())
    }

    fun checkUserExists(msg: Message): Boolean {
        return userRepository.countByTelegramId(msg.from.id) > 0
    }

}