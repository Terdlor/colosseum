package com.example.terdlor_first_bot.rest

import com.example.terdlor_first_bot.utils.SinglResponseHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MsgTransferRestController @Autowired constructor(
        private val helper : SinglResponseHelper
) {

    @PostMapping("save/{id}")
    fun transferMsg(@PathVariable("id") id : Long, @RequestBody body : String) : String = work(id, body)

    fun work(id : Long, body : String) : String {
        helper.sendSimpleNotification(id, body, 0)
        return "отправлено в $id"
    }
}