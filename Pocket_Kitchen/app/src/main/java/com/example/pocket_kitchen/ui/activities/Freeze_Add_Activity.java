package com.example.pocket_kitchen.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Freeze;
import com.example.pocket_kitchen.ui.services.NotifyService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class Freeze_Add_Activity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Long now = System.currentTimeMillis();
    private String layer, isEdit;

    private IntentIntegrator qrScan;
    private Button freeze_QRcode_btn, freeze_Send;
    private AppCompatSpinner spinnerCount, spinnerLayer;
    private EditText freeze_Title, freeze_Memo;
    private TextView freezeExpirationStart, freeze_ExpirationDate;
    private Switch switchAlarm;

    private int alarmYear = 0, alarmMonth = 0, alarmDate = 0, service = 999;
    private boolean alarm = false;
    private Calendar cal = Calendar.getInstance();
    private long alarmTime = 0;
    private String alarmTimeStr;

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeze_add);

        sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        sped = sp.edit();

        freezeExpirationStart = findViewById(R.id.freeze_expiration_start);
        Intent intent = getIntent(); //intent에서 값을 넘겨받음
        try {
            isEdit = intent.getExtras().getString("edit");
            layer = intent.getExtras().getString("layer");
            if (!isEdit.equals("false")) {
                edit(Long.valueOf(isEdit), layer); //새로 추가하는게 아닐 시 edit func 호출
            } else {
                Date currentTime = Calendar.getInstance().getTime();
                String date_text = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
                freezeExpirationStart.setText(date_text);

                SharedPreferences sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
                service = sp.getInt("count", 0) + 1;
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("count", service);
                editor.apply(); //editor에 넣는 이유 = count가 겹치면 알림이 안생김
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "오류. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }

        Toolbar freeze_add_toolbar = findViewById(R.id.freeze_add_toolbar);

        setSupportActionBar(freeze_add_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        qrScan = new IntentIntegrator(this);

        freeze_QRcode_btn = (Button) findViewById(R.id.freeze_QRcode_btn);
        freeze_QRcode_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qrScan.setPrompt("Scanning...");
                qrScan.initiateScan();

            }
        });

        spinnerCount = findViewById(R.id.freeze_spinner_count);
        spinnerLayer = findViewById(R.id.freeze_spinner_layer);

        freeze_Title = findViewById(R.id.freeze_title);
        freeze_Memo = findViewById(R.id.freeze_memo);

        freeze_Title.requestFocus();

        switchAlarm = findViewById(R.id.freeze_switch_alarm);
        if (switchAlarm.isChecked()) switchAlarm.setText("ON");
        else switchAlarm.setText("OFF");
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) switchAlarm.setText("ON");
                else switchAlarm.setText("OFF");
            }
        });

        freezeExpirationStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(Freeze_Add_Activity.this,
                        start_listener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        freeze_ExpirationDate = findViewById(R.id.freeze_expiration_date);
        freeze_ExpirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(Freeze_Add_Activity.this,
                        listener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        freeze_Send = findViewById(R.id.freeze_send);
        freeze_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Freeze_Add_Activity.this);
                dialogBuilder.setMessage("작성된 내용을 저장합니다.");
                dialogBuilder.setTitle("저장");
                dialogBuilder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!freezeExpirationStart.getText().toString().equals("")) {
                            String isAlarm = "설정되지 않음.";
                            Long alarmTime = 0L;
                            if (switchAlarm.isChecked() && alarmDate != 0 && alarmYear != 0 && alarmMonth != 0) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(System.currentTimeMillis());
                                cal.set(Calendar.YEAR, alarmYear);
                                cal.set(Calendar.MONTH, alarmMonth);
                                cal.set(Calendar.DAY_OF_MONTH, alarmDate);
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);
                                cal.set(Calendar.MILLISECOND, 0);

                                String d = new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(cal.getTime());
                                Log.e("alarm date", d);

                                Calendar back = Calendar.getInstance();
                                back.setTimeInMillis(System.currentTimeMillis());
                                cal.set(Calendar.YEAR, alarmYear);
                                cal.set(Calendar.MONTH, alarmMonth);
                                back.set(Calendar.DAY_OF_MONTH, alarmDate - 3);
                                back.set(Calendar.HOUR_OF_DAY, 0);
                                back.set(Calendar.MINUTE, 0);
                                back.set(Calendar.SECOND, 0);
                                back.set(Calendar.MILLISECOND, 0);

                                if (back.getTimeInMillis() < System.currentTimeMillis())
                                    back.set(Calendar.DAY_OF_MONTH, alarmDate - 2);
                                if (back.getTimeInMillis() < System.currentTimeMillis())
                                    back.set(Calendar.DAY_OF_MONTH, alarmDate - 1);
                                if (back.getTimeInMillis() < System.currentTimeMillis())
                                    back.set(Calendar.DAY_OF_MONTH, alarmDate);
                                if (back.getTimeInMillis() < System.currentTimeMillis()) {

                                } else {
                                    alarmTime = cal.getTimeInMillis(); //저장할 알림 시간을 time value에 삽입
                                    Long today = back.getTimeInMillis();
                                    Intent start = new Intent(Freeze_Add_Activity.this, NotifyService.class);
                                    start.putExtra("code", service);
                                    start.putExtra("today", today);
                                    start.putExtra("alarm", alarmTime);
                                    startService(start);

                                    sped.putBoolean("alarm" + service, true);
                                    sped.apply();
                                }
                                String d2 = new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(back.getTime());
                                Log.e("alarm first date", d2);

                                isAlarm = "설정됨.";
                            }
                            if (!switchAlarm.isChecked()) {
                                sped.putBoolean("alarm" + service, false);
                                sped.apply();
                            }

                            SharedPreferences sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();

                            Freeze freeze = new Freeze();
                            freeze.setRegisteredDate(now);
                            freeze.setTitle(freeze_Title.getText().toString());
                            freeze.setCount(spinnerCount.getSelectedItem().toString());
                            freeze.setLayer(spinnerLayer.getSelectedItem().toString());
                            if (freeze_ExpirationDate.getText().toString().equals(""))
                                freeze.setExpiration("설정되지 않음.");
                            else freeze.setExpiration(freeze_ExpirationDate.getText().toString());
                            freeze.setExpirationStart(freezeExpirationStart.getText().toString());
                            freeze.setMemo(freeze_Memo.getText().toString());
                            freeze.setAlarm(alarmTime);
                            freeze.setService(service);
                            freeze.setAlarmTime(isAlarm);

                            if (!isEdit.equals("false")) {
                                if (!layer.equals(spinnerLayer.getSelectedItem().toString())) {
                                    firestore.collection("users")
                                            .document(Objects.requireNonNull(sp.getString("uid", "0")))
                                            .collection("freeze" + layer)
                                            .document(freeze.getRegisteredDate().toString()).delete();
                                }
                            }

                            firestore.collection("users").document(Objects.requireNonNull(sp.getString("uid", "0")))
                                    .collection("freeze" + spinnerLayer.getSelectedItem().toString())
                                    .document(now.toString())
                                    .set(freeze)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "저장됨.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "오류. 잠시 후 시도해보세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "가져온 날짜는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener start_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month;
            String date;

            if (0 <= monthOfYear && monthOfYear <= 8) month = "0" + (monthOfYear + 1);
            else month = String.valueOf(monthOfYear + 1);

            if (1 <= dayOfMonth && dayOfMonth <= 9) date = "0" + dayOfMonth;
            else date = String.valueOf(dayOfMonth);

            freezeExpirationStart.setText(String.format("%s-%s-%s", year, month, date));
        }
    };

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month;
            String date;

            if (0 <= monthOfYear && monthOfYear <= 8) month = "0" + (monthOfYear + 1);
            else month = String.valueOf(monthOfYear + 1);

            if (1 <= dayOfMonth && dayOfMonth <= 9) date = "0" + dayOfMonth;
            else date = String.valueOf(dayOfMonth);
            freeze_ExpirationDate.setText(String.format("%s-%s-%s", year, month, date));

            alarmYear = year;
            alarmMonth = monthOfYear;
            alarmDate = dayOfMonth;
        }
    };

    private void edit(Long registeredDate, String layer) { //어댑터에서 보낸 도큐멘트 이름을 넘겨받음
        ProgressDialog progressDialog = ProgressDialog.show(this, "로딩중", "로딩중입니다.");
        firestore.collection("users").document(Objects.requireNonNull(sp.getString("uid", "0")))
                .collection("freeze" + layer)//lists 컬렉션에서 도큐멘트를 찾아서 불러옴
                .document(String.valueOf(registeredDate))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Freeze freeze = task.getResult().toObject(Freeze.class); //불러온 도큐멘트를 list array에 넣고, 값을 하나하나 대입함
                            now = registeredDate;
                            freeze_Title.setText(freeze.getTitle());
                            freeze_Memo.setText(freeze.getMemo());
                            alarmTimeStr = freeze.getAlarmTime();
                            freezeExpirationStart.setText(freeze.getExpirationStart());
                            freeze_ExpirationDate.setText(freeze.getExpiration());
                            service = freeze.getService();

                            if (freeze.getAlarmTime().equals("설정됨.")) switchAlarm.setChecked(true);
                            if (switchAlarm.isChecked()) switchAlarm.setText("ON");
                            else switchAlarm.setText("OFF");

                            for (int i = 0; i < spinnerCount.getCount(); i++) {
                                if (spinnerCount.getItemAtPosition(i).toString().equals(freeze.getCount()))
                                    spinnerCount.setSelection(i);
                            }

                            for (int i = 0; i < spinnerLayer.getCount(); i++) {
                                if (spinnerLayer.getItemAtPosition(i).toString().equals(freeze.getLayer()))
                                    spinnerLayer.setSelection(i);
                            }

                            if (!freeze.getExpiration().equals("설정되지 않음.")) {
                                String year = freeze.getExpiration().substring(0, 4);
                                String month = freeze.getExpiration().substring(5, 7);
                                String date = freeze.getExpiration().substring(8, 10);
                                alarmYear = Integer.parseInt(year);
                                alarmMonth = Integer.parseInt(month) - 1;
                                alarmDate = Integer.parseInt(date);
                            }
                        }
                    }
                });
    }

    private String barcode2text(String barcode) { //바코드를 한글로 변환하는 함수
        String output = barcode;
        HashMap<String, String> kor = new HashMap<>(); //바코드 쌍을 등록해주세요
        kor.put("8809243590994", "게살 슬라이스");
        kor.put("8809516795705", "노세범 미네랄 파우더");
        kor.put("8806390557368", "아리따움 립커버 컬러 틴트");
        kor.put("8801067083386", "노란색 형광펜");
        kor.put("8801043031493", "포카칩");

        for (String key : kor.keySet()) {
            if (barcode.equals(key)) output = kor.get(key);
        }

        return output; //변환이 되면 한글, 쌍이 없다면 기본 바코드 출력
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                freeze_Title.setText(barcode2text(result.getContents())); //바코드를 변환하여 타이틀에 넣어줌
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}