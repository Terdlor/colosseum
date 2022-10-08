package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.stateMachine.Events
import com.example.terdlor_first_bot.stateMachine.States
import com.example.terdlor_first_bot.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateMachine
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity


@Component
class StateTestWork(tgbParam : TelegramLongPollingBot) {

    private var rsh : SinglResponseHelper

    @Autowired
    lateinit var stateMachine: StateMachine<States, Events>

    init {
        rsh = SinglResponseHelper(tgbParam)
    }

    fun work(msg : Message, msgBd : com.example.terdlor_first_bot.bd.model.Message) : Boolean {
        try {
            if (msg.entities == null) return false

            var entity : MessageEntity? =
                    msg.entities.stream().filter{ en -> en.type.equals("bot_command") && en.text.equals("/start_st") }.findAny().orElse(null)
            if (entity != null) {
                stateMachine?.start();
                return true
            }
            entity =
                    msg.entities.stream().filter{ en -> en.type.equals("bot_command") && en.text.equals("/get_st") }.findAny().orElse(null)
            if (entity != null) {
                rsh.sendSimpleNotification(msg.chat.id, stateMachine?.getState().toString(), msg.messageId)
                return true
            }
            entity =
                    msg.entities.stream().filter{ en -> en.type.equals("bot_command") && en.text.equals("/next_st") }.findAny().orElse(null)
            if (entity != null) {
                stateMachine?.sendEvent(Events.E1);
                return true
            }
            return false
        } catch (ex : Exception) {
            val str =Печататель().дайException(ex)
            println(str)
            LogHelper().saveLog(str, "ОШИБКА-" + DatabaseHelper.getUserDao().findById(msg.from.id)?.userName!!)
            rsh.sendSimpleNotification(msg.chat.id, str, msg.messageId)
            return false
        }
    }
}