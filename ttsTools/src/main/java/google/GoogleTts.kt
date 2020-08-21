package google

import com.google.auth.oauth2.AccessToken
import com.google.cloud.texttospeech.v1.*
import common.Log
import okio.buffer
import okio.sink
import java.io.File
import java.util.*

/**
 * google tts
 */
object GoogleTts {
    private var mTtsClient: TextToSpeechClient? = null

    // Build the voice request
    /*
    中文普通话	标准	cmn-CN	cmn-CN-Standard-A	女
    中文普通话	标准	cmn-CN	cmn-CN-Standard-B	男
    中文普通话	标准	cmn-CN	cmn-CN-Standard-C	男
    中文普通话	标准	cmn-CN	cmn-CN-Standard-D	女
    中文普通话	WaveNet	cmn-CN	cmn-CN-Wavenet-A	女
    中文普通话	WaveNet	cmn-CN	cmn-CN-Wavenet-B	男
    中文普通话	WaveNet	cmn-CN	cmn-CN-Wavenet-C	男
    中文普通话	WaveNet	cmn-CN	cmn-CN-Wavenet-D	女
     */
    private val mVoiceInstant = VoiceSelectionParams.newBuilder()
        .apply {
            languageCode = "cmn-CN"
            name = "cmn-CN-Standard-A"

            println("默认语言： code = $languageCode, name = $name")
        }
        .build()

    // Select the type of audio file you want returned
    private val mAudioConfigInstant = AudioConfig.newBuilder()
        .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
        .build()


    fun setUpClient(token: String, expireDate: Date): TextToSpeechClient {
        mTtsClient = TextToSpeechClient.create(
            TextToSpeechSettings.newBuilder()
                .setCredentialsProvider(
                    SBCredentialsProvider(
                        AccessToken(token, expireDate)
                    )
                )
                .build()
        )
        return mTtsClient!!
    }

    fun tts(tts: String, ttsFileName: String): Boolean {
        mTtsClient?.let {
            Log.d("tts", "执行: $tts --> $ttsFileName")
            _tts(it, tts, ttsFileName)
            return File(ttsFileName).length() > 0
        } ?: return false
    }

    private fun _tts(ttsClient: TextToSpeechClient, tts: String, ttsFileName: String) {
        try {
            val synthesizeSpeechResponse = ttsClient.synthesizeSpeech(
                SynthesisInput.newBuilder().setText(tts).build(),
                mVoiceInstant,
                mAudioConfigInstant
            )
            val audioContent = synthesizeSpeechResponse.audioContent
            val Dir = File("goodTts")
            if (!Dir.exists()) {
                Dir.mkdirs()
            }
            File(Dir, ttsFileName).sink().buffer().write(audioContent.toByteArray()).flush()
            Log.d("tts", "tts success :: $ttsFileName")
        } catch (e: java.lang.Exception) {
            Log.d("tts", "tts Exception: $e")
        }
    }
}