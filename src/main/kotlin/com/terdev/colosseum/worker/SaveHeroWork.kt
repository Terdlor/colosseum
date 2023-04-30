package com.terdev.colosseum.worker

import com.terdev.colosseum.HeroCreator
import com.terdev.colosseum.UserCreator
import com.terdev.colosseum.common.CommandWork
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class SaveHeroWork : CommandWork() {

    override var command = "hero"
    override var commandDesc = "Соранить героя"

    @Autowired
    lateinit var heroCreator: HeroCreator
    
    @Autowired
    lateinit var userCreator: UserCreator

    override fun commandWork(msg: Message) {
        val output = StringBuilder()
        output.appendLine(heroCreator.createHero(msg.from.userName))

        val user = userCreator.getUser(msg.from.userName)
        if (user != null) {
            val hero = heroCreator.getHero(user)
            if (hero != null) {
                output.appendLine(
                        "${hero.id} | " +
                                "${hero.userLink!!.id} | " +
                                "${hero.isAlive} | " +
                                "${hero.name} | " +
                                "${hero.classType} | " +
                                "${hero.level} | " +
                                "${hero.strength} | " +
                                "${hero.dexterity} | " +
                                "${hero.intelligence} | " +
                                "${hero.luck}"
                )
            }
            else {
                output.appendLine("SaveHeroWork: Ошибка! Герой не найден")
            }
        }
        else {
            output.appendLine("SaveHeroWork: Ошибка! Пользователь не найден")
        }
        rsSH.sendSimpleNotification(msg.chatId, output.toString())
    }
}