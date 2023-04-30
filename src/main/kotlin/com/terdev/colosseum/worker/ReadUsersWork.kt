package com.terdev.colosseum.worker

import com.terdev.colosseum.Admin
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.jpa.dao.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class ReadUsersWork : CommandWork() {

    override var command = "getUsers"
    override var commandDesc = "Дать список пользователей"

    @Autowired
    lateinit var userRepository: UserRepository

    override fun commandWork(msg: Message) {
        val usersList = userRepository.findAll()
        val output = StringBuilder()
        if (userHasAdminRights(msg)) {
            usersList.forEach {
                output.appendLine(
                        "\n${it.id} | " +
                                "${it.telegramId} | " +
                                "${it.telegramHandle} | " +
                                "${it.isBot} | " +
                                "${it.firstName} | " +
                                "${it.lastName} | " +
                                "${it.dateCreated}"
                )
            }
        }
        else {
            output.appendLine("Недостаточно прав для вызова команды")
        }
        rsSH.sendSimpleNotification(msg.chatId, output.toString())
    }
}