package com.terdev.colosseum.worker

import com.example.terdlor_first_bot.collosseum.BattleCycle
import com.terdev.colosseum.bd.system.model.Message
import com.terdev.colosseum.common.CommandWork
import org.springframework.stereotype.Component

@Component
class BattleWork : CommandWork() {
    override var command = "fight"
    override var commandDesc = "Стартует бой"

    override fun commandWork(msgBd: Message) {
        // Выводим ответ в чат телеги
        sendReplyNotification(msgBd.chat, BattleCycle().runBattle(), msgBd.messageId)
    }
}