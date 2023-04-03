package com.terdev.colosseum.trash

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@EnableScheduling
@Service
class ScheduledBatTasks @Autowired constructor(
    private val stateMap: BattleMap
) {

    @Scheduled(fixedRate = 5000)
    fun reportCurrentTime() {
        val strBuild = StringBuilder()
        stateMap.forEach { strBuild.appendLine(it.toString()) }
        com.terdev.colosseum.utils.println(stateMap.toString())
    }
}