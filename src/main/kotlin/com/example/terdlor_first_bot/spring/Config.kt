package com.example.terdlor_first_bot.spring

import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Configuration
class Config {

    @Bean
    fun getWriter() : Writer {
        return WriterImpl()
    }

    @Bean
    fun gerDate() : Date {
        return Date(getWriter())
    }

    @Bean
    fun getSinglResponseHelperBean(@Autowired tgbParam : TelegramLongPollingBot) : SinglResponseHelper {
        return SinglResponseHelper(tgbParam)
    }

    @Bean
    fun getGroupResponseHelperBean (@Autowired tgbParam : TelegramLongPollingBot) : GroupResponseHelper {
        return GroupResponseHelper(tgbParam)
    }
}

class Date @Autowired constructor(val writer : Writer)