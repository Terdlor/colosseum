package com.terdev.colosseum.common

import com.terdev.colosseum.Admin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.telegram.telegrambots.meta.api.objects.Message


abstract class ForwardWork : Work() {

    @Autowired
    private lateinit var env: Environment

    abstract fun forwardWork(msg: Message)

    override fun checkWork(msg: Message): Boolean {
        if (msg.forwardFrom != null && userHasAdminRights(msg)) {
            forwardWork(msg)
            return true
        }
        return false
    }
}