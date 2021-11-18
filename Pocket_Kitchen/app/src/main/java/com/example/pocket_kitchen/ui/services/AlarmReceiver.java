package com.example.pocket_kitchen.ui.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.pocket_kitchen.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        int requestCode = bundle.getInt("requestCode");
        long today = bundle.getLong("today");
        long alarm = bundle.getLong("alarm");

        SharedPreferences sp = context.getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        boolean isAlarm = sp.getBoolean("alarm" + requestCode, false);
        if (isAlarm) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("냉장실 알람");
            if (today == alarm) builder.setContentText("유통기한이 마감되었습니다.");
            else builder.setContentText("유통기한 마감이 얼마 남지 않았습니다.");

            builder.setColor(Color.WHITE);
            // 사용자가 탭을 클릭하면 자동 제거
            builder.setAutoCancel(true);

            // 알림 표시
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            notificationManager.notify(requestCode, builder.build());
        }

        /** 아래 부분은 알림이 울렸을 경우에 대한 로직임.
         * 만약 알람이 울렸을 경우 울린 날짜 +1을 해서 알림을 다시 생성해줌. **/
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(today);
        String d = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(cal.getTime());
        Log.e("webnautes", d);
        cal.add(Calendar.DATE, 1);

        Intent start = new Intent(context, NotifyService.class);
        start.putExtra("code", requestCode);
        start.putExtra("today", cal.getTimeInMillis());
        start.putExtra("alarm", alarm);
        context.startService(start);

        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(cal.getTime());
        Log.e("webnautes", date_text);
    }
}