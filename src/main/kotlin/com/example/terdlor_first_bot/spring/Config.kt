package com.example.terdlor_first_bot.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
}

class Date @Autowired constructor(val writer : Writer) 