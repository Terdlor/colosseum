package com.example.terdlor_first_bot.collosseum.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.collosseum.BattleMap
import com.example.terdlor_first_bot.common.CommandWork
import com.example.terdlor_first_bot.common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("cancelWork")
class CancelWork : CommandWork(){

    @Autowired
    private lateinit var stateMap: BattleMap

    override var command = "cancel_my_call_battle"
    override var commandDesc = "Сброс вызова(collosseum)"

    override fun commandWork(msgBd: com.example.terdlor_first_bot.bd.chat.model.Message) {
        val user1 = DatabaseHelper.getUserDao().findById(msgBd.from) ?: throw TextException("ВЫЗЫВАЮЩИЙ ${msgBd.from} НЕ НАЙДЕН В БД!!!")
        val battle = stateMap.getBattle(user1)
        stateMap.remove(battle)
        sendNotification(msgBd.chat, "было отменено ${battle.user1.userName} vs ${battle.user2.userName}")
    }
}