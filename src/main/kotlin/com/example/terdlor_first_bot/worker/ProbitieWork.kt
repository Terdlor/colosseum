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
        // Генерируем случайные значения
        val hasProbitie = Random().nextBoolean()
        val hasPodzhog = Random().nextBoolean()
        val podzhogStr: String
        val probitieEmoji: String
        val podzhogEmoji = String(Character.toChars(128293))
        val randomCockSize = Random().nextInt(50)

        // Flavor-текст для 50 см
        val dickNamesList = arrayListOf<String>("хуищем", "дрыном", "титанюгой", "гигантом", "йормунгандром")
        val randomDickNameInt = Random().nextInt(dickNamesList.size-1)
        val dickName = dickNamesList.get(randomDickNameInt)

        // Flavor-текст для 0 см
        val noDickNameList = arrayListOf<String>(
                "У тебя вместо ганфингера - пусси-пистол!",
                "Ты не пробъешь!",
                "Наш детектор не распознал у вас наличие COCK",
                "Вы просыпаетесь на кухне с сильным желанием приготовить что-то вкусное"
        )
        val randomNoDickNameInt = Random().nextInt(noDickNameList.size-1)
        val noDickName = noDickNameList.get(randomNoDickNameInt)

        // Flavor-текст для поджога
        if (hasPodzhog) {
            podzhogStr = "С поджогом! $podzhogEmoji"
        }
        else {
            podzhogStr = ""
        }

        // Меняем эмодзи для пробития
        if (hasProbitie) {
            probitieEmoji = String(Character.toChars(128547))
        }
        else {
            probitieEmoji = String(Character.toChars(128522))
        }

        // Запускаем сборщик строки
        val strBuild = StringBuilder()
        when (randomCockSize) {
            50 -> strBuild.appendLine("Ты пробиваешь всех в этом чате своим $randomCockSize-сантиметровым $dickName \n$podzhogStr")
            0 -> strBuild.appendLine("Твой размер $randomCockSize см \n$noDickName")
            in 45..49 ->
                if (hasProbitie) {
                    strBuild.appendLine("Есть пробитие сзади $probitieEmoji \nТебя критануло аж на $randomCockSize см \n$podzhogStr")
                }
                else {
                    strBuild.appendLine("Нет пробития $probitieEmoji \nТвоя биба $randomCockSize см! Это палка, которую не перегнуть..")
                }
            in 37..44 ->
                if (hasProbitie) {
                    strBuild.appendLine("Есть пробитие сзади $probitieEmoji \nТвою шахту развалили на $randomCockSize см вглубь \n$podzhogStr")
                }
                else {
                    strBuild.appendLine("Нет пробития $probitieEmoji \nРасчехлил змея на $randomCockSize см! Это уже слишком..")
                }
            in 23..36 ->
                if (hasProbitie) {
                    strBuild.appendLine("Есть пробитие сзади $probitieEmoji \nГлубина вхождения: $randomCockSize см \n$podzhogStr")
                }
                else {
                    strBuild.appendLine("Нет пробития $probitieEmoji \nТы достаешь из широких штанин свои $randomCockSize см гражданства")
                }
            in 7..11 ->
                if (hasProbitie) {
                    strBuild.appendLine("Есть пробитие сзади $probitieEmoji \nКто-то попытался припехнуть свои $randomCockSize см \n$podzhogStr")
                }
                else {
                    strBuild.appendLine("Нет пробития $probitieEmoji \nТвой пиструн всего лишь $randomCockSize см")
                }
            in 1..6 ->
                if (hasProbitie) {
                    strBuild.appendLine("Есть пробитие сзади $probitieEmoji \nВошло всего лишь на $randomCockSize см. Это примерно как палец \n$podzhogStr")
                }
                else {
                    strBuild.appendLine("Нет пробития $probitieEmoji \nА с таким стручком в $randomCockSize см может и не будет")
                }
            else ->
                if (hasProbitie) {
                    strBuild.appendLine("Есть пробитие сзади $probitieEmoji \nВошло уверенно на $randomCockSize см \n$podzhogStr")
                }
                else {
                    strBuild.appendLine("Нет пробития $probitieEmoji \nРазмер твоего достоинства $randomCockSize см. Поздравляю, это средний результат!")
                }
        }

        // Выводим ответ в консоль
        println(msgBd.text + " отправил " + DatabaseHelper.getUserDao().findById(msgBd.from)?.userName + ", чат " + msgBd.chat + " в " + Date())
        println(strBuild.toString())

        // Выводим ответ в чат телеги
        sendNotification(msgBd.chat, strBuild.toString())

        // Логируем
        msgBd.rs = strBuild.toString()
        msgBd.rs_chat_id = msgBd.chat.toString()
        DatabaseHelper.getMessageDao().update(msgBd)
    }
}