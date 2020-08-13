fun main() {

    Log.d("main", "start tts...")

    val tts = "今天天气正好，哈哈哈哈哈啊哈"

    Token.refresh()
    Token.getToken()?.let {
        TtsOnline.dotts(tts, it)
        Log.d("main", "end tts success...")
    } ?: Log.d("main", "end tts, token is empty...")

}