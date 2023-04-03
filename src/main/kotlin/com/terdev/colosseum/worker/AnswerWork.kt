package com.terdev.colosseum.worker

import com.terdev.colosseum.bd.DatabaseHelper
import com.terdev.colosseum.bd.system.model.Message
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.TextException
import com.terdev.colosseum.trash.BattleMap
import com.terdev.colosseum.utils.EditValueHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component("answerWork")
class AnswerWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "nu_go_battle"
    override var commandDesc = "Ответ на вызов(collosseum)"

    override fun commandWork(msgBd: Message) {

        val userCall = EditValueHelper().nick(getParam(msgBd.text))
        if (userCall.isBlank()) {
            throw TextException("непонятно от кого принимаешь")
        }
        val user2 = DatabaseHelper.getUserDao().findById(msgBd.from)
            ?: throw TextException("ОТВЕЧАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val user1 = DatabaseHelper.getUserDao().findByName(userCall) ?: throw TextException("$userCall непонятно кто")

        val battle = stateMap.getBattleNoCreate(user1, user2)

        if (battle.user1.id == user1.id && battle.user2.id == user2.id) {
            sendNotification(msgBd.chat, "${user2.userName} ответил ${user1.userName}")

            if (Random.Default.nextBoolean()) {
                sendNotification(msgBd.chat, "${battle.user2.userName} win ${battle.user1.userName}")
            } else {
                sendNotification(msgBd.chat, "${battle.user2.userName} lose ${battle.user1.userName}")
            }
            stateMap.remove(battle)
        } else {
            throw Exception("ОШИБКА, ожидался бой ${battle.user1.id} ${battle.user2.id}, сработала обработка на ${user1.id} ${user2.id}")
        }
    }
}