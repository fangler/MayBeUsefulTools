package tencent

import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.tts.v20190823.TtsClient
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceRequest
import okio.buffer
import okio.sink
import java.io.File
import java.time.LocalDate
import java.util.*

object Tts {
    fun doTts(ttsInput: String) {
        val cred = Credential(Token.SECRET_ID, Token.SECRET_KEY)
        //https://cloud.tencent.com/document/api/1073/37989#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8
        //fixme 第二个参数不知道有什么鸟用
        val ttsClient = TtsClient(cred, "ap-shenzhen-fsi")
        //这个接口封装的是真的烂，Builder模式都不用
        val textToVoiceRequest = TextToVoiceRequest().apply {
            // https://cloud.tencent.com/document/api/1073/37995
            text = ttsInput
            sessionId = System.currentTimeMillis().toString()
            modelType = 1
            // 音量大小，范围：[0，10]，分别对应11个等级的音量，默认为0，代表正常音量。没有静音选项。
            volume = 0f
            //语速，范围：[-2，2]，分别对应不同语速：
            //-2代表0.6倍
            //-1代表0.8倍
            //0代表1.0倍（默认）
            //1代表1.2倍
            //2代表1.5倍
            speed = 0f
            projectId = 0 //没啥用
            /*
            	普通音色
                0-云小宁，亲和女声(默认)
                1-云小奇，亲和男声
                2-云小晚，成熟男声
                4-云小叶，温暖女声
                5-云小欣，情感女声
                6-云小龙，情感男声
                7-云小曼，客服女声
                1000-智侠，情感男声
                1001-智瑜，情感女声
                1002-智聆，通用女声
                1003-智美，客服女声
                1050-WeJack，英文男声
                1051-WeRose，英文女声
                精品音色
                精品音色拟真度更高，价格不同于普通音色，查看购买指南
                101000-智侠，情感男声（精品）
                101001-智瑜，情感女声（精品）
                101002-智聆，通用女声（精品）
                101003-智美，客服女声（精品）
                101004-智云，通用男声
                101005-智莉，通用女声
                101006-智言，助手女声
                101007-智娜，客服女声
                101008-智琪，客服女声
                101009-智芸，知性女声
                101010-智华，通用男声
                101050-WeJack，英文男声（精品）
                101051-WeRose，英文女声（精品）
                102000-贝蕾，客服女声
                102001-贝果，客服女声
                102002-贝紫，粤语女声
                102003-贝雪，新闻女声
             */
            voiceType = 0

            //返回音频格式，可取值：wav（默认），mp3
            codec = "mp3"


        }
        val textToVoiceResponse = ttsClient.TextToVoice(textToVoiceRequest)
        if (textToVoiceResponse.audio != null) {
            val bytes = Base64.getDecoder().decode(textToVoiceResponse.audio)
            val now = LocalDate.now()
            val fileName = "${ttsInput.subSequence(0, 4)}-${now.year}-${now.month}-${now.dayOfMonth}"
            File("tencent/$fileName.mp3").sink().buffer().write(bytes).flush()
        }
    }
}