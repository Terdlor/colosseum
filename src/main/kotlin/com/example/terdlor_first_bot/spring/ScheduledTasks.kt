package com.example.terdlor_first_bot.spring

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.text.SimpleDateFormat
import java.util.Date

@Configuration
@EnableScheduling
class ScheduledTasks {

    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(fixedRate = 5000)
    fun currentTime() {
        com.example.terdlor_first_bot.utils.println("Current time " + dateFormat.format(Date()))
    }
}