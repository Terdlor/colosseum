package com.terdev.colosseum.trash

import com.terdev.colosseum.bd.system.model.User

class Battle(var user1: User, var user2: User) {

    override fun toString(): String {
        val strBuild = StringBuilder()
        strBuild.appendLine("user1 ${user1.userName}, user2 ${user2.userName}")
        return strBuild.toString()
    }
}