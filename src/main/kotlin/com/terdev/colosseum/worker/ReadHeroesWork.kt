package com.terdev.colosseum.worker

import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.jpa.dao.HeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class ReadHeroesWork : CommandWork() {

    override var command = "getHeroes"
    override var commandDesc = "Дать список всех героев"

    @Autowired
    lateinit var heroRepository: HeroRepository

    override fun commandWork(msg: Message) {
        val heroesList = heroRepository.findAll()
        val output = StringBuilder()
        if (heroesList.isNotEmpty()) {
            heroesList.forEach {
                output.appendLine(
                        "\n${it.id} | " +
                                "${it.userLink!!.id} | " +
                                "${it.isAlive} | " +
                                "${it.name} | " +
                                "${it.classType} | " +
                                "${it.level} | " +
                                "${it.strength} | " +
                                "${it.dexterity} | " +
                                "${it.intelligence} | " +
                                "${it.luck}"
                )
            }
        } else {
            output.appendLine("Герои не найдены")
        }
        rsSH.sendSimpleNotification(msg.chatId, output.toString())
    }
}