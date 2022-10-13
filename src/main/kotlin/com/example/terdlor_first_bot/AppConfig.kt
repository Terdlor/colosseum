package com.example.terdlor_first_bot

import com.example.terdlor_first_bot.utils.GroupResponseHelper
import com.example.terdlor_first_bot.utils.SinglResponseHelper
import com.example.terdlor_first_bot.worker.GroupMessageWork
import com.example.terdlor_first_bot.worker.LastSpamInfoWork
import com.example.terdlor_first_bot.worker.SinglMessageWork
import com.example.terdlor_first_bot.worker.SystemMessageWork
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
    fun getSinglLastSpamInfoWorkBean (
            @Autowired tgbParam : TelegramLongPollingBot,
            @Autowired srh : SinglResponseHelper
    ) : LastSpamInfoWork {
        return LastSpamInfoWork(tgbParam, srh)
    }

    @Bean
    fun getGroupLastSpamInfoWorkBean (
            @Autowired tgbParam : TelegramLongPollingBot,
            @Autowired grh : GroupResponseHelper
    ) : LastSpamInfoWork {
        return LastSpamInfoWork(tgbParam, grh)
    }
}