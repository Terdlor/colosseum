package com.terdev.colosseum.worker

import com.terdev.colosseum.common.ForwardWork
import com.terdev.colosseum.jpa.dao.UserRepository
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class SaveForwardedUserWork : ForwardWork() {

    override var command = ""
    override var commandDesc = "Сохранение пользователя из форварда"

    @Autowired
    lateinit var userRepository: UserRepository

    override fun forwardWork(msg: Message) {
        val output = StringBuilder()

        if (!checkUserExists(msg)) {
            val user = User()
            user.telegramId = msg.forwardFrom.id
            user.telegramHandle = msg.forwardFrom.userName
            user.isBot = msg.forwardFrom.isBot
            user.firstName = msg.forwardFrom.firstName
            user.lastName = msg.forwardFrom.lastName
            userRepository.save(user)
            output.appendLine("Пользователь создан")
        } else {
            output.appendLine("Пользователь уже существует")
        }

        rsSH.sendSimpleNotification(msg.chatId, output.toString())
    }

    fun checkUserExists(msg: Message): Boolean {
        return userRepository.countByTelegramId(msg.forwardFrom.id) > 0
    }

}