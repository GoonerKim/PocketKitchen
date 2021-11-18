package com.example.pocket_kitchen.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostAddActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Long now = System.currentTimeMillis();
    private String page;

    private EditText editTitle, editContent;
    private float titlesize = 20;
    private float textsize = 20;
    private String titlecolor = "Black";
    private String titlefont = "sans";
    private String textcolor = "Black";
    private String textfont = "sans";
    private String pickedtext = "title";

    @SuppressLint("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        Button btnSizeup = findViewById(R.id.btn_sizeup);
        Button btnSizeDown = findViewById(R.id.btn_sizedown);
        Button btnColor = findViewById(R.id.btn_color);
        Button btnFont = findViewById(R.id.btn_font);
        Button btnSave = findViewById(R.id.btn_save);

        editTitle.setTextSize(titlesize);
        editContent.setTextSize(textsize);

        /** 게시 글을 추가할 시에 편집창을 하나로 만들었음
         * notice와 honey 모두 여기로 연결됨. 따라서 편집을 요청한 곳이 무슨 fragment를 구별하기 위한 page parameter가 추가되었음**/
        Intent intent = getIntent(); //intent에서 값을 넘겨받음
        try {
            String isEdit = intent.getExtras().getString("edit");
            page = intent.getExtras().getString("page");
            if (!isEdit.equals("false"))
                edit(Long.valueOf(isEdit)); //새로 추가하는게 아닐 시 edit func 호출
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "오류. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }

        editTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pickedtext = "title";
                return false;
            }
        });

        editContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pickedtext = "content";
                return false;
            }
        });

        btnSizeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pickedtext) {
                    case "title":
                        titlesize += 1;
                        editTitle.setTextSize(titlesize);
                        break;
                    case "content":
                        textsize += 1;
                        editContent.setTextSize(textsize);
                        break;
                }
            }
        });

        btnSizeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pickedtext) {
                    case "title":
                        titlesize -= 1;
                        editTitle.setTextSize(titlesize);
                        break;
                    case "content":
                        textsize -= 1;
                        editContent.setTextSize(textsize);
                        break;
                }
            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭시 팝업 윈도우 생성
                final PopupWindow popup1 = new PopupWindow(v);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //팝업으로 띄울 커스텀뷰를 설정하고
                final View view = inflater.inflate(R.layout.post_color_menu, null);
                popup1.setContentView(view);
                //팝업의 크기 설정
                popup1.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //팝업 뷰 터치 되도록
                popup1.setTouchable(true);
                //팝업 뷰 포커스도 주고
                popup1.setFocusable(true);
                //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                popup1.setOutsideTouchable(true);

                ImageView black = (ImageView) view.findViewById(R.id.btn_black);
                black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (pickedtext) {
                            case "title":
                                titlecolor = "Black";
                                editTitle.setTextColor(Color.BLACK);
                                break;
                            case "content":
                                textcolor = "Black";
                                editContent.setTextColor(Color.BLACK);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                ImageView red = (ImageView) view.findViewById(R.id.btn_red);
                red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (pickedtext) {
                            case "title":
                                titlecolor = "Red";
                                editTitle.setTextColor(Color.RED);
                                break;
                            case "content":
                                textcolor = "Red";
                                editContent.setTextColor(Color.RED);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                ImageView blue = (ImageView) view.findViewById(R.id.btn_blue);
                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (pickedtext) {
                            case "title":
                                titlecolor = "Blue";
                                editTitle.setTextColor(Color.BLUE);
                                break;
                            case "content":
                                textcolor = "Blue";
                                editContent.setTextColor(Color.BLUE);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                ImageView green = (ImageView) view.findViewById(R.id.btn_green);
                green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (pickedtext) {
                            case "title":
                                titlecolor = "Green";
                                editTitle.setTextColor(Color.GREEN);
                                break;
                            case "content":
                                textcolor = "Green";
                                editContent.setTextColor(Color.GREEN);
                                break;
                        }
                        popup1.dismiss();
                    }
                });
                //인자로 넘겨준 v 아래로 보여주기
                popup1.showAsDropDown(v);
            }
        });

        btnFont.setOnClickListener(new View.OnClickListener() {
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
                getMenuInflater().inflate(R.menu.my_recipe_fontmenu, popup.getMenu());

                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 설정
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {//눌러진 MenuItem의 My_Recipe_Item Id를 얻어와 식별
                            case R.id.black:
                                switch (pickedtext) {
                                    case "title":
                                        titlecolor = "Black";
                                        editTitle.setTextColor(Color.BLACK);
                                        break;
                                    case "content":
                                        textcolor = "Black";
                                        editContent.setTextColor(Color.BLACK);
                                        break;
                                }
                                break;

                            case R.id.red:
                                switch (pickedtext) {
                                    case "title":
                                        titlecolor = "Red";
                                        editTitle.setTextColor(Color.RED);
                                        break;
                                    case "content":
                                        textcolor = "Red";
                                        editContent.setTextColor(Color.RED);
                                        break;
                                }

                                break;

                            case R.id.blue:
                                switch (pickedtext) {
                                    case "title":
                                        titlecolor = "Blue";
                                        editTitle.setTextColor(Color.BLUE);
                                        break;
                                    case "content":
                                        textcolor = "Blue";
                                        editContent.setTextColor(Color.BLUE);
                                        break;
                                }

                                break;

                            case R.id.green:
                                switch (pickedtext) {
                                    case "title":
                                        titlecolor = "Green";
                                        editTitle.setTextColor(Color.GREEN);
                                        break;
                                    case "content":
                                        textcolor = "Green";
                                        editContent.setTextColor(Color.GREEN);
                                        break;
                                }

                                break;

                            case R.id.sans:
                                switch (pickedtext) {
                                    case "title":
                                        titlefont = "sans";
                                        editTitle.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                        editTitle.setTypeface(null, Typeface.BOLD);
                                        break;
                                    case "content":
                                        textfont = "sans";
                                        editContent.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                        break;
                                }

                                break;

                            case R.id.serif:

                                Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");

                                switch (pickedtext) {
                                    case "title":
                                        titlefont = "serif";
                                        editTitle.setTypeface(typeface1);
                                        break;
                                    case "content":
                                        textfont = "serif";
                                        editContent.setTypeface(typeface1);
                                        break;
                                }
                                break;

                            case R.id.casual:
                                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");

                                switch (pickedtext) {
                                    case "title":
                                        titlefont = "casual";
                                        editTitle.setTypeface(typeface);
                                        break;
                                    case "content":
                                        textfont = "casual";
                                        editContent.setTypeface(typeface);
                                        break;
                                }
                                break;
                        }
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sp = getSharedPreferences("pocket_kitchen", MODE_PRIVATE);

            @Override
            public void onClick(View view) {
                Post post = new Post();
                post.setRegisteredDate(now);
                post.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                post.setName(sp.getString("name", "unKnown"));
                post.setTitle(editTitle.getText().toString());
                post.setContent(editContent.getText().toString());
                post.setTitleSize(titlesize);
                post.setTitleColor(titlecolor);
                post.setTitleFont(titlefont);
                post.setTextSize(textsize);
                post.setTextColor(textcolor);
                post.setTextFont(textfont);

                final ProgressDialog progressDialog = ProgressDialog.show(PostAddActivity.this, "저장중", "저장중입니다.");
                firestore.collection("notice" + page)
                        .document(now.toString())
                        .set(post)
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
    }

    private void edit(Long registeredDate) { //어댑터에서 보낸 도큐멘트 이름을 넘겨받음
        final ProgressDialog progressDialog = ProgressDialog.show(this, "로딩중", "로딩중입니다.");
        firestore.collection("notice" + page) //lists 컬렉션에서 도큐멘트를 찾아서 불러옴
                .document(String.valueOf(registeredDate))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Post post = task.getResult().toObject(Post.class); //불러온 도큐멘트를 list array에 넣고, 값을 하나하나 대입함
                            now = registeredDate;

                            editTitle.setText(post.getTitle());
                            editTitle.setTextSize(post.getTitleSize());
                            titlesize = post.getTitleSize();
                            titlefont = post.getTitleFont();
                            titlecolor = post.getTitleColor();

                            editContent.setText(post.getContent());
                            editContent.setTextSize(post.getTextSize());
                            textsize = post.getTextSize();
                            textfont = post.getTextFont();
                            textcolor = post.getTextColor();

                            switch (post.getTitleColor()) {
                                case "Black":
                                    editTitle.setTextColor(Color.BLACK);
                                    break;
                                case "Red":
                                    editTitle.setTextColor(Color.RED);
                                    break;
                                case "Blue":
                                    editTitle.setTextColor(Color.BLUE);
                                    break;
                                case "Green":
                                    editTitle.setTextColor(Color.GREEN);
                                    break;
                            }

                            switch (post.getTextColor()) {
                                case "Black":
                                    editContent.setTextColor(Color.BLACK);
                                    break;
                                case "Red":
                                    editContent.setTextColor(Color.RED);
                                    break;
                                case "Blue":
                                    editContent.setTextColor(Color.BLUE);
                                    break;
                                case "Green":
                                    editContent.setTextColor(Color.GREEN);
                                    break;
                            }

                            Typeface typeface;

                            switch (post.getTitleFont()) {
                                case "sans":
                                    editTitle.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                    editTitle.setTypeface(null, Typeface.BOLD);
                                    break;
                                case "serif":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/batang.ttc");
                                    editTitle.setTypeface(typeface);
                                    break;
                                case "casual":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nanumpen.ttf");
                                    editTitle.setTypeface(typeface);
                                    break;
                            }

                            switch (post.getTextFont()) {
                                case "sans":
                                    editContent.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                    break;
                                case "serif":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/batang.ttc");
                                    editContent.setTypeface(typeface);
                                    break;
                                case "casual":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nanumpen.ttf");
                                    editContent.setTypeface(typeface);
                                    break;
                            }
                        }
                    }
                });
    }
}
