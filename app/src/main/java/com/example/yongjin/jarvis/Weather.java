package com.example.yongjin.jarvis;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by YongJin on 2015-06-22.
 */
public class Weather {
    // 자신의 위치와 날씨를 알려줌
    Context context;
    TextToSpeech tts;
    String JSONDocument;
    JSONHandler handler;
    Double longti,lati;
    public String location="";
    Geocoder gc;
    String result;
    public Weather(Context context,TextToSpeech tts){
        this.context = context;
        this.tts = tts;
        this.location=getLocation();
    }

    public void getWeather(){
        JSONThread t = new JSONThread();
        handler = new JSONHandler();
        t.start();

    }
    class JSONThread extends Thread{
        @Override
        public void run() {
            try{
                String key="666465479c7c0dc8d3d1278373feb";
                String urlAddr="http://api.worldweatheronline.com/free/v2/weather.ashx?format=json&num_of_days=1&key="+key+"&q=seoul";

                URL url = new URL(urlAddr);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String str;
                StringBuffer buf = new StringBuffer();

                while((str=br.readLine())!=null){
                    buf.append(str);
                }

                String rec_data=buf.toString();

                JSONDocument = rec_data;
                JSONObject json_object = new JSONObject(rec_data);


                for(int i=0;i<=json_object.length();i++){
                    JSONObject obj = json_object.getJSONObject("data");
                    for(int j=0; j<=obj.length();j++){
                        JSONArray ary = obj.getJSONArray("current_condition");
                        JSONArray ary3 = obj.getJSONArray("request");

                        JSONObject obj1=  ary.getJSONObject(0);
                        JSONArray ary1 = obj1.getJSONArray("weatherDesc");
                        JSONObject obj2 = ary1.getJSONObject(0);


                        String degree = obj1.getString("temp_C");
                        String weather = obj2.getString("value");
                        if(weather.equals("Sunny")){
                            weather ="맑음";
                        }else if(weather.equals("Partly Cloudy")){
                            weather ="구름 조금";
                        }else if(weather.equals("Light rain shower")) {
                            weather = "맑음.. 비.. 약간..";
                        }else if(weather.equals("Light Rain")) {
                            weather = "비.. 약간..";
                        }else if(weather.equals("Overcast")) {
                            weather = "맑음.. 비.. 약간..";
                        }else if(weather.equals("Patchy rain nearby")) {
                            weather = "비";
                        }else if(weather.equals("Light Rain")) {
                            weather = "맑음.. 비.. 약간..";
                        }
                        result="현재 온도 .."+degree+"도 ..."+"현재 날씨 .."+weather+".. 입니다.";
                    }
                }

                handler.sendEmptyMessage(0);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    public void getWhere(){
        LocationManager manager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Double latitude =new Double(0);
        Double longitude =new Double(0);
        //위치 정보를 받을 위치 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime=10000;     //10초마다 위치정보 전달.
        float minDistance = 0;  //이동 거리 지정

        //GPS를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);
        //네트워크를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener
        );
        try {
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        manager.removeUpdates(gpsListener);

        try{
            List<Address> addresses=gc.getFromLocation(latitude,longitude,1);
            ArrayList<Address> addrList = new ArrayList<Address>();
            Iterator<Address> iterator = addresses.iterator();
            if(addresses.size()>0){
                while(iterator.hasNext()){
                    addrList.add(iterator.next());
                }
                tts.speak("현재 주인님의 위치는 .."+addrList.get(0).getAddressLine(0)+".. 입니다.", TextToSpeech.QUEUE_FLUSH, null);
            }
        }catch(Exception ex){

            ex.printStackTrace();
        }
    }
    public String getWhere2(){
        LocationManager manager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Double latitude =new Double(0);
        Double longitude =new Double(0);
        //위치 정보를 받을 위치 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime=10000;     //10초마다 위치정보 전달.
        float minDistance = 0;  //이동 거리 지정

        //GPS를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);
        //네트워크를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener
        );
        try {
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        manager.removeUpdates(gpsListener);

        try{
            List<Address> addresses=gc.getFromLocation(latitude,longitude,1);
            ArrayList<Address> addrList = new ArrayList<Address>();
            Iterator<Address> iterator = addresses.iterator();
            if(addresses.size()>0){
                while(iterator.hasNext()){
                    addrList.add(iterator.next());
                }
            }
            return "도와주시겠어요? 저는.."+addrList.get(0).getAddressLine(0)+".. 에 있습니다.";
        }catch(Exception ex){
            ex.printStackTrace();
            return "도와주시겠어요? 곤란한 상황에 놓여있어요 도와주세요.";
        }
    }
    class JSONHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                tts.speak(result, TextToSpeech.QUEUE_FLUSH, null);

            }
        }
    }

    private String getLocation() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gc=new Geocoder(context, Locale.KOREAN);
        // 위치 정보를 받을 위치 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;//GPS정보 전달 시간 지정 - 10초마다 위치정보 전달
        float minDistance = 0;//이동거리 지정
        Double latitude = new Double(0L);
        Double longtitude= new Double(0L);
        lati = latitude;
        longti = longtitude;
        // GPS를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 네트워크(기지국)를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
        try {
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longtitude = lastLocation.getLongitude();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        manager.removeUpdates(gpsListener);

        return latitude+","+longtitude;
    }

    /**
     * 위치 리스너 클래스 정의
     */

    private class GPSListener implements LocationListener {

        /**
         * 위치 정보가 확인(수신)될 때마다 자동 호출되는 메소드
         */

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();//위도
            Double longitude = location.getLongitude();//경도
       }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}
