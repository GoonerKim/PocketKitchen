package com.example.pocket_kitchen.ui.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Honey_tip_Notice;
import com.example.pocket_kitchen.datas.Notice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Honey_tip_add_notice extends AppCompatActivity {

    private Button honey_send, honey_sizeup, honey_sizedown, honey_color, honey_font;
    private TextView honey_content, honey_name;
    private EditText honey_title;
    private float honey_titlesize;
    private float honey_textsize;
    private float honey_namesize;
    private String honey_titlecolor = "Black";
    private String honey_textcolor = "Black";
    private String honey_namecolor = "Black";
    private String honey_titlefont = "sans";
    private String honey_textfont = "sans";
    private String honey_namefont = "sans";
    private String honey_pickedtext = "title";

    private PopupWindow mDropdown = null;
    private LayoutInflater mInflater;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Long now = System.currentTimeMillis();

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.honey_tip_add_notice);

        sp = getSharedPreferences("pocket_kitchen", MODE_PRIVATE);
        sped = sp.edit();

        honey_sizeup = findViewById(R.id.honey_sizeup_button);
        honey_sizedown = findViewById(R.id.honey_sizedown_button);
        honey_color = findViewById(R.id.honey_color_button);
        honey_send = findViewById(R.id.honey_send);
        honey_font = findViewById(R.id.honey_font_button);

        honey_content = findViewById(R.id.honey_content);
        honey_name = findViewById(R.id.honey_name);
        honey_title = findViewById(R.id.honey_title);

        honey_textsize = 20;
        honey_content.setTextSize(honey_textsize);

        honey_titlesize = 20;
        honey_title.setTextSize(honey_titlesize);

        //  namesize = 18;
        //  name.setTextSize(namesize);

        honey_title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                honey_pickedtext = "title";
                return false;
            }
        });

      /*name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pickedtext = "name";
                return false;
            }
        });*/

        honey_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                honey_pickedtext = "content";
                return false;
            }
        });

        honey_sizeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (honey_pickedtext) {
                    case "content":
                        honey_textsize += 1;
                        honey_content.setTextSize(honey_textsize);
                        break;
                    case "title":
                        honey_titlesize += 1;
                        honey_title.setTextSize(honey_titlesize);
                        break;
                    case "name":
                        honey_namesize += 1;
                        honey_name.setTextSize(honey_namesize);
                        break;
                }
            }
        });

        honey_sizedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (honey_pickedtext) {
                    case "content":
                        honey_textsize -= 1;
                        honey_content.setTextSize(honey_textsize);
                        break;
                    case "title":
                        honey_titlesize -= 1;
                        honey_title.setTextSize(honey_titlesize);
                        break;
                    case "name":
                        honey_namesize -= 1;
                        honey_name.setTextSize(honey_namesize);
                        break;
                }
            }
        });

        honey_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String date = sdf.format(new Date(System.currentTimeMillis()));

                SimpleDateFormat sdff = new SimpleDateFormat("kk:mm", Locale.KOREA);
                String time = sdff.format(new Date(System.currentTimeMillis()));

                Notice notice = new Notice();
                notice.setRegisteredDate(now);
                notice.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                notice.setDay(date);
                notice.setTime(time);
                notice.setName(sp.getString("name", "unKnown"));
                notice.setTitle(honey_title.getText().toString());
                notice.setContent(honey_content.getText().toString());
                notice.setTitleSize(honey_titlesize);
                notice.setTitleColor(honey_titlecolor);
                notice.setTitleFont(honey_titlefont);
                notice.setTextSize(honey_textsize);
                notice.setTextColor(honey_textcolor);
                notice.setTextFont(honey_textfont);

                final ProgressDialog progressDialog = ProgressDialog.show(Honey_tip_add_notice.this, "저장중", "저장중입니다.");
                firestore.collection("notice2")
                        .document(now.toString())
                        .set(notice)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "저장됨.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "오류. 잠시 후 시도해보세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        honey_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //PopupMenu객체 생성.
