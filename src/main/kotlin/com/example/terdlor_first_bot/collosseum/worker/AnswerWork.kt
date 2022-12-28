package com.example.terdlor_first_bot.collosseum.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.collosseum.BattleMap
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import com.example.terdlor_first_bot.utils.EditValueHelper
import com.example.terdlor_first_bot.common.CommandWork
import com.example.terdlor_first_bot.common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component("answerWork")
class AnswerWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "nu_go_battle"
    override var commandDesc = "Ответ на вызов(collosseum)"

    override fun commandWork(msgBd: com.example.terdlor_first_bot.bd.chat.model.Message) {

        val userCall = EditValueHelper().nick(getParam(msgBd.text))
        if (userCall.isBlank()) {
            throw TextException("непонятно от кого принимаешь")
        }
        val user2 = DatabaseHelper.getUserDao().findById(msgBd.from) ?: throw TextException("ОТВЕЧАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val user1 = DatabaseHelper.getUserDao().findByName(userCall) ?: throw TextException("$userCall непонятно кто")

        val battle = stateMap.getBattleNoCreate(user1, user2)

        if (battle.stateMachine.state.id == States.CALLING) {
            if (battle.user1.id == user1.id && battle.user2.id == user2.id) {
                battle.stateMachine.sendEvent(Events.ANSWER)
                sendNotification(msgBd.chat, "${user2.userName} ответил ${user1.userName}")

                if (Random.Default.nextBoolean()) {
                    sendNotification(msgBd.chat, "${battle.user2.userName} win ${battle.user1.userName}")
                } else {
                    sendNotification(msgBd.chat, "${battle.user2.userName} lose ${battle.user1.userName}")
                }
                battle.stateMachine.sendEvent(Events.BAT)
                stateMap.remove(battle)
            } else {
                throw Exception("ОШИБКА, ожидался бой ${battle.user1.id} ${battle.user2.id}, сработала обработка на ${user1.id} ${user2.id}")
            }
        } else {
            throw TextException("бой ${battle.user1.userName} и ${battle.user2.userName} находится в состоянии ${battle.stateMachine.id}")
        }
    }
}