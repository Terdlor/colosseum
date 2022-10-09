package com.example.terdlor_first_bot.rest

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MsgTransferRestController {

    @PostMapping("save/{id}")
    fun transferMsg(@PathVariable("id") id : Long, @RequestBody body : String) : String = work(id, body)

    fun work(id : Long, body : String) : String {
        return "$id"
    }
}