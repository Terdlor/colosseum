package com.example.terdlor_first_bot.rest

import com.example.terdlor_first_bot.stateMachine.Events
import com.example.terdlor_first_bot.stateMachine.States
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StateRestController @Autowired constructor(
        private val stateMachineFactory : StateMachineFactory<States, Events>,
        private val context : ApplicationContext
) {
    val statMap : HashMap<Int, StateMachine<States, Events>> = HashMap()

    @GetMapping("stateWork/{id}")
    fun stateWork(@PathVariable("id") id : Int) : String = work(id)

    fun getStateMachineFromId(id : Int) : StateMachine<States, Events> {
        return if (statMap.get(id) == null) {
            val stateMachine = stateMachineFactory.getStateMachine(id.toString())
            statMap.put(id, stateMachine)
            stateMachine
        } else {
            statMap.get(id)!!
        }
    }

    fun work(id : Int) : String {
        val stateMachine = getStateMachineFromId(id)
        val state = stateMachine.state.id

        var count =
                if (stateMachine.extendedState.variables.get("COUNT") == null) {
                    0
                } else {
                    stateMachine.extendedState.variables.get("COUNT")
                }
        if (count is Int) {
            count++
            stateMachine.extendedState.variables.put("COUNT", count)
            if (count > 2 && States.STATE1.equals(state)) {
                stateMachine.sendEvent(Events.EVENT1)
            }
        }
        return count.toString() + "\n" + stateMachine.state.toString()
    }
}