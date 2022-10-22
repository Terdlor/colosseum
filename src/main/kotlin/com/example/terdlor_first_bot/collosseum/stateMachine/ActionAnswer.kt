package com.example.terdlor_first_bot.collosseum.stateMachine

import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action
import org.springframework.stereotype.Component

@Component("actionAnswer")
class ActionAnswer  : Action<States, Events> {
    override fun execute(p0: StateContext<States, Events>) {
        p0.extendedState?.variables?.forEach {
            System.out.println("${it.key} - ${it.value}")
        }

        System.out.println("move from " + p0.stateMachine.state.id + ", event " + p0.message.payload)
    }
}