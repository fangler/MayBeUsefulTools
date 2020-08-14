package common

import java.time.LocalDate

object Log {
    fun d(tag: String, msg: String) {
        val now = LocalDate.now()
        println("${now.year}:${now.month}:${now.dayOfMonth}--[${Thread.currentThread().name}][$tag]: $msg")
    }
}