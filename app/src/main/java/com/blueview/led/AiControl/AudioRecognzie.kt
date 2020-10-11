package com.blueview.led.AiControl

import android.content.Context
import com.blueview.led.Data.MessageEvent_Aimsg
import com.tencent.aai.AAIClient
import com.tencent.aai.audio.data.AudioRecordDataSource
import com.tencent.aai.auth.AbsCredentialProvider
import com.tencent.aai.auth.LocalCredentialProvider
import com.tencent.aai.exception.ClientException
import com.tencent.aai.exception.ServerException
import com.tencent.aai.listener.AudioRecognizeResultListener
import com.tencent.aai.listener.AudioRecognizeStateListener
import com.tencent.aai.listener.AudioRecognizeTimeoutListener
import com.tencent.aai.model.AudioRecognizeRequest
import com.tencent.aai.model.AudioRecognizeResult
import com.tencent.aai.model.type.AudioRecognizeConfiguration
import com.tencent.aai.model.type.AudioRecognizeTemplate
import com.tencent.aai.model.type.EngineModelType
import org.greenrobot.eventbus.EventBus

object AudioRecognzie {

    private  var aaiClient: AAIClient? =null
    private lateinit var audioRecognizeStateListener:AudioRecognizeStateListener
    private lateinit var audioRecognizeRequest:AudioRecognizeRequest
    private lateinit var audioRecognizeResultListener:AudioRecognizeResultListener
    private lateinit var audioRecognizeTimeoutListener:AudioRecognizeTimeoutListener
    private lateinit var audioRecognizeConfiguration:AudioRecognizeConfiguration
    private var isAudio:Boolean=false

