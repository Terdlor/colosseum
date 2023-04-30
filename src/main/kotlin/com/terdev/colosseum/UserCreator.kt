package com.terdev.colosseum

import com.terdev.colosseum.jpa.dao.UserRepository
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class UserCreator {

    @Autowired
    lateinit var userRepository: UserRepository

    fun createUser(msg: Message): String {
        val output = StringBuilder()

        if (!checkUserExists(msg.from.userName)) {
            val user = User()
            user.telegramId = msg.from.id
            user.telegramHandle = msg.from.userName
            user.isBot = msg.from.isBot
            user.firstName = msg.from.firstName
            user.lastName = msg.from.lastName
            userRepository.save(user)
            output.appendLine("Пользователь создан")
        }
        else {
            output.appendLine("Пользователь уже существует")
        }

        return output.toString()
    }

    fun getUser(nickname: String): User? {
        if (checkUserExists(nickname)) {
            return userRepository.getByTelegramHandle(nickname)
        }
        else {
            return null
        }
    }

    fun checkUserExists(nickname: String): Boolean {
        return userRepository.countByTelegramHandle(nickname) > 0
    }
}