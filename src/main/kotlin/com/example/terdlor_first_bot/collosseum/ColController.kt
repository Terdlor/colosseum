package com.example.terdlor_first_bot.collosseum

import com.example.terdlor_first_bot.collosseum.stateMachine.Events
import com.example.terdlor_first_bot.collosseum.stateMachine.States
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class ColController @Autowired constructor(
        private val stateMap: BatMap
)  {

    @GetMapping( "/reset/{id1}")
    fun path1(@PathVariable("id1") id1 : Int): String = reset(id1)
    @GetMapping( "/col1/{id1}/{id2}")
    fun path1(@PathVariable("id1") id1 : Int,
              @PathVariable("id2") id2 : Int): String = work1(id1, id2)
    @GetMapping( "/col2/{id2}/{id1}")
    fun path2(@PathVariable("id2") id2 : Int,
              @PathVariable("id1") id1 : Int): String = work2(id2, id1)

    fun reset(id1 : Int) : String {
        println("$id1 reset")
        return if (stateMap.get(id1) == null) {
            "nope $id1"
        } else {
            stateMap.remove(id1)
            "stop $id1"
        }
    }

    fun work1(id1 : Int, id2 : Int) : String {

        println("$id1 call $id2")
        if (stateMap.checkExist(id1) < 0) return "$id1 in calling"
        if (stateMap.checkExist(id2) < 0) return "$id2 in calling"
        if (stateMap.checkExist(id1) > 0) return "$id1 in answering"
        if (stateMap.checkExist(id2) > 0) return "$id2 in answering"

        val stateMachine = stateMap.getSMC(id1, id2)
        if (stateMachine.state.id == States.INIT) {
            stateMachine.sendEvent(Events.CALL)
            println("вызов $id1 vs $id2" )
            return "создано $id1 vs $id2"
        } else {
            val bat = stateMachine.extendedState.variables.get("COUNT")
            if (bat is Bat) {
                return "уже было создано ${bat.us1} vs ${bat.us2}"
            }
            return "ОШИБКА1"
        }
    }

    fun work2(id2 : Int, id1 : Int) : String {
        println("$id2 answ $id1")
        val stateMachine = stateMap.getSMC(id1, id2)
        if (stateMachine.state.id == States.CALLING) {
            val bat = stateMachine.extendedState.variables.get("COUNT")
            if (bat is Bat) {
                return if (bat.us1 == id1 && bat.us2 == id2) {
                    stateMachine.sendEvent(Events.ANSWER)
                    println("ответ $id1 vs $id2" )
                    bat(bat.us1, bat.us2)
                    stateMachine.sendEvent(Events.BAT)
                    reset(id1)
                    "completed ${bat.us1} vs ${bat.us2}"
                } else {
                    "не тоt состaw ${bat.us1} ${bat.us2}"
                }
            }
            return "state inwalid"
        } else {
            return "${stateMachine.state.id} это не то состояние"
        }
    }

    fun bat(id1 : Int, id2 : Int) {
        if (Random.Default.nextBoolean()) {
            println("$id2 win $id1")
        } else {
            println("$id2 lose $id1")
        }
        println("end $id2 vs $id1")
    }

    class Bat(var us1 : Int, var us2 : Int)
}