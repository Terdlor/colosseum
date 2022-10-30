package com.example.terdlor_first_bot.collosseum

import com.example.terdlor_first_bot.bd.chat.model.User
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import org.springframework.statemachine.StateMachine

class Battle(var user1 : User, var user2 : User, var stateMachine : StateMachine<States, Events>) {

    override fun toString(): String {
        val strBuild = StringBuilder()
        strBuild.appendLine("user1 ${user1.userName}, user2 ${user2.userName}, stateMachine ${stateMachine.state.id}")
        return strBuild.toString()
    }
}