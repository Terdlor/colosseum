package com.terdev.colosseum.utils

import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

class Печататель {

    fun дайException(ex: Exception): String {
        val strBuild = StringBuilder()
        strBuild.appendLine("ОШИБКА")
        strBuild.appendLine(ex.toString())
        for (exst in ex.stackTrace) {
            strBuild.appendLine(exst.className + "." + exst.methodName + ":" + exst.lineNumber)
        }
        return strBuild.toString()
    }
}

//JEP 400: UTF-8 по умолчанию, Стандартизация UTF-8 во всех стандартных Java API, за исключением консольного ввода-вывода.
fun println(p: Any?) {
    PrintStream(FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8.toString()).println(p)
}