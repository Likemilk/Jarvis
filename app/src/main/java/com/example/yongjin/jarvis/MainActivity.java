package com.example.yongjin.jarvis;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {
    private MyBroadCasting mReceiver;
    private TextToSpeech tts;
    int countPage=0;
    int isLanguageAvailable = 0;
    ArrayList<Button> bt=null;
    Integer[] bt_id = { R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10 };

    ArrayList<ResolveInfo> lists = null;
    PackageManager myPackageManager;
    Vibrator vb;

    int page = 1;
    int listNum=0;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerScreenStateReceiver();
        vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        bt = new ArrayList<Button>();
        for(int i=0;i<bt_id.length;i++){
            Button temp = (Button) findViewById(bt_id[i]);
            bt.add(temp);
        }

        lists=new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        myPackageManager = this.getPackageManager();
        List<ResolveInfo> intentList = myPackageManager.queryIntentActivities(intent, 0);
        Iterator<ResolveInfo> iterator = intentList.iterator();

        while(iterator.hasNext()){
            lists.add(iterator.next());
        }
        pagination();

        bt.get(8).setText("이전");
        bt.get(8).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(page==1){
                    tts.speak("이전 페이지는 존재하지 않습니다", TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getBaseContext(),"이전 페이지는 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                    vb.vibrate(1000);
                }else{
                    page--;
                    pagination();
                    tts.speak("이전페이지", TextToSpeech.QUEUE_FLUSH, null);

                    vb.vibrate(200);
                }
            }
        });
        bt.get(9).setText("다음");
        bt.get(9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(page>=countPage){
                    tts.speak("다음 페이지는 존재하지 않습니다", TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getBaseContext(),"다음 페이지는 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                    vb.vibrate(1000);
                }else{
                    page++;
                    pagination();
                    tts.speak("다음페이지", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    public void pagination() {
        int listTotal=lists.size();
        countPage = listTotal/8;
        listNum=0;

        if((page-1)==0){
            listNum=8*(page-1);
        }else{
            listNum=page*8;
        }
        Log.d("페이징 상태 확인",listTotal+" <> "+countPage+" <> "+page+" <> "+listNum+" <> ");
        if(listNum<listTotal) {
            bt.get(0).setText(lists.get(listNum).loadLabel(myPackageManager).toString());
            bt.get(0).setBackgroundColor(0xffffd42f);
            bt.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum).loadLabel(myPackageManager).toString() + "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(0).setText("빈 버튼");
            bt.get(0).setBackgroundColor(0xffff747f);
            bt.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
        if(listNum+1<listTotal) {
            bt.get(1).setText(lists.get(listNum+1).loadLabel(myPackageManager).toString());
            bt.get(1).setBackgroundColor(0xffffd42f);
            bt.get(1).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+1).loadLabel(myPackageManager).toString() + "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+1).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(1).setText("빈 버튼");
            bt.get(1).setBackgroundColor(0xffff747f);
            bt.get(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
        if(listNum+2<listTotal) {
            bt.get(2).setText(lists.get(listNum+2).loadLabel(myPackageManager).toString());
            bt.get(2).setBackgroundColor(0xffffd42f);
            bt.get(2).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+2).loadLabel(myPackageManager).toString() + "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+2).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(2).setText("빈 버튼");
            bt.get(2).setBackgroundColor(0xffff747f);
            bt.get(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
        if(listNum+3<listTotal) {
            bt.get(3).setText(lists.get(listNum+3).loadLabel(myPackageManager).toString());
            bt.get(3).setBackgroundColor(0xffffd42f);
            bt.get(3).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+3).loadLabel(myPackageManager).toString() + "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+3).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(3).setText("빈 버튼");
            bt.get(3).setBackgroundColor(0xffff747f);
            bt.get(3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
        if(listNum+4<listTotal) {
            bt.get(4).setText(lists.get(listNum+4).loadLabel(myPackageManager).toString());
            bt.get(4).setBackgroundColor(0xffffd42f);
            bt.get(4).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+4).loadLabel(myPackageManager).toString()+ "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+4).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(4).setText("빈 버튼");
            bt.get(4).setBackgroundColor(0xffff747f);
            bt.get(4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
        if(listNum+5<listTotal) {
            bt.get(5).setText(lists.get(listNum+5).loadLabel(myPackageManager).toString());
            bt.get(5).setBackgroundColor(0xffffd42f);
            bt.get(5).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+5).loadLabel(myPackageManager).toString()+ "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+5).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(5).setText("빈 버튼");
            bt.get(5).setBackgroundColor(0xffff747f);
            bt.get(5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
        if(listNum+6<listTotal) {
            bt.get(6).setText(lists.get(listNum+6).loadLabel(myPackageManager).toString());
            bt.get(6).setBackgroundColor(0xffffd42f);
            bt.get(6).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+6).loadLabel(myPackageManager).toString() + "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+6).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(6).setText("빈 버튼");
            bt.get(6).setBackgroundColor(0xffff747f);
            bt.get(6).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }
            if(listNum+7<listTotal) {
            bt.get(7).setText(lists.get(listNum+7).loadLabel(myPackageManager).toString());
            bt.get(7).setBackgroundColor(0xffffd42f);
            bt.get(7).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tts.speak(lists.get(listNum+7).loadLabel(myPackageManager).toString() + "어플리케이션을 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(lists.get(listNum+7).activityInfo.packageName);
                    startActivity(launchIntent);
                }
            });
        }else{
            bt.get(7).setText("빈 버튼");
            bt.get(7).setBackgroundColor(0xffff747f);
            bt.get(7).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts.speak("빈 버튼입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    vb.vibrate(200);
                    allKillRunningApps();
                }
            });
        }

    }

    private void registerScreenStateReceiver() {

        tts = new TextToSpeech(this, ttsListener);
        tts.setLanguage(Locale.KOREA);
        tts.setPitch(1.0f);
        tts.setSpeechRate(0.9f);
        isLanguageAvailable = tts.isLanguageAvailable(Locale.KOREA);

        //dbHelper = new DBHelper(this,this.tts);
        //Screen on 과 off 는 manifest 에서 직접 기술해서 사용할 수 없다.
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyBroadCasting(tts);
        registerReceiver(mReceiver, filter);
    }


    void allKillRunningApps()	{
        // 시각장애인을위한 리소스 낭비 방지
        ActivityManager activity_manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> app_list = activity_manager.getRunningAppProcesses();
        for(int i=0; i<app_list.size(); i++)	{
            if(!app_list.get(i).processName.equals("com.example.yongjin.jarvis"))	{
                android.os.Process.sendSignal(app_list.get(i).pid, android.os.Process.SIGNAL_KILL);
                activity_manager.killBackgroundProcesses(app_list.get(i).processName);
            }
        }
        System.gc();
    }
    TextToSpeech.OnInitListener ttsListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            Log.d("테스트", "여긴가1");
            if (status == TextToSpeech.SUCCESS) {
                Log.d("테스트", "여긴가2");
            } else {
                Log.d("테스트", "여긴가3");
            }
        }
    };
}

