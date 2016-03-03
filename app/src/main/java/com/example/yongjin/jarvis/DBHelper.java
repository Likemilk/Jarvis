package com.example.yongjin.jarvis;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by YongJin on 2015-06-19.
 */
public class DBHelper extends SQLiteOpenHelper {
    final static String DB_NAME="jarvis.db";
    final static String TABLE_NAME="applist";
    final static int DB_VERSION=2;
    Context context;
    SQLiteDatabase db;
    Cursor cursor;
    private TextToSpeech tts;
    public DBHelper(Context context,TextToSpeech tts){
        super(context,DB_NAME,null,DB_VERSION);
        this.tts = tts;
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        TelephonyManager mag;
        String sql = "create table "+TABLE_NAME+"("
                +"name char(20) primary key not null," +
                "package varchar(50));";
        db.execSQL(sql);
        db.execSQL("insert into "+TABLE_NAME+" values('이메일','com.lge.email');");
        db.execSQL("insert into "+TABLE_NAME+" values('메일','com.lge.email');");
        db.execSQL("insert into "+TABLE_NAME+" values('지메일','com.google.android.gm');");
        db.execSQL("insert into "+TABLE_NAME+" values('구글메일','com.google.android.gm');");
        db.execSQL("insert into "+TABLE_NAME+" values('문자','com.android.mms');");
        db.execSQL("insert into "+TABLE_NAME+" values('메세지','com.android.mms');");
        db.execSQL("insert into "+TABLE_NAME+" values('주소록','com.android.contact');");
        db.execSQL("insert into "+TABLE_NAME+" values('전화번호부','com.android.contact');");
        db.execSQL("insert into "+TABLE_NAME+" values('페이스북','com.facebook.katana');");
        db.execSQL("insert into "+TABLE_NAME+" values('페북','com.facebook.katana');");

        db.execSQL("insert into "+TABLE_NAME+" values('카카오톡','com.kakao.talk');");
        db.execSQL("insert into "+TABLE_NAME+" values('카톡','com.kakao.talk');");
        db.execSQL("insert into "+TABLE_NAME+" values('라인','jp.naver.line.android');");
        db.execSQL("insert into "+TABLE_NAME+" values('인터넷','com.android.chrome');");
        db.execSQL("insert into "+TABLE_NAME+" values('크롬','com.android.chrome');");
        db.execSQL("insert into "+TABLE_NAME+" values('구글','com.android.chrome');");
        db.execSQL("insert into "+TABLE_NAME+" values('브라우저','com.android.chrome');");
        db.execSQL("insert into "+TABLE_NAME+" values('카메라','com.lge.camera');");
        db.execSQL("insert into "+TABLE_NAME+" values('앨범','com.android.gallery3d');");
        db.execSQL("insert into "+TABLE_NAME+" values('사진','com.android.gallery3d');");

        db.execSQL("insert into "+TABLE_NAME+" values('음악','com.lge.music');");
        db.execSQL("insert into "+TABLE_NAME+" values('mp3','com.lge.music');");
        db.execSQL("insert into "+TABLE_NAME+" values('뮤직','com.lge.music');");
        db.execSQL("insert into "+TABLE_NAME+" values('녹음','com.lge.voicerecorder')");
        db.execSQL("insert into "+TABLE_NAME+" values('음성','com.lge.voicerecorder')");
        db.execSQL("insert into "+TABLE_NAME+" values('설정','com.android.settings')");
        db.execSQL("insert into "+TABLE_NAME+" values('옵션','com.android.settings')");
        db.execSQL("insert into "+TABLE_NAME+" values('알람','com.lge.clock')");
        db.execSQL("insert into "+TABLE_NAME+" values('유튜브','com.google.android.youtube')");
        db.execSQL("insert into "+TABLE_NAME+" values('영상검색','com.google.android.youtube')");

        db.execSQL("insert into "+TABLE_NAME+" values('dmb','com.lge.tdmb')");
        db.execSQL("insert into "+TABLE_NAME+" values('tv','com.lge.tdmb')");
        db.execSQL("insert into "+TABLE_NAME+" values('텔레비전','com.lge.tdmb')");
        db.execSQL("insert into "+TABLE_NAME+" values('달력','com.android.calendar')");
        db.execSQL("insert into "+TABLE_NAME+" values('캘린더','com.android.calendar')");
        db.execSQL("insert into "+TABLE_NAME+" values('카렌다','com.android.calendar')");
        db.execSQL("insert into "+TABLE_NAME+" values('다음웹툰','net.daum.android.webtoon')");
        db.execSQL("insert into "+TABLE_NAME+" values('네이버웹툰','com.nhn.android.webtoon')");
        db.execSQL("insert into "+TABLE_NAME+" values('웹툰','com.nhn.android.webtoon')");
        db.execSQL("insert into "+TABLE_NAME+" values('네이버','com.nhn.android.naverwebengine')");

        db.execSQL("insert into "+TABLE_NAME+" values('계산기.','com.android.calculator2')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Toast.makeText(context,"onOpen() 메소드 호출", Toast.LENGTH_LONG).show();
    }

    public Cursor checkDB(){
        db = getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
    public boolean checkPackage(String args){
        db=getReadableDatabase();
        cursor =db.rawQuery("SELECT package from "+TABLE_NAME+" where name like '"+args+"'",null);
        while(cursor.moveToNext()){
            Log.d("likemilk",cursor.getString(0)+" 라는 결과가 나옴"+cursor.getCount());
            if(cursor.getCount()==1){
                if(context.getPackageManager().getLaunchIntentForPackage(cursor.getString(0)).getPackage()!=null) {
                    Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(cursor.getString(0));
                    context.startActivity(launchIntent);
                    tts.speak(args + "..  어플리케이션을 실행할게요", TextToSpeech.QUEUE_FLUSH, null);
                    allKillRunningApps();
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    void allKillRunningApps()	{
        // 시각장애인을위한 리소스 낭비 방지
        ActivityManager activity_manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> app_list = activity_manager.getRunningAppProcesses();
        for(int i=0; i<app_list.size(); i++)	{
            if(!app_list.get(i).processName.equals("com.example.yongjin.jarvis"))	{
                android.os.Process.sendSignal(app_list.get(i).pid, android.os.Process.SIGNAL_KILL);
                activity_manager.killBackgroundProcesses(app_list.get(i).processName);
            }
        }
        System.gc();
    }
}






































