package com.example.terdlor_first_bot.collosseum.stateMachine

import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State

class Listener : StateMachineListenerAdapter<States, Events>() {
    override fun stateChanged(from: State<States, Events>?, to: State<States, Events>?) {
        super.stateChanged(from, to)
        com.example.terdlor_first_bot.utils.println("state changed $from to $to")
    }
}