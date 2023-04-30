package com.terdev.colosseum.worker

import com.terdev.colosseum.UserCreator
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.jpa.dao.UserRepository
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class SaveUserWork : CommandWork() {

    override var command = "user"
    override var commandDesc = "Сохранить пользователя"

    @Autowired
    lateinit var userCreator: UserCreator

    override fun commandWork(msg: Message) {
        val output = StringBuilder()
        output.appendLine(userCreator.createUser(msg))
        
        val user = userCreator.getUser(msg.from.userName)
        if (user != null) {
            output.appendLine(
                    "${user.id} | " +
                            "${user.telegramId} | " +
                            "${user.telegramHandle} | " +
                            "${user.isBot} | " +
                            "${user.firstName} | " +
                            "${user.lastName} | " +
                            "${user.dateCreated}"
            )
        }
        else {
            output.appendLine("SaveUserWork: Ошибка! Пользователь не найден")
        }
        rsSH.sendSimpleNotification(msg.chatId, output.toString())
    }


}