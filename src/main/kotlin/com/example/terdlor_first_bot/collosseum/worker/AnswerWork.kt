package com.example.terdlor_first_bot.collosseum.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.collosseum.BattleMap
import com.example.terdlor_first_bot.collosseum.Battle
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import com.example.terdlor_first_bot.utils.EditValueHelper
import common.CommandWork
import common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import kotlin.random.Random

@Component("answerWork")
class AnswerWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "nu_go_battle"

    override fun commandWork(msg: Message, msgBd: com.example.terdlor_first_bot.bd.chat.model.Message) {

        val userCall = EditValueHelper().nickDef(getParam(msg))
        if (userCall.isBlank()) {
            throw TextException("непонятно от кого принимаешь")
        }
        val user2 = DatabaseHelper.getUserDao().findById(msgBd.from) ?: throw TextException("ОТВЕЧАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val user1 = DatabaseHelper.getUserDao().findByName(userCall) ?: throw TextException("$userCall непонятно кто")

        val stateMachine = stateMap.getSMCnoCreate(user1, user2)

        if (stateMachine.state.id == States.CALLING) {
            val bat = stateMachine.extendedState.variables.get("BATTLE")
            if (bat is Battle) {
                if (bat.user1.id == user1.id && bat.user2.id == user2.id) {
                    stateMachine.sendEvent(Events.ANSWER)
                    sendNotification(msgBd.chat, "${user2.userName} ответил ${user1.userName}")

                    if (Random.Default.nextBoolean()) {
                        sendNotification(msgBd.chat, "${bat.user2.userName} win ${bat.user1.userName}")
                    } else {
                        sendNotification(msgBd.chat, "${bat.user2.userName} lose ${bat.user1.userName}")
                    }
                    stateMachine.sendEvent(Events.BAT)
                    stateMap.remove(bat)
                } else {
                    throw TextException("не тоt состaв ${bat.user1.userName} ${bat.user2.userName}")
                }
            }
        } else {
            throw TextException("неправильное состояние")
        }
    }
}