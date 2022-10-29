package com.example.terdlor_first_bot.collosseum.stateMachine

import com.example.terdlor_first_bot.rest.BatMap
import com.example.terdlor_first_bot.rest.ColController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateContext
import org.springframework.stereotype.Component
import org.springframework.statemachine.guard.Guard

@Component("guardCall")
class GuardCall @Autowired constructor(
        private val stateMap: BatMap
)  : Guard<States, Events> {
    override fun evaluate(p0: StateContext<States, Events>): Boolean {

        val bat = p0.extendedState.variables.get("COUNT")
        if (bat is ColController.Bat) {
            if (stateMap.checkExist(bat.us1) < 0) return false
            if (stateMap.checkExist(bat.us2) < 0) return false
            if (stateMap.checkExist(bat.us1) > 0) return false
            if (stateMap.checkExist(bat.us2) > 0) return false
        }
        return true
    }
}