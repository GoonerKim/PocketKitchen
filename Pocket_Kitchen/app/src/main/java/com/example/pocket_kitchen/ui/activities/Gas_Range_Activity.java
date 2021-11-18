package com.example.pocket_kitchen.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.pocket_kitchen.R;

public class Gas_Range_Activity extends AppCompatActivity {

    Button start, pause, reset;
    Handler handler;
    NumberPicker h_picker, m_picker, s_picker;
    private long hour, min, sec;
    private long startTime = 0;
    private long count = startTime/1000;
    private boolean timerRunning = false;
    private boolean timerPause = false;

    String[] display = new String[60];

    private TextView countTxt ;
    private CountDownTimer countDownTimer;

    private SoundPool soundPool;
    int soundEffect;

    String[] ListElements = new String[] {  };
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas__range_);

        Toolbar gas_range_toolbar = findViewById(R.id.gas_range_toolbar);

        setSupportActionBar(gas_range_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        countTxt = (TextView) findViewById(R.id.timerView);

        start = (Button)findViewById(R.id.timer_start_btn);
        pause = (Button)findViewById(R.id.timer_pause_btn);
        reset = (Button)findViewById(R.id.timer_reset_btn);

        for (int i=0; i<60; i++) {
            display[i] = String.format("%02d", i);
        }

        PickerListener listener = new PickerListener();

        h_picker = (NumberPicker)findViewById(R.id.h_picker);

        h_picker.setMinValue(0);
        h_picker.setMaxValue(59);
        h_picker.setValue(0);

        h_picker.setOnScrollListener(listener);
        h_picker.setOnValueChangedListener(listener);
        h_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        m_picker = (NumberPicker)findViewById(R.id.m_picker);

        m_picker.setMinValue(0);
        m_picker.setMaxValue(59);
        m_picker.setValue(0);

        m_picker.setOnScrollListener(listener);
        m_picker.setOnValueChangedListener(listener);
        m_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        s_picker = (NumberPicker)findViewById(R.id.s_picker);

        s_picker.setMinValue(0);
        s_picker.setMaxValue(59);
        s_picker.setValue(0);

        s_picker.setOnScrollListener(listener);
        s_picker.setOnValueChangedListener(listener);
        s_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        h_picker.setDisplayedValues(display);
        m_picker.setDisplayedValues(display);
        s_picker.setDisplayedValues(display);

        h_picker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                h_picker.setDescendantFocusability(NumberPicker.FOCUS_AFTER_DESCENDANTS);
                return false;
            }
        });

        m_picker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                m_picker.setDescendantFocusability(NumberPicker.FOCUS_AFTER_DESCENDANTS);
                return false;
            }
        });

        s_picker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                s_picker.setDescendantFocusability(NumberPicker.FOCUS_AFTER_DESCENDANTS);
                return false;
            }
        });

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        setListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (timerRunning == false) {
                    switch(position) {
                        case 0:
                            count = 600;
                            setTime(count);
                            h_picker.setValue(0);
                            m_picker.setValue(10);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 1:
                            count = 900;
                            setTime(count);
                            h_picker.setValue(0);
                            m_picker.setValue(15);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 2:
                            count = 180;
                            setTime(count);
                            h_picker.setValue(0);
                            m_picker.setValue(3);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 3:
                            count = 300;
                            setTime(count);
                            h_picker.setValue(0);
                            m_picker.setValue(5);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 4:
                            count = 3600;
                            setTime(count);
                            h_picker.setValue(1);
                            m_picker.setValue(0);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 5:
                            count = 900;
                            setTime(count);
                            h_picker.setValue(0);
                            m_picker.setValue(15);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 6:
                            count = 3600;
                            setTime(count);
                            h_picker.setValue(1);
                            m_picker.setValue(0);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 7:
                            count = 1200;
                            setTime(count);
                            h_picker.setValue(0);
                            m_picker.setValue(20);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        case 8:
                            count = 3600;
                            setTime(count);
                            h_picker.setValue(1);
                            m_picker.setValue(0);
                            s_picker.setValue(0);
                            countTxt.setVisibility(view.INVISIBLE);
                            h_picker.setVisibility(view.VISIBLE);
                            m_picker.setVisibility(view.VISIBLE);
                            s_picker.setVisibility(view.VISIBLE);
                            updateCountDownText();
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTime == 0) {
                    Toast.makeText(Gas_Range_Activity.this, "타이머를 설정해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    countTxt.setVisibility(v.VISIBLE);
                    h_picker.setVisibility(v.INVISIBLE);
                    m_picker.setVisibility(v.INVISIBLE);
                    s_picker.setVisibility(v.INVISIBLE);
                    startTimer();
                }
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                resetTimer();
                countTxt.setVisibility(v.INVISIBLE);
                h_picker.setVisibility(v.VISIBLE);
                m_picker.setVisibility(v.VISIBLE);
                s_picker.setVisibility(v.VISIBLE);
            }
        });

        handler = new Handler() ;
    }

    public void update() {
        hour = h_picker.getValue();
        min = m_picker.getValue();
        sec = s_picker.getValue();
        count = (hour*3600)+(min*60)+sec;
        setTime(count);
    }

    public class PickerListener implements NumberPicker.OnScrollListener, NumberPicker.OnValueChangeListener {
        private int scrollState=0;
        @Override
        public void onScrollStateChange(NumberPicker view, int scrollState) {
            this.scrollState=scrollState;
            if (scrollState==SCROLL_STATE_IDLE){
                update();
            }
        }
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (scrollState==0){
                update();
            }
        }
    }

    private void setListView() {
        String[] stringList = {"  삶은 계란(반숙)", "  삶은 계란(완숙)", "  라면", "  멸치/다시마 육수", "  수육", "  냄비밥", "  삼계탕", "  고구마/감자", "  옥수수"};
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        listView = (ListView)findViewById(R.id.cook);
        listView.setAdapter(listAdapter);
    }

    public void setTime(long seconds) {
        startTime = seconds*1000;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(startTime, 1000) {
            public void onTick(long millisUntilFinished) {
                count--;
                updateCountDownText();
            }
            public void onFinish() {
                countTxt.setText(String.valueOf("Finish"));
                resetTimer();
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(3000);
                soundPool.play(soundEffect,1,1,1,0,1);
            }
        }.start();
        timerRunning = true;
    }
    public void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }
    public void resetTimer() {
        startTime = 0;
        count = 0;
        timerRunning = false;
        h_picker.setValue(0);
        m_picker.setValue(0);
        s_picker.setValue(0);
    }

    public void updateCountDownText() {
        countTxt.setText(String.format("%02d : %02d : %02d",
                TimeUnit.SECONDS.toHours(count)%60,
                TimeUnit.SECONDS.toMinutes(count)%60,
                (count)%60));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(8).build();
        }
        else {
            soundPool = new SoundPool(8, AudioManager.STREAM_NOTIFICATION, 0);
        }

        soundEffect = soundPool.load(this, R.raw.timer_sound, 1 );

    }

    @Override
    protected void onStop() {
        super.onStop();
        soundPool.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
    }

}