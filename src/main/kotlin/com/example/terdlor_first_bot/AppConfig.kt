package com.example.terdlor_first_bot

import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.LogHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Configuration
class AppConfig {

    @Bean
    fun servletWebServerFactory(): ServletWebServerFactory? {
        return TomcatServletWebServerFactory()
    }

    @Bean
    fun getSinglResponseHelperBean(@Autowired tgbParam : TelegramLongPollingBot) : SinglResponseHelper {
        return SinglResponseHelper(tgbParam)
    }

    @Bean
    fun getGroupResponseHelperBean (@Autowired tgbParam : TelegramLongPollingBot) : GroupResponseHelper {
        return GroupResponseHelper(tgbParam)
    }

    @Bean
    fun getLogHelper () : LogHelper {
        return LogHelper()
    }
}