package tencent

import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.cvm.v20170312.CvmClient
import com.tencentcloudapi.tts.v20190823.TtsClient
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceRequest
import common.Log

//TODO tencent的sdk实在是烂，完全没法直接用，而且这个腾讯云的sdk全封装在一起，乱七八糟的，各种字母缩写，无力吐槽啊
fun main() {
    Log.d("main", "start tencent tts...")

    val tts = "李白可真白啊"

    /**
     * TODO 需要在 [Token] 内填写有效的token信息
     */
    Tts.doTts(tts)


    Log.d("main", "end tencent tts...")
}