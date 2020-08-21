package microsoft

import common.Holder
import common.Log
import common.base64Encode
import okhttp3.Headers
import okhttp3.Request

//FIXME 未验证
object MicrosoftTts {

    fun listVoices(accessToken: String) {
        val URL = "https://eastasia.tts.speech.microsoft.com/cognitiveservices/voices/list"
        val request = Request.Builder().url(URL).headers(
            Headers.of(
                mapOf(
                    "Authorization" to accessToken.base64Encode()
                )
            )
        ).build()
        val call = Holder.okhttp.newCall(request)
        val response = call.execute()
        Log.d("listVoices", "response ${response.code()}, ${response.body()?.string()}")
    }


    //https://docs.microsoft.com/zh-cn/azure/cognitive-services/speech-service/rest-text-to-speech
    /**
     *raw-16khz-16bit-mono-pcm            raw-8khz-8bit-mono-mulaw
    riff-8khz-8bit-mono-alaw            riff-8khz-8bit-mono-mulaw
    riff-16khz-16bit-mono-pcm           audio-16khz-128kbitrate-mono-mp3
    audio-16khz-64kbitrate-mono-mp3     audio-16khz-32kbitrate-mono-mp3
    raw-24khz-16bit-mono-pcm            riff-24khz-16bit-mono-pcm
    audio-24khz-160kbitrate-mono-mp3    audio-24khz-96kbitrate-mono-mp3
    audio-24khz-48kbitrate-mono-mp3     ogg-24khz-16bit-mono-opus
     */
    fun normal(accessToken: String) {
        val URL = "https://eastasia.tts.speech.microsoft.com/cognitiveservices/v1"
        val request = Request.Builder().url(URL).headers(
            Headers.of(
                mapOf(
                    "Content-type" to "application/ssml+xml",
                    "Authorization" to accessToken.base64Encode(),
                    "User-Agent" to "Niubi sumsang mobie .. hah ",
                    "X-Microsoft-OutputFormat" to "audio-16khz-64kbitrate-mono-mp3 "
                )
            )
        ).build()
        val call = Holder.okhttp.newCall(request)
        val response = call.execute()
        Log.d("normal", "response ${response.code()}, ${response.body()?.string()}")
    }

    //自定义语音
    fun hard(deploymentId: String, accessToken: String) {
        val URL = "https://eastasia.voice.speech.microsoft.com/cognitiveservices/v1?deploymentId=$deploymentId"

        //fixme 请求逻辑同上
    }
}