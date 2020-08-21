package google

import java.util.*

fun main() {
    //FIXME 需要自行提供google accessToken实现方式
    val token = "ya29.c.Ko8B2QfGxqxNcKiDMdA3jBPy0DJoYxl62UXoQKwXyGOYP58cRQ7QBuiigFg8VBXbX6C4vQgTUSdBIElRj4g-sGK2LpI5ZzqijAilYzbLJAqCD20gPeZ5V556O7CieZn7A5ipiuEUM2OD_sEXiCdvW1f94wupXY4M7WFxSvRl2LN_Q3OV8HX2WisXhlNGmwLYm24"
    val expireData = Date(1597996735817)
    val tts = "哈哈，今天天气真差啊啊"

    GoogleTts.setUpClient(token, expireData)
    GoogleTts.tts(tts, "1.mp3")

    //如果出现io.grpc.StatusRuntimeException: UNAUTHENTICATED 异常，一般都是token过期或者有误-_-_-
}