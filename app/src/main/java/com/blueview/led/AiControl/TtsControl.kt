package com.blueview.led.AiControl

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.tencent.qcloudtts.LongTextTTS.LongTextTtsController
import com.tencent.qcloudtts.VoiceLanguage
import com.tencent.qcloudtts.VoiceSpeed
import com.tencent.qcloudtts.VoiceType
import com.tencent.qcloudtts.callback.QCloudPlayerCallback
import com.tencent.qcloudtts.callback.TtsExceptionHandler
import com.tencent.qcloudtts.exception.TtsNotInitializedException

object TtsControl {

    private lateinit var mTtsController: LongTextTtsController
    private lateinit var mContext:Context
    private var listener: OnAudioFocusChangeListener? = null

    fun TtsControlinit(context: Context,string: String)
    {

        mContext=context
        //构造LongTextTtsController，支持长文本播放，可暂停/恢复播放。非流式api，故建议文本中第一句话不要设的太长
        mTtsController = LongTextTtsController()


        /* 在使用云API之前，请前往 腾讯云API密钥页面 申请安全凭证。 安全凭证包括 SecretId 和 SecretKey：
         * SecretId 用于标识 API 调用者身份
         * SecretKey 用于加密签名字符串和服务器端验证签名字符串的密钥。
         */

        //注意：这里只是示例，请根据用户实际申请的 SecretId 和 SecretKey 进行后续操作！
        mTtsController.init(context, 1259576303L, "AKIDEE9oDMnpebZHjtqQbWhmb9QcaLq5nlES", "5lFSUw83mKp54mdJktlPnukdGbfSvGME")
        requestAudioFocus(mTtsController)

        //设置语速
        mTtsController.setVoiceSpeed(0)

        //设置音色
        mTtsController.setVoiceType(102000)

        //设置语言
        mTtsController.setVoiceLanguage(1) //1:zh 2:en

        //设置ProjectId
        mTtsController.setProjectId(0)
        start(string,context)
    }
    private fun requestAudioFocus(@NonNull ttsController: LongTextTtsController) {
        //初始化audio mananger
        listener = OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //丢失焦点，直接
                mTtsController.stop()
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                //丢失焦点，但是马上又能恢复
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //降低音量
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //获得了音频焦点
            }
        }

        //设置listener
        val am = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am?.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }
    private fun start(ttsText: String,context: Context) {
        try {
            mTtsController.startTts(ttsText, mTtsExceptionHandler, object : QCloudPlayerCallback {
                //播放开始
                override fun onTTSPlayStart() {
                   AudioRecognzie.stop()
                }

                //音频缓冲中
                override fun onTTSPlayWait() {
                    Log.d("tts", "onPlayWait")
                }

                //缓冲完成，继续播放
                override fun onTTSPlayResume() {
                    Log.d("tts", "onPlayResume")
                }

                //连续播放下一句
                override fun onTTSPlayNext() {
                    Log.d("tts", "onPlayNext")
                }

                //播放中止
                override fun onTTSPlayStop() {
                    Log.d("tts", "onPlayStop")
                }

                //播放结束
                override fun onTTSPlayEnd() {
                    Log.d("tts", "onPlayEnd")
                    AudioRecognzie.AudioRecognzieInit(context)
                }

                //当前播放的字符,当前播放的字符在所在的句子中的下标.
                override fun onTTSPlayProgress(
                    currentWord: String,
                    currentIndex: Int
                ) {
                    Log.d("tts", "onTTSPlayProgress$currentWord$currentIndex")
                }
            })
        } catch (e: TtsNotInitializedException) {
            Log.e("tts", "TtsNotInitializedException e:" + e.message)
        }
    }
    private val mTtsExceptionHandler =
        TtsExceptionHandler { e ->
            //网络出错的时候
            mTtsController.pause()
            Toast.makeText(mContext, e.errMsg, Toast.LENGTH_SHORT).show()
        }
}