package com.terdev.colosseum.worker

import com.terdev.colosseum.bd.DatabaseHelper
import com.terdev.colosseum.bd.system.model.Message
import com.terdev.colosseum.common.CommandWork
import com.terdev.colosseum.common.TextException
import com.terdev.colosseum.trash.BattleMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("cancelWork")
class CancelWork : CommandWork() {

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "cancel_my_call_battle"
    override var commandDesc = "Сброс вызова(collosseum)"

    override fun commandWork(msgBd: Message) {
        val user1 = DatabaseHelper.getUserDao().findById(msgBd.from)
            ?: throw TextException("ВЫЗЫВАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val battle = stateMap.getBattle(user1)
        stateMap.remove(battle)
        sendNotification(msgBd.chat, "было отменено ${battle.user1.userName} vs ${battle.user2.userName}")
    }
}