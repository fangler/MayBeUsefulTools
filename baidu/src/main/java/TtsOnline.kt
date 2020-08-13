import okhttp3.Request
import okhttp3.RequestBody
import okio.buffer
import okio.sink
import okio.source
import java.io.File

object TtsOnline {

    private val voiceMap = mapOf(Pair(1, "度小宇"), Pair(0, "度小美"),
            Pair(3, "度逍遥"), Pair(4, "度丫丫"),
            Pair(5003, "度逍遥（精品）"), Pair(5118, "度小鹿"),
            Pair(106, "度博文"), Pair(110, "度小童"),
            Pair(111, "度小萌"), Pair(103, "度米朵"), Pair(5, "度小娇"))

    // 发音人选择, 基础音库：度小宇=1，度小美=0，度逍遥（基础）=3，度丫丫=4
    // 精品音库：度逍遥（精品）=5003，度小鹿=5118，度博文=106，度小童=110，度小萌=111，度米朵=103，度小娇=5
    private const val per = 0
    // 语速，取值0-15，默认为5中语速
    private const val spd = 5
    // 音调，取值0-15，默认为5中语调
    private const val pit = 5
    // 音量，取值0-9，默认为5中音量
    private const val vol = 5

    private const val cuid = "1234567JAVA"

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private const val aue = 3

    private const val url = "http://tsn.baidu.com/text2audio" // 可以使用https

    fun dotts(ttsInput: String, token: String) {
        val encodeTtsInput = urlEncode(urlEncode(ttsInput))
        val bodyText = "tex=$encodeTtsInput&lan=zh&ctp=1&per=$per&spd=$spd&pit=$pit&vol=$vol&cuid=$cuid&tok=$token&aue=$aue"
        val body = RequestBody.create(null, bodyText)
        val request = Request.Builder().url(url).post(body).build()
        val call = Holder.okhttp.newCall(request)
        val response = call.execute()
        if (response.code() != 200) {
            Log.d("okhttp", "dotts failed with code = ${response.code()}, message = ${response.body()?.string()}")
            return
        } else {
            val responseBody = response.body() ?: return
            if (responseBody.contentType()?.type() == "audio") {
                val pd = voiceMap[per]?.also {
                    if (!File(it).exists()) {
                        File(it).mkdirs()
                    }
                }

                val buffer = File(pd ?: "", "$ttsInput.mp3").sink().buffer()
                buffer.writeAll(responseBody.byteStream().source())
                buffer.flush()
                Log.d("tts", "dotts success : $ttsInput.mp3")
            } else {
                Log.d("okhttp", "dotts Error: content-type=${responseBody.contentType()}, msg=${responseBody.string()}")
            }
        }
    }
}