package com.example.terdlor_first_bot.spring

class WriterImpl : Writer {

    override fun write(text: Int) {
        com.example.terdlor_first_bot.utils.println(text)
    }

    override fun write(text: String) {
        com.example.terdlor_first_bot.utils.println(text)
    }
}