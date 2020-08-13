import java.text.SimpleDateFormat
import java.util.*

object Log {
    fun d(tag: String, msg: String) {
        println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())}--[${Thread.currentThread().name}][$tag]: $msg")
    }
}