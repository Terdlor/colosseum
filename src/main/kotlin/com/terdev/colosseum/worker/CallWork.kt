package com.terdev.colosseum.worker

import com.terdev.colosseum.bd.DatabaseHelper
import com.terdev.colosseum.bd.system.model.Message
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.TextException
import com.terdev.colosseum.trash.BattleMap
import com.terdev.colosseum.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("callWork")
class CallWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "go_battle"
    override var commandDesc = "Вызов(collosseum)"

    override fun commandWork(msgBd: Message) {
        val userAnswer = EditValueHelper().nick(getParam(msgBd.text))
        if (userAnswer.isBlank()) {
            throw TextException("непонятно кого вызвал")
        }
        val user1 = DatabaseHelper.getUserDao().findById(msgBd.from)
            ?: throw TextException("ВЫЗЫВАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val user2 =
            DatabaseHelper.getUserDao().findByName(userAnswer) ?: throw TextException("$userAnswer непонятно кто")

        if (stateMap.checkExist(user1) < 0) throw TextException("ты уже кого-то вызвал")
        if (stateMap.checkExist(user1) > 0) throw TextException("${user2.userName} уже кого-то вызвал")
        if (stateMap.checkExist(user2) < 0) throw TextException("тебя уже кто-то вызвал")
        if (stateMap.checkExist(user2) > 0) throw TextException("${user2.userName} уже кто-то вызвал")

        val battle = stateMap.getBattle(user1, user2)
        val strBuild = StringBuilder()
        strBuild.appendLine("${user1.userName} вызвал ${user2.userName}")
        sendNotification(msgBd.chat, strBuild.toString())
    }
}