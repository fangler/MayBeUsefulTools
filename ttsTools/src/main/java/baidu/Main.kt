package baidu

import common.Log
import java.time.LocalDate

fun main() {

    Log.d("main", "start baidu tts...")

    val tts = "但是，李白一生几乎没担任什么官职。他没有参加科举考试，只有在唐玄宗天宝初年（公元742年）经别人引荐当了三年的翰林待诏。说是三年，其实满打满算就一年多。翰林待诏是什么职位呢?其实就是一个陪皇帝玩乐的御用文人，还不参与政治事务。除此之外，他就没有在官场担任什么职务。"
    val now = LocalDate.now()
    val fileName = "${tts.subSequence(0, 4)}-${now.year}-${now.month}-${now.dayOfMonth}"

    Token.refresh()
    Token.getToken()?.let {
        TtsOnline.dotts(tts, it, fileName)
        Log.d("main", "end baidu tts success...")
    } ?: Log.d("main", "end baidu tts, token is empty...")

}