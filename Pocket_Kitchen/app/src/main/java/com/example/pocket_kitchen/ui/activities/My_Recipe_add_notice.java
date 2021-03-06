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
import com.example.pocket_kitchen.datas.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class My_Recipe_add_notice extends AppCompatActivity {

    private Button send, sizeup, sizedown, color, font;
    private TextView content, name;
    private EditText title;
    private float titlesize;
    private float textsize;
    private float namesize;
    private String titlecolor = "Black";
    private String textcolor = "Black";
    private String namecolor = "Black";
    private String titlefont = "sans";
    private String textfont = "sans";
    private String namefont = "sans";
    private String pickedtext = "title";

    private PopupWindow mDropdown = null;
    private LayoutInflater mInflater;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Long now = System.currentTimeMillis();

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipe_add_notice);

        sp = getSharedPreferences("pocket_kitchen", MODE_PRIVATE);
        sped = sp.edit();

        sizeup = findViewById(R.id.sizeup_button);
        sizedown = findViewById(R.id.sizedown_button);
        color = findViewById(R.id.color_button);
        send = findViewById(R.id.send);
        font = findViewById(R.id.font_button);

        content = findViewById(R.id.content);
        name = findViewById(R.id.name);
        title = findViewById(R.id.title);

        textsize = 20;
        content.setTextSize(textsize);

        titlesize = 20;
        title.setTextSize(titlesize);

        //  namesize = 18;
        //  name.setTextSize(namesize);

        title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pickedtext = "title";
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

        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pickedtext = "content";
                return false;
            }
        });

        sizeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pickedtext) {
                    case "content":
                        textsize += 1;
                        content.setTextSize(textsize);
                        break;
                    case "title":
                        titlesize += 1;
                        title.setTextSize(titlesize);
                        break;
                    case "name":
                        namesize += 1;
                        name.setTextSize(namesize);
                        break;
                }
            }
        });

        sizedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pickedtext) {
                    case "content":
                        textsize -= 1;
                        content.setTextSize(textsize);
                        break;
                    case "title":
                        titlesize -= 1;
                        title.setTextSize(titlesize);
                        break;
                    case "name":
                        namesize -= 1;
                        name.setTextSize(namesize);
                        break;
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                post.setRegisteredDate(now);
                post.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                post.setName(sp.getString("name", "unKnown"));
                post.setTitle(title.getText().toString());
                post.setContent(content.getText().toString());
                post.setTitleSize(titlesize);
                post.setTitleColor(titlecolor);
                post.setTitleFont(titlefont);
                post.setTextSize(textsize);
                post.setTextColor(textcolor);
                post.setTextFont(textfont);

                final ProgressDialog progressDialog = ProgressDialog.show(My_Recipe_add_notice.this, "?????????", "??????????????????.");
                firestore.collection("notice")
                        .document(now.toString())
                        .set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "?????????.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "??????. ?????? ??? ??????????????????.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????? ?????? ????????? ??????
                final PopupWindow popup1 = new PopupWindow(v);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //???????????? ?????? ??????????????? ????????????
                final View view = inflater.inflate(R.layout.post_color_menu, null);
                popup1.setContentView(view);
                //????????? ?????? ??????
                popup1.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //?????? ??? ?????? ?????????
                popup1.setTouchable(true);
                //?????? ??? ???????????? ??????
                popup1.setFocusable(true);
                //?????? ??? ???????????? ???????????? (????????? ?????? ?????? ?????? ??????)
                popup1.setOutsideTouchable(true);

                ImageView black = (ImageView) view.findViewById(R.id.btn_black);
                black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (pickedtext) {
                            case "content":
                                textcolor = "Black";
                                content.setTextColor(Color.BLACK);
                                break;
                            case "title":
                                titlecolor = "Black";
                                title.setTextColor(Color.BLACK);
                                break;
                            case "name":
                                namecolor = "Black";
                                name.setTextColor(Color.BLACK);
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
                            case "content":
                                textcolor = "Red";
                                content.setTextColor(Color.RED);
                                break;
                            case "title":
                                titlecolor = "Red";
                                title.setTextColor(Color.RED);
                                break;
                            case "name":
                                namecolor = "Red";
                                name.setTextColor(Color.RED);
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
                            case "content":
                                textcolor = "Blue";
                                content.setTextColor(Color.BLUE);
                                break;
                            case "title":
                                titlecolor = "Blue";
                                title.setTextColor(Color.BLUE);
                                break;
                            case "name":
                                namecolor = "Blue";
                                name.setTextColor(Color.BLUE);
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
                            case "content":
                                textcolor = "Green";
                                content.setTextColor(Color.GREEN);
                                break;
                            case "title":
                                titlecolor = "Green";
                                title.setTextColor(Color.GREEN);
                                break;
                            case "name":
                                namecolor = "Green";
                                name.setTextColor(Color.GREEN);
                                break;
                        }
                        popup1.dismiss();
                    }
                });

                //????????? ????????? v ????????? ????????????
                popup1.showAsDropDown(v);
            }
        });

        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupMenu?????? ??????.
                //?????????????????? ????????? ???????????? : Context
                //?????????????????? ????????? ???????????? : Popup Menu??? ?????? anchor ???
                PopupMenu popup = new PopupMenu(v.getContext(), v);//v??? ????????? ?????? ??????

                //Popup Menu??? ????????? MenuItem ??????.
                //?????? ???????????? ???????????? ??????(Context menu)?????? xml ?????? ????????? ??????
                //????????? ???????????? : res??????>>menu??????>>my_recipe_mainmenu.xml?????? ?????????
                //????????? ???????????? : Menu ??????->Popup Menu ????????? ?????? Menu ?????? ????????????
                getMenuInflater().inflate(R.menu.my_recipe_fontmenu, popup.getMenu());

                //Popup Menu??? MenuItem??? ???????????? ?????? ???????????? listener ??????
                popup.setOnMenuItemClickListener(listener);

                popup.show();//Popup Menu ?????????
            }
        });

        Intent intent = getIntent(); //intent?????? ?????? ????????????
        try {
            String isEdit = intent.getExtras().getString("edit");
            if (!isEdit.equals("false")) edit(Long.valueOf(isEdit)); //?????? ??????????????? ?????? ??? edit func ??????
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "??????. ?????? ??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    //Popup Menu??? MenuItem??? ???????????? ?????? ???????????? listener ?????? ??????
    //import android.widget.PopupMenu.OnMenuItemClickListener ??? ??????????????? ?????????.
    //OnMenuItemClickListener ???????????? ?????? ??????????????? ?????? ????????? PopupMenu??? ???????????? ???????????? ?????????????????? ?????????.
    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // TODO Auto-generated method stub
            switch (item.getItemId()) {//????????? MenuItem??? My_Recipe_Item Id??? ????????? ??????
                case R.id.black:
                    switch (pickedtext) {
                        case "content":
                            textcolor = "Black";
                            content.setTextColor(Color.BLACK);
                            break;
                        case "title":
                            titlecolor = "Black";
                            title.setTextColor(Color.BLACK);
                            break;
                        case "name":
                            namecolor = "Black";
                            name.setTextColor(Color.BLACK);
                            break;
                    }
                    break;

                case R.id.red:
                    switch (pickedtext) {
                        case "content":
                            textcolor = "Red";
                            content.setTextColor(Color.RED);
                            break;
                        case "title":
                            titlecolor = "Red";
                            title.setTextColor(Color.RED);
                            break;
                        case "name":
                            namecolor = "Red";
                            name.setTextColor(Color.RED);
                            break;
                    }

                    break;

                case R.id.blue:
                    switch (pickedtext) {
                        case "content":
                            textcolor = "Blue";
                            content.setTextColor(Color.BLUE);
                            break;
                        case "title":
                            titlecolor = "Blue";
                            title.setTextColor(Color.BLUE);
                            break;
                        case "name":
                            namecolor = "Blue";
                            name.setTextColor(Color.BLUE);
                            break;
                    }

                    break;

                case R.id.green:
                    switch (pickedtext) {
                        case "content":
                            textcolor = "Green";
                            content.setTextColor(Color.GREEN);
                            break;
                        case "title":
                            titlecolor = "Green";
                            title.setTextColor(Color.GREEN);
                            break;
                        case "name":
                            namecolor = "Green";
                            name.setTextColor(Color.GREEN);
                            break;
                    }

                    break;

                case R.id.sans:
                    switch (pickedtext) {
                        case "content":
                            textfont = "sans";
                            content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                            break;
                        case "title":
                            titlefont = "sans";
                            title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                            title.setTypeface(null, Typeface.BOLD);
                            break;
                        case "name":
                            namefont = "sans";
                            name.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                            break;
                    }

                    break;

                case R.id.serif:

                    Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");

                    switch (pickedtext) {
                        case "content":
                            textfont = "serif";
                            content.setTypeface(typeface1);
                            break;
                        case "title":
                            titlefont = "serif";
                            title.setTypeface(typeface1);
                            break;
                        case "name":
                            namefont = "serif";
                            name.setTypeface(typeface1);
                            break;
                    }
                    break;

                case R.id.casual:
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");

                    switch (pickedtext) {
                        case "content":
                            textfont = "casual";
                            content.setTypeface(typeface);
                            break;
                        case "title":
                            titlefont = "casual";
                            title.setTypeface(typeface);
                            break;
                        case "name":
                            namefont = "casual";
                            name.setTypeface(typeface);
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
            View layout = mInflater.inflate(R.layout.post_color_menu, null);

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
            mDropdown.showAsDropDown(color, 5, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }

    private void edit(Long registeredDate) { //??????????????? ?????? ???????????? ????????? ????????????
        final ProgressDialog progressDialog = ProgressDialog.show(this, "?????????", "??????????????????.");
        firestore.collection("notice") //lists ??????????????? ??????????????? ????????? ?????????
                .document(String.valueOf(registeredDate))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Post post = task.getResult().toObject(Post.class); //????????? ??????????????? list array??? ??????, ?????? ???????????? ?????????
                            now = registeredDate;

                            title.setText(post.getTitle());
                            title.setTextSize(post.getTitleSize());
                            titlesize = post.getTitleSize();
                            titlefont = post.getTitleFont();
                            titlecolor = post.getTitleColor();

                            content.setText(post.getContent());
                            content.setTextSize(post.getTextSize());
                            textsize = post.getTextSize();
                            textfont = post.getTextFont();
                            textcolor = post.getTextColor();

                            switch (post.getTitleColor()) {
                                case "Black":
                                    title.setTextColor(Color.BLACK);
                                    break;
                                case "Red":
                                    title.setTextColor(Color.RED);
                                    break;
                                case "Blue":
                                    title.setTextColor(Color.BLUE);
                                    break;
                                case "Green":
                                    title.setTextColor(Color.GREEN);
                                    break;
                            }

                            switch (post.getTextColor()) {
                                case "Black":
                                    content.setTextColor(Color.BLACK);
                                    break;
                                case "Red":
                                    content.setTextColor(Color.RED);
                                    break;
                                case "Blue":
                                    content.setTextColor(Color.BLUE);
                                    break;
                                case "Green":
                                    content.setTextColor(Color.GREEN);
                                    break;
                            }

                            Typeface typeface;

                            switch (post.getTitleFont()) {
                                case "sans":
                                    title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                    title.setTypeface(null, Typeface.BOLD);
                                    break;
                                case "serif":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/batang.ttc");
                                    title.setTypeface(typeface);
                                    break;
                                case "casual":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nanumpen.ttf");
                                    title.setTypeface(typeface);
                                    break;
                            }

                            switch (post.getTextFont()) {
                                case "sans":
                                    content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                    break;
                                case "serif":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/batang.ttc");
                                    content.setTypeface(typeface);
                                    break;
                                case "casual":
                                    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nanumpen.ttf");
                                    content.setTypeface(typeface);
                                    break;
                            }
                        }
                    }
                });
    }
}