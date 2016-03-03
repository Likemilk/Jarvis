package com.example.yongjin.jarvis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;

/**
 * Created by YongJin on 2015-06-20.
 */

public class MyBroadCasting extends BroadcastReceiver {
    //-----조용진의 필요변수 -시작
    TextToSpeech tts;
    Vibrator vb;
    public static boolean wasScreenOn = true;
    private ShakePhone shake;
    //-----조용진의 필요 변수 -완료

    public MyBroadCasting(TextToSpeech tts){
        this.tts = tts;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        init(context);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // DO WHATEVER YOU NEED TO DO HERE
            wasScreenOn = false;
            tts.speak("화면이 종료되었습니다.", TextToSpeech.QUEUE_FLUSH, null);
            vb.vibrate(200);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            wasScreenOn = true;
            tts.speak("화면이 켜졌습니다.", TextToSpeech.QUEUE_FLUSH,null);
            vb.vibrate(200);
            shake = new ShakePhone(context,tts);
            shake.manager.registerListener(shake.listener, shake.sensor, SensorManager.SENSOR_DELAY_UI);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        shake.manager.unregisterListener(shake.listener, shake.sensor);
                        vb.vibrate(500);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable,5000);
            // 무언가 작업 을 실행하면 shake.manager.unregisterListener(shake.listener); 이것을 넣어서 리스너를 종료해준다.

        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            wasScreenOn = true;
            tts.speak("휴대폰의 정상 부팅 완료되었습니다.", TextToSpeech.QUEUE_FLUSH, null);
            vb.vibrate(2000);
        }
    }

    public void init(Context context){
        vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //TTS 객체 생성
    }
}
