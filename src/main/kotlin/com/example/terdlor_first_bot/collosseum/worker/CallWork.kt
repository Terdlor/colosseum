package com.example.terdlor_first_bot.collosseum.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.collosseum.BattleMap
import com.example.terdlor_first_bot.collosseum.Battle
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import com.example.terdlor_first_bot.utils.*
import common.CommandWork
import common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component("callWork")
class CallWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "go_battle"

    override fun commandWork(msg: Message, msgBd: com.example.terdlor_first_bot.bd.chat.model.Message) {
        val userAnswer = EditValueHelper().nickDef(getParam(msg))
        if (userAnswer.isBlank()) {
            throw TextException("непонятно кого вызвал")
        }
        val user1 = DatabaseHelper.getUserDao().findById(msgBd.from) ?: throw TextException("ВЫЗЫВАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val user2 = DatabaseHelper.getUserDao().findByName(userAnswer) ?: throw TextException("$userAnswer непонятно кто")

        if (stateMap.checkExist(user1) < 0) throw TextException("ты уже кого-то вызвал")
        if (stateMap.checkExist(user1) < 0) throw TextException("${user2.userName} уже кого-то вызвал")
        if (stateMap.checkExist(user2) > 0) throw TextException("тебя уже кто-то вызвал")
        if (stateMap.checkExist(user2) > 0) throw TextException("${user2.userName} уже кто-то вызвал")

        val stateMachine = stateMap.getSMC(user1, user2)
        if (stateMachine.state.id == States.INIT) {
            stateMachine.sendEvent(Events.CALL)
            val strBuild = StringBuilder()
            strBuild.appendLine("${user1.userName} вызвал ${user2.userName}")
            sendNotification(msgBd.chat, strBuild.toString())
        } else {
            val bat = stateMachine.extendedState.variables.get("BATTLE")
            if (bat is Battle) {
                sendNotification(msgBd.chat, "уже было создано ${bat.user1.userName} vs ${bat.user2.userName}")
            }
        }
    }
}