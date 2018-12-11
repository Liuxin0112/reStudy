package com.whtlkj.duku.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.iflytek.cloud.speech.*;
import com.whtlkj.duku.service.SpeechService;
import com.whtlkj.duku.utils.DebugLog;
import com.whtlkj.duku.utils.FileUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SpeechServiceImpl implements SpeechService {
    // 合成webapi接口地址
    private static final String WEBTTS_URL = "http://api.xfyun.cn/v1/service/v1/tts";
    // 应用ID
    private static final String APPID = "5b972e8d";
    // 接口密钥
    private static final String API_KEY = "470825493c4b8044b5741d61dd20f644";
    // 音频编码
    private static final String AUE = "raw";
    // 采样率
    private static final String AUF = "audio/L16;rate=16000";
    // 发音人
    private static final String VOICE_NAME = "xiaoyan";
    // 引擎类型
    private static final String ENGINE_TYPE = "intp65_en";
    // 文本类型
    private static final String TEXT_TYPE = "text";

    // 语速
    private static final String SPEED = "50";
    // 音量
    private static final String VOLUME = "50";
    // 音调
    private static final String PITCH = "50";

//    public static void main(String[] args){
//        Map<String,Object> map = new HashMap<>();
//        map.put("text","读了4年大学，拿不到学位证书的大有人在。该校一名大四学生告诉记者，有些大学生脱离老师和父母管束，就像脱缰的野马，通宵打游戏、逃课，考试挂科的情况屡见不鲜。");
//        map.put("speed","50");
//        map.put("volumn","50");
//        map.put("pitch","50");
//
//        try {
//            Map<String, Object> re = speechTTS(map);
//            System.out.println(re);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public Map<String, Object> speechTTS(Map<String, Object> requestMap) throws UnsupportedEncodingException {
        // 获取参数
        String text = (String) requestMap.get("text");          // 待合成文本
        String speed = (String) requestMap.get("speed");      // 语速
        String volumn = (String) requestMap.get("volumn");    // 音量
        String pitch = (String) requestMap.get("pitch");      // 音调

        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 创建SSLContext
        StringBuffer buffer = null;

        // 封装json参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("engine_type", "sms-en16k");    // 引擎类型
        map.put("aue", "raw");              // 音频编码
        String jsonString = JSON.toJSONString(map);
//        String api_key = "f028da72174ccd3a0b31196614fa8000";
//        String x_appid = "5b972e8d";
//        String x_param = Base64.getEncoder().encodeToString(jsonString.getBytes());

        String param = "{\"auf\":\"" + AUF + "\",\"aue\":\"" + AUE + "\",\"voice_name\":\"" + VOICE_NAME + "\",\"speed\":\"" + speed + "\",\"volume\":\"" + volumn + "\",\"pitch\":\"" + pitch + "\",\"engine_type\":\"" + ENGINE_TYPE + "\",\"text_type\":\"" + TEXT_TYPE + "\"}";
//        String param = "{\"auf\":\"" + AUF + "\",\"aue\":\"" + AUE + "\",\"voice_name\":\"" + VOICE_NAME + "\",\"speed\":\"" + SPEED + "\",\"volume\":\"" + VOLUME + "\",\"pitch\":\"" + PITCH + "\",\"engine_type\":\"" + ENGINE_TYPE + "\",\"text_type\":\"" + TEXT_TYPE + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));

        String time = System.currentTimeMillis() / 1000L + "";
        String checkSum = DigestUtils.md5Hex(API_KEY + time + paramBase64);

        try {
            URL url = new URL(WEBTTS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("X-Appid", APPID);
            conn.setRequestProperty("X-CurTime", time);
            conn.setRequestProperty("X-Param", paramBase64);
            conn.setRequestProperty("X-CheckSum", checkSum);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容

            String ntext = "text=" + URLEncoder.encode(text, "utf-8");

            if (null != text) {
                OutputStream os = conn.getOutputStream();
                os.write(ntext.getBytes("utf-8"));
                os.close();
            }

            // 读取服务器端返回的内容
            String responseContentType = conn.getHeaderField("Content-Type");
            if ("audio/mpeg".equals(responseContentType)) {
                // 获取响应body
                byte[] bytes = toByteArray(conn.getInputStream());
                resultMap.put("Content-Type", "audio/mpeg");
                resultMap.put("sid", conn.getHeaderField("sid"));
                resultMap.put("body", bytes);
            } else {
                // 设置请求 body
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String result = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                resultMap.put("Content-Type", "text/plain");
                resultMap.put("body", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> remap = new HashMap<>();
//        Map<String,Object> resultMap = (Map<String, Object>) JSON.parse(buffer.toString());
        if ("audio/mpeg".equals(resultMap.get("Content-Type"))) { // 合成成功
            if ("raw".equals(AUE)) {
                FileUtil.save("C:\\Users\\HP\\Desktop\\sample", resultMap.get("sid") + ".wav", (byte[]) resultMap.get("body"));
                System.out.println("合成 WebAPI 调用成功，音频保存位置：C:\\Users\\HP\\Desktop\\CET4_6sp\\sample\\" + resultMap.get("sid") + ".wav");
                remap.put("url","C:\\Users\\HP\\Desktop\\CET4_6sp\\sample\\" + resultMap.get("sid") + ".wav");
            } else {
                FileUtil.save("C:\\Users\\HP\\Desktop\\sample", resultMap.get("sid") + ".mp3", (byte[]) resultMap.get("body"));
                System.out.println("合成 WebAPI 调用成功，音频保存位置：C:\\Users\\HP\\Desktop\\CET4_6sp\\sample\\" + resultMap.get("sid") + ".mp3");
                remap.put("url","C:\\Users\\HP\\Desktop\\CET4_6sp\\sample\\" + resultMap.get("sid") + ".mp3");
            }
        } else { // 合成失败
            System.out.println("合成 WebAPI 调用失败，错误信息：" + resultMap.get("body").toString());
        }
        return remap;
    }

    @Override
    public Map<String, Object> speechTTSLocal(Map<String, Object> requestMap) {
        // 获取参数
        String text = (String) requestMap.get("text");          // 待合成文本
        String speed = (String) requestMap.get("speed");      // 语速
        String volumn = (String) requestMap.get("volumn");    // 音量
        String pitch = (String) requestMap.get("pitch");      // 音调

        Setting.setShowLog( false );
        SpeechUtility.createUtility("appid=5b972e12");

        SpeechSynthesizer speechSynthesizer = SpeechSynthesizer
                .createSynthesizer();
        // 设置发音人
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng");// xiaoyu xiaofeng xiaomei xiaorong
        speechSynthesizer.setParameter(SpeechConstant.VOLUME,volumn);
        speechSynthesizer.setParameter(SpeechConstant.PITCH,pitch);
        speechSynthesizer.setParameter(SpeechConstant.SPEED,speed);

        //启用合成音频流事件，不需要时，不用设置此参数
        speechSynthesizer.setParameter( SpeechConstant.TTS_BUFFER_EVENT, "1" );
        // 设置合成音频保存位置（可自定义保存位置, 要带文件名），默认不保存
        speechSynthesizer.synthesizeToUri(text,
                "C:\\Users\\HP\\Desktop\\sample\\msc"+System.currentTimeMillis()+".pcm",
                synthesizeToUriListener);
        Map<String,Object> map = new HashMap<>();
        // file:///C:/Users/HP/Desktop/sample/msc1539859720313.wav
        map.put("url"," file:///C:/Users/HP/Desktop/sample/msc"+System.currentTimeMillis()+".pcm");
        return map;
    }

    /**
     * 合成监听器
     */
    static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {

        public void onBufferProgress(int progress) {
//            DebugLog.Log("*************合成进度*************" + progress);

        }

        public void onSynthesizeCompleted(String uri, SpeechError error) {
            if (error == null) {
                DebugLog.Log("*************合成成功*************");
                DebugLog.Log("合成音频生成路径：" + uri);
            }
            else
                DebugLog.Log("*************" + error.getErrorCode()
                        + "*************");
        }


        @Override
        public void onEvent(int eventType, int arg1, int arg2, int arg3, Object obj1, Object obj2) {
            if( SpeechEvent.EVENT_TTS_BUFFER == eventType ){
//                DebugLog.Log( "onEvent: type="+eventType
//                        +", arg1="+arg1
//                        +", arg2="+arg2
//                        +", arg3="+arg3
//                        +", obj2="+(String)obj2 );
                ArrayList<?> bufs = null;
                if( obj1 instanceof ArrayList<?> ){
                    bufs = (ArrayList<?>) obj1;
                }else{
                    DebugLog.Log( "onEvent error obj1 is not ArrayList !" );
                }//end of if-else instance of ArrayList

                if( null != bufs ){
                    for( final Object obj : bufs ){
                        if( obj instanceof byte[] ){
                            final byte[] buf = (byte[]) obj;
//                            DebugLog.Log( "onEvent buf length: "+buf.length );
                        }else{
                            DebugLog.Log( "onEvent error element is not byte[] !" );
                        }
                    }//end of for
                }//end of if bufs not null
            }//end of if tts buffer event
        }

    };

    /**
     * 流转二进制数组
     *
     * @param in
     * @return
     * @throws IOException
     */
    private byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}
