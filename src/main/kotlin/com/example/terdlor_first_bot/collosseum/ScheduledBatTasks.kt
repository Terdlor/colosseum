package com.example.terdlor_first_bot.collosseum

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@EnableScheduling
@Service
class ScheduledBatTasks @Autowired constructor(
        private val stateMap: BattleMap) {

    @Scheduled(fixedRate = 5000)
    fun reportCurrentTime() {
        println(stateMap.values)
    }
}