    fun AudioRecognzieInit(context: Context)
    {
        // 用户配置：需要在控制台申请相关的账号;
        val appid = 1259576303
        val projectId = 0
        val secretId = "AKIDEE9oDMnpebZHjtqQbWhmb9QcaLq5nlES"
        val secretKey = "5lFSUw83mKp54mdJktlPnukdGbfSvGME"

        val credentialProvider: AbsCredentialProvider = LocalCredentialProvider(secretKey)

        // 1、初始化AAIClient对象。
        if(aaiClient==null)
        {
            try {
                aaiClient = AAIClient(context, appid, projectId, secretId, credentialProvider)
            }catch (e: ClientException) {
                e.printStackTrace()
            }
        }
        // 2、初始化语音识别请求。
        val isSaveAudioRecordFiles = false //默认是关的 false

        // 初始化识别请求
        val builder = AudioRecognizeRequest.Builder()
        audioRecognizeRequest = builder //
            .pcmAudioDataSource(AudioRecordDataSource(isSaveAudioRecordFiles)) // 设置数据源
            //.templateName(templateName) // 设置模板
            .template(
                AudioRecognizeTemplate(
                    EngineModelType.EngineModelType16K.type,
                    0,
                    0
                )
            ) // 设置自定义模板
            .setFilterDirty(0) // 0 ：默认状态 不过滤脏话 1：过滤脏话
            .setFilterModal(0) // 0 ：默认状态 不过滤语气词  1：过滤部分语气词 2:严格过滤
            .setFilterPunc(1) // 0 ：默认状态 不过滤句末的句号 1：滤句末的句号
            .setConvert_num_mode(1) //1：默认状态 根据场景智能转换为阿拉伯数字；0：全部转为中文数字。
            //                        .setHotWordId("")//热词 id。用于调用对应的热词表，如果在调用语音识别服务时，不进行单独的热词 id 设置，自动生效默认热词；如果进行了单独的热词 id 设置，那么将生效单独设置的热词 id。
            .build()

        // 自定义识别配置
        audioRecognizeConfiguration=AudioRecognizeConfiguration.Builder()
            .setSilentDetectTimeOut(true) // 是否使能静音检测，true表示不检查静音部分
            .audioFlowSilenceTimeOut(5000) // 静音检测超时停止录音
            .minAudioFlowSilenceTime(1500) // 语音流识别时的间隔时间
            .minVolumeCallbackTime(80) // 音量回调时间
            .sensitive(2.5f) // 识别敏感度
            .build()

        // 3、初始化语音识别结果监听器。

        audioRecognizeResultListener=
            object : AudioRecognizeResultListener {
                override fun onSliceSuccess(
                    audioRecognizeRequest: AudioRecognizeRequest,
                    audioRecognizeResult: AudioRecognizeResult,
                    i: Int
                ) {
                    // 返回语音分片的识别结果
                }

                override fun onSegmentSuccess(
                    audioRecognizeRequest: AudioRecognizeRequest,
                    audioRecognizeResult: AudioRecognizeResult,
                    i: Int
                ) {
                    // 返回语音流的识别结果
                    EventBus.getDefault().post(
                        MessageEvent_Aimsg(
                            audioRecognizeResult.text
                        )
                    )
                    clean()
                }

                override fun onSuccess(
                    audioRecognizeRequest: AudioRecognizeRequest,
                    s: String
                ) {
                    // 返回所有的识别结果
                }



                override fun onFailure(
                    audioRecognizeRequest: AudioRecognizeRequest,
                    e: ClientException,
                    e1: ServerException
                ) {
                    // 识别失败
                    if (e != null) {
                        EventBus.getDefault().post(
                            MessageEvent_Aimsg(
                                "客户端异常"
                            )
                        )
                    }
                    if (e1 != null) {
                        EventBus.getDefault().post(
                            MessageEvent_Aimsg(
                                "服务端异常"
                            )
                        )
                    }
                }

            }

        /**
         * 识别状态监听器
         */
        audioRecognizeStateListener=
            object : AudioRecognizeStateListener {
                /**
                 * 开始录音
                 * @param request
                 */
                override fun onStartRecord(request: AudioRecognizeRequest) {
                    EventBus.getDefault().post(MessageEvent_Aimsg("起动语音"))
                    isAudio=true
                }

                /**
                 * 结束录音
                 * @param request
                 */
                override fun onStopRecord(request: AudioRecognizeRequest) {
                    EventBus.getDefault().post(MessageEvent_Aimsg("停止语音"))
                    isAudio=false
                }

                /**
                 * 第seq个语音流开始识别
                 * @param request
                 * @param seq
                 */
                override fun onVoiceFlowStartRecognize(
                    request: AudioRecognizeRequest,
                    seq: Int
                ) {

                }

                /**
                 * 第seq个语音流结束识别
                 * @param request
                 * @param seq
                 */
                override fun onVoiceFlowFinishRecognize(
                    request: AudioRecognizeRequest,
                    seq: Int
                ) {

                }

                /**
                 * 第seq个语音流开始
                 * @param request
                 * @param seq
                 */
                override fun onVoiceFlowStart(
                    request: AudioRecognizeRequest,
                    seq: Int
                ) {

                }

                /**
                 * 第seq个语音流结束
                 * @param request
                 * @param seq
                 */
                override fun onVoiceFlowFinish(
                    request: AudioRecognizeRequest,
                    seq: Int
                ) {

                }

                /**
                 * 语音音量回调
                 * @param request
                 * @param volume
                 */
                override fun onVoiceVolume(
                    request: AudioRecognizeRequest,
                    volume: Int
                ) {
                    //EventBus.getDefault().post(MessageEvent_Aimsg(volume.toString()))
                }
            }

        /**
         * 识别超时监听器
         */
        /**
         * 识别超时监听器
         */
        audioRecognizeTimeoutListener=
            object : AudioRecognizeTimeoutListener {
                /**
                 * 检测第一个语音流超时
                 * @param request
                 */
                override fun onFirstVoiceFlowTimeout(request: AudioRecognizeRequest) {

                }

                /**
                 * 检测下一个语音流超时
                 * @param request
                 */
                override fun onNextVoiceFlowTimeout(request: AudioRecognizeRequest) {

                }
            }
        if (!isAudio)
        {
            Thread(Runnable {
                aaiClient?.startAudioRecognize(audioRecognizeRequest, audioRecognizeResultListener, audioRecognizeStateListener, audioRecognizeTimeoutListener, audioRecognizeConfiguration)
            }).start()
        }else{
            clean()
        }
    }
    fun stop()
    {
        val requestId = audioRecognizeRequest.requestId
            Thread(Runnable {
                aaiClient?.stopAudioRecognize(requestId)
            }).start()

    }
    fun clean()
    {
        val requestId = audioRecognizeRequest.requestId
            Thread(Runnable {
                aaiClient?.cancelAudioRecognize(requestId)
            }).start()

    }
    fun release()
    {
        aaiClient?.release();
    }
}