package com.terdev.colosseum.worker

import com.terdev.colosseum.*
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.utils.EditValueHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class BattleWork : CommandWork() {

    override var command = "fight"
    override var commandDesc = "Вступить в бой"

    @Autowired
    lateinit var userCreator: UserCreator

    @Autowired
    lateinit var heroCreator: HeroCreator

    override fun commandWork(msg: Message) {
        val output = StringBuilder()

        // Определяем никнеймы игроков
        val callerTelegramHandle = msg.from.userName
        val responderTelegramHandle = EditValueHelper().nick(getParam(msg.text))
        output.appendLine("caller = $callerTelegramHandle")
        output.appendLine("responder = $responderTelegramHandle")
        rsSH.sendSimpleNotification(msg.chatId, output.toString())
        output.clear()

        // Получаем игроков из базы
        val caller = userCreator.getUser(callerTelegramHandle)
        val responder = userCreator.getUser(responderTelegramHandle)

        // Проводим бой между героями
        if (caller != null && responder != null) {
            // Создаем героев для игроков
            output.appendLine(heroCreator.createHero(callerTelegramHandle))
            output.appendLine(heroCreator.createHero(responderTelegramHandle))
            rsSH.sendSimpleNotification(msg.chatId, output.toString())
            output.clear()

            // Получаем героев из базы
            val callerHeroEntity = heroCreator.getHero(caller)
            val responderHeroEntity = heroCreator.getHero(responder)

            // Маппим сущности БД на объекты героев
            val callerHero = Hero(HeroClassType.valueOf(callerHeroEntity!!.classType!!)).mapHeroFromDatabase(callerHeroEntity)
            val responderHero = Hero(HeroClassType.valueOf(responderHeroEntity!!.classType!!)).mapHeroFromDatabase(responderHeroEntity)

            // Формируем лог битвы
            output.appendLine(BattleCycle().runBattle(callerHero, responderHero))
        }
        else {
            output.appendLine("Один или несколько пользователей не были найдены")
        }

        rsSH.sendSimpleNotification(msg.chatId, output.toString())
    }
}