package com.example.terdlor_first_bot.collosseum.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.collosseum.BattleMap
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import com.example.terdlor_first_bot.utils.*
import com.example.terdlor_first_bot.common.CommandWork
import com.example.terdlor_first_bot.common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("callWork")
class CallWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "go_battle"

    override fun commandWork(msgBd: com.example.terdlor_first_bot.bd.chat.model.Message) {
        val userAnswer = EditValueHelper().nick(getParam(msgBd.text))
        if (userAnswer.isBlank()) {
            throw TextException("непонятно кого вызвал")
        }
        val user1 = DatabaseHelper.getUserDao().findById(msgBd.from) ?: throw TextException("ВЫЗЫВАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val user2 = DatabaseHelper.getUserDao().findByName(userAnswer) ?: throw TextException("$userAnswer непонятно кто")

        if (stateMap.checkExist(user1) < 0) throw TextException("ты уже кого-то вызвал")
        if (stateMap.checkExist(user1) > 0) throw TextException("${user2.userName} уже кого-то вызвал")
        if (stateMap.checkExist(user2) < 0) throw TextException("тебя уже кто-то вызвал")
        if (stateMap.checkExist(user2) > 0) throw TextException("${user2.userName} уже кто-то вызвал")

        val battle = stateMap.getBattle(user1, user2)
        if (battle.stateMachine.state.id == States.INIT) {
            battle.stateMachine.sendEvent(Events.CALL)
            val strBuild = StringBuilder()
            strBuild.appendLine("${user1.userName} вызвал ${user2.userName}")
            sendNotification(msgBd.chat, strBuild.toString())
        } else {
            sendNotification(msgBd.chat, "уже было создано ${battle.user1.userName} vs ${battle.user2.userName}")
        }
    }
}