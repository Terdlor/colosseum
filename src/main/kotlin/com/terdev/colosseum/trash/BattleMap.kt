package com.terdev.colosseum.trash

import com.terdev.colosseum.bd.system.model.User
import com.terdev.colosseum.common.TextException
import org.springframework.stereotype.Component

@Component
class BattleMap : HashMap<Long, Battle>() {


    //StateMachine<States, Events>
    private var calList = ArrayList<User>()
    private var answList = ArrayList<User>()

    fun getBattle(user1: User) = get(user1.id) ?: throw TextException("${user1.userName} никого не вызывали")

    fun getBattle(user1: User, user2: User): Battle {
        var battle = get(user1.id)
        return if (battle == null) {
            battle = Battle(user1, user2)
            put(user1.id, battle)
            calList.add(user1)
            answList.add(user2)
            battle
        } else {
            battle
        }
    }

    fun getBattleNoCreate(user1: User, user2: User) =
        get(user1.id) ?: throw TextException("${user1.userName} не вызывал ${user2.userName}")

    fun remove(battle: Battle): Battle? {
        answList.remove(battle.user2)
        calList.remove(battle.user1)
        return super.remove(battle.user1.id)
    }

    fun checkExist(id: User): Int {
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
        this.forEach { strBuild.appendLine("${it.key} - ${it.value}") }
        return strBuild.toString()
    }
}