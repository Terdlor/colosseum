package com.example.terdlor_first_bot.collosseum

import com.example.terdlor_first_bot.bd.chat.model.User
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.stereotype.Component

@Component
class BattleMap @Autowired constructor(
        val stateMachineFactory: StateMachineFactory<States, Events>
) : HashMap<Long, StateMachine<States, Events>>() {

    private var calList = ArrayList<User>()
    private var answList = ArrayList<User>()

    fun getSMC(user1 : User, user2 : User) : StateMachine<States, Events> {
        val stateMachine = get(user1.id)
        return if (stateMachine == null) {
            val st = stateMachineFactory.getStateMachine(user1.id.toString())
            put(user1.id, st)
            st.extendedState.variables["BATTLE"] = Battle(user1, user2)
            calList.add(user1)
            answList.add(user2)
            st
        } else {
            get(user1.id)!!
        }
    }

    fun getSMCnoCreate(user1 : User, user2 : User) : StateMachine<States, Events> {
        val stateMachine = get(user1.id)
        return if (stateMachine == null) {
            throw TextException("${user1.userName} не вызывал ${user2.userName}")
        } else {
            get(user1.id)!!
        }
    }

    fun getSMCany(user1 : User, user2 : User) : StateMachine<States, Events> {
        var stateMachine = get(user1.id)
        if (stateMachine != null) {
            return stateMachine
        }
        stateMachine = get(user2.id)
        if (stateMachine != null) {
            return stateMachine
        }
        throw TextException("${user1.userName} и ${user2.userName} никого не вызывали")
    }

    fun remove(battle: Battle): StateMachine<States, Events>? {
        answList.remove(battle.user2)
        calList.remove(battle.user1)
        return super.remove(battle.user1.id)
    }

    fun checkExist(id : User) : Int {
        if (calList.contains(id)) {
            return -1
        }
        if (answList.contains(id)) {
            return 1
        }

        return 0
    }
}