//                //생성자함수의 첫번재 파라미터 : Context
//                //생성자함수의 두번째 파라미터 : Popup Menu를 붙일 anchor 뷰
//                PopupMenu popup= new PopupMenu(v.getContext(), v);//v는 클릭된 뷰를 의미
//
//                //Popup Menu에 들어갈 MenuItem 추가.
//                //이전 포스트의 컨텍스트 메뉴(Context menu)처럼 xml 메뉴 리소스 사용
//                //첫번재 파라미터 : res폴더>>menu폴더>>my_recipe_mainmenu.xml파일 리소스
//                //두번재 파라미터 : Menu 객체->Popup Menu 객체로 부터 Menu 객체 얻어오기
//                getMenuInflater().inflate(R.menu.my_recipe_mainmenu, popup.getMenu());
//
//                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 설정
//                popup.setOnMenuItemClickListener(listener);
//
//                setForceShowIcon(popup);
//
////                popup.show();//Popup Menu 보이기
//
////                initiatePopupWindow();
                //클릭시 팝업 윈도우 생성
                final PopupWindow popup1 = new PopupWindow(v);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //팝업으로 띄울 커스텀뷰를 설정하고
                final View view = inflater.inflate(R.layout.honey_color_menu, null);
                popup1.setContentView(view);
                //팝업의 크기 설정
                popup1.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //팝업 뷰 터치 되도록
                popup1.setTouchable(true);
                //팝업 뷰 포커스도 주고
                popup1.setFocusable(true);
                //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                popup1.setOutsideTouchable(true);

                ImageView black = (ImageView) view.findViewById(R.id.honey_btn_black);
                black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (honey_pickedtext) {
                            case "content":
                                honey_textcolor = "Black";
                                honey_content.setTextColor(Color.BLACK);
                                break;
                            case "title":
                                honey_titlecolor = "Black";
                                honey_title.setTextColor(Color.BLACK);
                                break;
                            case "name":
                                honey_namecolor = "Black";
                                honey_name.setTextColor(Color.BLACK);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                ImageView red = (ImageView) view.findViewById(R.id.honey_btn_red);
                red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (honey_pickedtext) {
                            case "content":
                                honey_textcolor = "Red";
                                honey_content.setTextColor(Color.RED);
                                break;
                            case "title":
                                honey_titlecolor = "Red";
                                honey_title.setTextColor(Color.RED);
                                break;
                            case "name":
                                honey_namecolor = "Red";
                                honey_name.setTextColor(Color.RED);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                ImageView blue = (ImageView) view.findViewById(R.id.honey_btn_blue);
                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (honey_pickedtext) {
                            case "content":
                                honey_textcolor = "Blue";
                                honey_content.setTextColor(Color.BLUE);
                                break;
                            case "title":
                                honey_titlecolor = "Blue";
                                honey_title.setTextColor(Color.BLUE);
                                break;
                            case "name":
                                honey_namecolor = "Blue";
                                honey_name.setTextColor(Color.BLUE);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                ImageView green = (ImageView) view.findViewById(R.id.honey_btn_green);
                green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (honey_pickedtext) {
                            case "content":
                                honey_textcolor = "Green";
                                honey_content.setTextColor(Color.GREEN);
                                break;
                            case "title":
                                honey_titlecolor = "Green";
                                honey_title.setTextColor(Color.GREEN);
                                break;
                            case "name":
                                honey_namecolor = "Green";
                                honey_name.setTextColor(Color.GREEN);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                //인자로 넘겨준 v 아래로 보여주기
                popup1.showAsDropDown(v);
            }
        });

        honey_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupMenu객체 생성.
                //생성자함수의 첫번재 파라미터 : Context
                //생성자함수의 두번째 파라미터 : Popup Menu를 붙일 anchor 뷰
                PopupMenu popup = new PopupMenu(v.getContext(), v);//v는 클릭된 뷰를 의미

                //Popup Menu에 들어갈 MenuItem 추가.
                //이전 포스트의 컨텍스트 메뉴(Context menu)처럼 xml 메뉴 리소스 사용
                //첫번재 파라미터 : res폴더>>menu폴더>>my_recipe_mainmenu.xml파일 리소스
                //두번재 파라미터 : Menu 객체->Popup Menu 객체로 부터 Menu 객체 얻어오기
                getMenuInflater().inflate(R.menu.honey_tip_fontmenu, popup.getMenu());

                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 설정
                popup.setOnMenuItemClickListener(listener);

                popup.show();//Popup Menu 보이기
            }
        });

        Intent intent = getIntent(); //intent에서 값을 넘겨받음
        try {
            String isEdit = intent.getExtras().getString("edit");
            if (!isEdit.equals("false")) edit(Long.valueOf(isEdit)); //새로 추가하는게 아닐 시 edit func 호출
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "오류. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 객체 생성
    //import android.widget.PopupMenu.OnMenuItemClickListener 가 되어있어야 합니다.
    //OnMenuItemClickListener 클래스는 다른 패키지에도 많기 때문에 PopupMenu에 반응하는 패키지를 임포트하셔야 합니다.
    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // TODO Auto-generated method stub
            switch (item.getItemId()) {//눌러진 MenuItem의 My_Recipe_Item Id를 얻어와 식별
                case R.id.black:
                    switch (honey_pickedtext) {
                        case "content":
                            honey_textcolor = "Black";
                            honey_content.setTextColor(Color.BLACK);
                            break;
                        case "title":
                            honey_titlecolor = "Black";
                            honey_title.setTextColor(Color.BLACK);
                            break;
                        case "name":
                            honey_namecolor = "Black";
                            honey_name.setTextColor(Color.BLACK);
                            break;
                    }
                    break;

                case R.id.red:
                    switch (honey_pickedtext) {
                        case "content":
                            honey_textcolor = "Red";
                            honey_content.setTextColor(Color.RED);
                            break;
                        case "title":
                            honey_titlecolor = "Red";
                            honey_title.setTextColor(Color.RED);
                            break;
                        case "name":
                            honey_namecolor = "Red";
                            honey_name.setTextColor(Color.RED);
                            break;
                    }

                    break;

                case R.id.blue:
                    switch (honey_pickedtext) {
                        case "content":
                            honey_textcolor = "Blue";
                            honey_content.setTextColor(Color.BLUE);
                            break;
                        case "title":
                            honey_titlecolor = "Blue";
                            honey_title.setTextColor(Color.BLUE);
                            break;
                        case "name":
                            honey_namecolor = "Blue";
                            honey_name.setTextColor(Color.BLUE);
                            break;
                    }

                    break;

                case R.id.green:
                    switch (honey_pickedtext) {
                        case "content":
                            honey_textcolor = "Green";
                            honey_content.setTextColor(Color.GREEN);
                            break;
                        case "title":
                            honey_titlecolor = "Green";
                            honey_title.setTextColor(Color.GREEN);
                            break;
                        case "name":
                            honey_namecolor = "Green";
                            honey_name.setTextColor(Color.GREEN);
                            break;
                    }

                    break;

                case R.id.honey_sans:
                    switch (honey_pickedtext) {
                        case "content":
                            honey_textfont = "sans";
                            honey_content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                            break;
                        case "title":
                            honey_titlefont = "sans";
                            honey_title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                            honey_title.setTypeface(null, Typeface.BOLD);
                            break;
                        case "name":
                            honey_namefont = "sans";
                            honey_name.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                            break;
                    }

                    break;

                case R.id.honey_serif:

                    Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");

                    switch (honey_pickedtext) {
                        case "content":
                            honey_textfont = "serif";
                            honey_content.setTypeface(typeface1);
                            break;
                        case "title":
                            honey_titlefont = "serif";
                            honey_title.setTypeface(typeface1);
                            break;
                        case "name":
                            honey_namefont = "serif";
                            honey_name.setTypeface(typeface1);
                            break;
                    }
                    break;

                case R.id.honey_casual:
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "font/nanumpen.ttf");

                    switch (honey_pickedtext) {
                        case "content":
                            honey_textfont = "casual";
                            honey_content.setTypeface(typeface);
                            break;
                        case "title":
                            honey_titlefont = "casual";
                            honey_title.setTypeface(typeface);
                            break;
                        case "name":
                            honey_namefont = "casual";
                            honey_name.setTypeface(typeface);
                            break;
                    }
                    break;
            }

            return false;
        }
    };

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private PopupWindow initiatePopupWindow() {

        try {

            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.honey_color_menu, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
//            final TextView itema = (TextView) layout.findViewById(R.id.ItemA);
//
//
//            final TextView itemb = (TextView) layout.findViewById(R.id.ItemB);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT, true);
            Drawable background = getResources().getDrawable(android.R.drawable.dialog_holo_light_frame);
            mDropdown.setBackgroundDrawable(background);
            mDropdown.showAsDropDown(honey_color, 5, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;

    }

    private void edit(Long registeredDate) { //어댑터에서 보낸 도큐멘트 이름을 넘겨받음
        final ProgressDialog progressDialog = ProgressDialog.show(this, "로딩중", "로딩중입니다.");
        firestore.collection("notice2") //lists 컬렉션에서 도큐멘트를 찾아서 불러옴
                .document(String.valueOf(registeredDate))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Notice notice = task.getResult().toObject(Honey_tip_Notice.class); //불러온 도큐멘트를 list array에 넣고, 값을 하나하나 대입함
                            now = registeredDate;

                            honey_title.setText(notice.getTitle());
                            honey_title.setTextSize(notice.getTitleSize());
                            honey_titlesize = notice.getTitleSize();
                            honey_titlefont = notice.getTitleFont();
                            honey_titlecolor = notice.getTitleColor();

                            honey_content.setText(notice.getContent());
                            honey_content.setTextSize(notice.getTextSize());
                            honey_textsize = notice.getTextSize();
                            honey_textfont = notice.getTextFont();
                            honey_textcolor = notice.getTextColor();

                            switch (notice.getTitleColor()) {
                                case "Black":
                                    honey_title.setTextColor(Color.BLACK);
                                    break;
                                case "Red":
                                    honey_title.setTextColor(Color.RED);
                                    break;
                                case "Blue":
                                    honey_title.setTextColor(Color.BLUE);
                                    break;
                                case "Green":
                                    honey_title.setTextColor(Color.GREEN);
                                    break;
                            }

                            switch (notice.getTextColor()) {
                                case "Black":
                                    honey_content.setTextColor(Color.BLACK);
                                    break;
                                case "Red":
                                    honey_content.setTextColor(Color.RED);
                                    break;
                                case "Blue":
                                    honey_content.setTextColor(Color.BLUE);
                                    break;
                                case "Green":
                                    honey_content.setTextColor(Color.GREEN);
                                    break;
                            }

                            Typeface typeface;

                            switch (notice.getTitleFont()) {
                                case "sans":
                                    honey_title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                    honey_title.setTypeface(null, Typeface.BOLD);
                                    break;
                                case "serif":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/batang.ttc");
                                    honey_title.setTypeface(typeface);
                                    break;
                                case "casual":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nanumpen.ttf");
                                    honey_title.setTypeface(typeface);
                                    break;
                            }

                            switch (notice.getTextFont()) {
                                case "sans":
                                    honey_content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                    break;
                                case "serif":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/batang.ttc");
                                    honey_content.setTypeface(typeface);
                                    break;
                                case "casual":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nanumpen.ttf");
                                    honey_content.setTypeface(typeface);
                                    break;
                            }
                        }
                    }
                });
    }
}