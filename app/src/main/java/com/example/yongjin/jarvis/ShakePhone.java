package com.example.yongjin.jarvis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by YongJin on 2015-06-20.
 */
public class ShakePhone {

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 1000;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    SensorListener listener;
    Sensor sensor;
    Vibrator vb;
    SensorManager manager;
    Context context;
    TextToSpeech tts;
    Intent stt;
    SpeechRecognizer mRecognizer;
    String result = "";
    DBHelper dbHelper;
    Weather weather;
    public ShakePhone(Context context, TextToSpeech tts) {
        this.tts = tts;
        this.context = context;
        weather = new Weather (context, tts);
        vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        listener = new SensorListener();
        manager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        mRecognizer.setRecognitionListener(sttListener);
        stt = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        stt.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        dbHelper = new DBHelper(context, tts);
    }

    //센서 리스너를 만들어주어야 한다
    class SensorListener implements SensorEventListener {
        //센서 값이 바뀔때마다 호출되는 메서드
        int time = 0;

        @Override
        public void onSensorChanged(SensorEvent event) {
            //센서의 타입값을 얻어오겠다.
            int type = event.sensor.getType();
            if (type == Sensor.TYPE_ACCELEROMETER) {
                long currentTime = System.currentTimeMillis();
                long gabOfTime = (currentTime - lastTime);
                if (gabOfTime > 100) {
                    lastTime = currentTime;
                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
                    //if (MainActivity.oneTime) {
                    if (speed > SHAKE_THRESHOLD) {
                        time++;
                        vb.vibrate(100);
                        if (time >= 5) {

                            tts.speak("자비스 부르셨나요?", TextToSpeech.QUEUE_FLUSH, null);
                            vb.vibrate(200);
                            SystemClock.sleep(2000);
                            mRecognizer.startListening(stt);
                            //delayListeningTime(4000);
                            manager.unregisterListener(this);
                            time = 0;
                        }
                    }
                    lastX = event.values[DATA_X];
                    lastY = event.values[DATA_Y];
                    lastZ = event.values[DATA_Z];
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    }

    public void doProcess(String temp) {
        String split[] = result.split(" ");
        for (String result : split) {
            if (result.equals("자비스")) {
                tts.speak("네 말씀하세요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
                SystemClock.sleep(2000);
                mRecognizer.startListening(stt);
            } else if (result.equals("시끄러")) {
                tts.speak("네.. 죄송해요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("시끄러워")) {
                tts.speak("네.. 죄송해요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("너아니야")) {
                tts.speak("자비스 종료합니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("종료")) {
                tts.speak("자비스 종료합니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("셧다운")) {
                tts.speak("자비스 종료합니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("아니")) {
                tts.speak("제가 아니군요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("노")) {
                tts.speak("제가 아니군요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("꺼져")) {
                tts.speak("말씀 너무하시네요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("저리가")) {
                tts.speak("네 알겠습니다", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("닥쳐")) {
                tts.speak("정말 너무하시네요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("멍청이")) {
                tts.speak("반사", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("똥개")) {
                tts.speak("무지개반사", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("시야")) {
                tts.speak(getTime(), TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("시")) {
                tts.speak(getTime(), TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("시간")) {
                tts.speak(getTime(), TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("시계")) {
                tts.speak(getTime(), TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("현재")) {
                tts.speak(getTime(), TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("몇일이야")) {
                tts.speak(getTime(), TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("메롱")) {
                tts.speak("주인님은 나이가 초등학생정도 인가봐요", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("날씨")) {
                weather.getWeather();
                vb.vibrate(200);
            } else if (result.equals("날씨는")) {
                weather.getWeather();
                vb.vibrate(200);
            } else if (result.equals("어디야")) {
                weather.getWhere();
                vb.vibrate(200);
            } else if (result.equals("위치")) {
                weather.getWhere();
                vb.vibrate(200);
            } else if (result.equals("어디")) {
                weather.getWhere();
                vb.vibrate(200);
            } else if (result.equals("여기")) {
                weather.getWhere();
                vb.vibrate(200);
            } else if (result.equals("현재위치")) {
                weather.getWhere();
                vb.vibrate(200);
            } else if (result.equals("도와")) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else if (result.equals("살려")) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-3794-2292", null, weather.getWhere2() , null, null);
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            }   else if (result.equals("위치 전송")) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-3794-2292", null, weather.getWhere2() , null, null);
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            }    else if (result.equals("으악")) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-3794-2292", null, weather.getWhere2() , null, null);
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            }    else if (result.equals("악")) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-3794-2292", null, weather.getWhere2() , null, null);
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            }    else if (result.equals("곤란해")) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-3794-2292", null, weather.getWhere2() , null, null);
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            }     else if (result.equals("곤란")) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("010-3794-2292", null, weather.getWhere2() , null, null);
                smsManager.sendTextMessage("010-5596-6935", null, weather.getWhere2() , null, null);
                tts.speak("응급 메세지를 전송했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(200);
            } else {
                if (dbHelper.checkPackage(result)) {
                } else {
                }
                if (!tts.isSpeaking()) {
                    tts.speak(temp + "라는 말이 무슨 소리죠?", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                }
            }
        }
        if (temp.equals("") | temp.equals(" ") || temp == null) {
            tts.speak("뭔가 말해봐요.", TextToSpeech.QUEUE_FLUSH, null);
            vb.vibrate(200);
            SystemClock.sleep(4000);
            mRecognizer.startListening(stt);
        }
    }

    private RecognitionListener sttListener = new RecognitionListener() {

        @Override
        public void onResults(Bundle results) {
            // TODO Auto-generated method stub
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            result = mResult.get(0);
            doProcess(result);
            Log.d("likemilk", result);
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onError(int error) {
            // TODO Auto-generated method stub
            if(!tts.isSpeaking()) {
                tts.speak("잘 못들었습니다?", TextToSpeech.QUEUE_FLUSH, null);
                vb.vibrate(1000);
            }
            mRecognizer.stopListening();
        }

        @Override
        public void onEndOfSpeech() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onBeginningOfSpeech() {
            // TODO Auto-generated method stub
        }
    };


    public String getTime() {
        // 현재 시간을 msec으로 구한다.
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷으로 만든다.
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
        String strNow = sdfNow.format(date);
        return "현재 시간은" + strNow + "입니다";
    }


}
