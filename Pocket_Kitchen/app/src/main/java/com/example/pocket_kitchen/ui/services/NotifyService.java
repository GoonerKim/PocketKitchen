package com.example.pocket_kitchen.ui.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class NotifyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /** service code, 알림을 띄워줄 날짜, 알람 날짜를 intent로 받음.
         * 여기서 code와 alarm은 처음 받은 값을 계속 유지하지만 today값은 하루마다 +1을 한다.**/
        int code = intent.getIntExtra("code", 999);
        long today = intent.getLongExtra("today", 0);
        long alarm = intent.getLongExtra("alarm", 0);

        /** 아래 비교를 통해 알림을 생성 & 생성중지 함. **/
        SharedPreferences sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        boolean isAlarm = sp.getBoolean("alarm" + code, false);
        if (today <= alarm && isAlarm) {
            repeatedNotification(alarm, today, code);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void repeatedNotification(Long alarm, Long today, int requestCode) {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("requestCode", requestCode);
        alarmIntent.putExtra("today", today);
        alarmIntent.putExtra("alarm", alarm);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, today, pendingIntent);
        }
    }
}