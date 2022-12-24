package com.example.terdlor_first_bot.worker

import com.example.terdlor_first_bot.bd.DatabaseHelper
import com.example.terdlor_first_bot.utils.*
import com.example.terdlor_first_bot.common.CommandWork
import org.springframework.stereotype.Component
import java.util.*

@Component("probitieWork")
class ProbitieWork : CommandWork() {

    override var command = "probitie"

    override fun commandWork(msgBd : com.example.terdlor_first_bot.bd.chat.model.Message) {
        val hasProbitie = Random().nextBoolean()
        val randomCockSize = Random().nextInt(50)
        val hasPodzhog = Random().nextBoolean()
        var podzhogStr = ""

        if (hasPodzhog) {
            podzhogStr = "С поджогом!"
        }
        else {
            podzhogStr = ""
        }

        val strBuild = StringBuilder()

        when (randomCockSize) {
            in 45..50 -> if (hasProbitie) {
                strBuild.appendLine("Есть пробитие сзади. Тебя критануло аж на $randomCockSize см \n$podzhogStr")
            } else {
                strBuild.appendLine("Нет пробития. Твоя биба $randomCockSize см! :)")
            }
            in 0..6 -> if (hasProbitie) {
                strBuild.appendLine("Есть пробитие сзади. Вошло всего на $randomCockSize см \n$podzhogStr")
            } else {
                strBuild.appendLine("Нет пробития. Твой пиструн $randomCockSize см :(")
            }
            else -> if (hasProbitie) {
                strBuild.appendLine("Есть пробитие сзади. Вошло на $randomCockSize см \n$podzhogStr")
            } else {
                strBuild.appendLine("Нет пробития. Твой COCK $randomCockSize см")
            }
        }

        println(msgBd.text + " отправил " + DatabaseHelper.getUserDao().findById(msgBd.from)?.userName + ", чат " + msgBd.chat + " в " + Date())
        println(strBuild.toString())

        sendNotification(msgBd.chat, strBuild.toString())

        msgBd.rs = strBuild.toString()
        msgBd.rs_chat_id = msgBd.chat.toString()
        DatabaseHelper.getMessageDao().update(msgBd)
    }
}