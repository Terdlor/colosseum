package com.example.terdlor_first_bot.collosseum

import com.example.terdlor_first_bot.bd.chat.model.User
import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import com.example.terdlor_first_bot.common.TextException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.stereotype.Component

@Component
class BattleMap @Autowired constructor(
        val stateMachineFactory: StateMachineFactory<States, Events>
) : HashMap<Long, Battle>() {


    //StateMachine<States, Events>
    private var calList = ArrayList<User>()
    private var answList = ArrayList<User>()

    fun getBattle(user1 : User) = get(user1.id) ?: throw TextException("${user1.userName} никого не вызывали")

    fun getBattle(user1 : User, user2 : User) : Battle {
        var battle = get(user1.id)
        return if (battle == null) {
            val st = stateMachineFactory.getStateMachine(user1.id.toString())
            battle = Battle(user1, user2, st)
            put(user1.id, battle)
            calList.add(user1)
            answList.add(user2)
            battle
        } else {
            battle
        }
    }

    fun getBattleNoCreate(user1 : User, user2 : User) = get(user1.id) ?: throw TextException("${user1.userName} не вызывал ${user2.userName}")

    fun remove(battle: Battle): Battle? {
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

    override fun toString(): String {
        val strBuild = StringBuilder()
        strBuild.appendLine("вызывающие ${calList.map { it.userName }}, принимающие ${answList.map { it.userName }}")
        this.forEach { strBuild.appendLine("${it.key} - ${it.value}")}
        return strBuild.toString()
    }
}