package com.example.terdlor_first_bot.spring

import com.example.terdlor_first_bot.common.ProbitieMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.text.SimpleDateFormat
import java.util.Date

@Configuration
@EnableScheduling
class ScheduledTasks {

    @Autowired
    private lateinit var probitieMap: ProbitieMap

    private val dateFormat = SimpleDateFormat("HH:mm:ss")
    private var countMinutProbitie = 15

    @Scheduled(fixedRate = 5000)
    fun currentTime() {
        com.example.terdlor_first_bot.utils.println("Current time " + dateFormat.format(Date()))
    }

    @Scheduled(fixedRate = 5000)
    fun removeProbitie() {
        probitieMap.forEach{
            if ((Date().time - it.value.insert.time) > 1000*60*countMinutProbitie) {
                probitieMap.remove(it.key)
            }
        }
    }
}