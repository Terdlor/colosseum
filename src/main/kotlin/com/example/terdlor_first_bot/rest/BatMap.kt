package com.example.terdlor_first_bot.rest

import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.stereotype.Component

@Component
class BatMap @Autowired constructor(
        val stateMachineFactory: StateMachineFactory<States, Events>
) : HashMap<Int, StateMachine<States, Events>>() {

    private var calList = ArrayList<Int>()
    private var answList = ArrayList<Int>()

    fun getSMC(id : Int, id2 : Int) : StateMachine<States, Events> {
        val stateMachine = get(id)
        return if (stateMachine == null) {
            val st = stateMachineFactory.getStateMachine(id.toString())
            put(id, st)
            st.extendedState.variables["COUNT"] = ColController.Bat(id, id2)
            calList.add(id)
            answList.add(id2)
            st
        } else {
            get(id)!!
        }
    }

    override fun remove(key: Int): StateMachine<States, Events>? {
        val bat = getValue(key).extendedState.variables.get("COUNT")
        if (bat is  ColController.Bat) {
            answList.remove(bat.us2)
            calList.remove(bat.us1)
        }
        return super.remove(key)
    }

    fun checkExist(id : Int) : Int {
        if (calList.contains(id)) {
            return -1
        }
        if (answList.contains(id)) {
            return 1
        }

        return 0
    }
}