package com.terdev.colosseum.worker

import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.ForwardWork
import com.terdev.colosseum.jpa.dao.UserRepository
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import java.text.SimpleDateFormat

@Component
class SaveForwardedUserWork : ForwardWork() {

    override var command = ""
    override var commandDesc = "Сохранение пользователя из форварда"

    @Autowired
    lateinit var userRepository: UserRepository

    override fun forwardWork(msg: Message) {
        val strBuild = StringBuilder()

        if (!checkUserExists(msg)) {
            val user = User()
            user.telegramId = msg.forwardFrom.id
            user.telegramHandle = msg.forwardFrom.userName
            user.isBot = msg.forwardFrom.isBot
            user.firstName = msg.forwardFrom.firstName
            user.lastName = msg.forwardFrom.lastName

            userRepository.save(user)
            strBuild.appendLine("Пользователь создан")
        } else {
            strBuild.appendLine("\nПользователь уже существует")
        }

        rsSH.sendSimpleNotification(msg.chatId, strBuild.toString())
    }

    fun checkUserExists(msg: Message): Boolean {
        return userRepository.countByTelegramId(msg.forwardFrom.id) > 0
    }

}