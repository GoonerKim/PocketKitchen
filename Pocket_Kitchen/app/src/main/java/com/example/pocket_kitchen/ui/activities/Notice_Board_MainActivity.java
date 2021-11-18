package com.example.pocket_kitchen.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.My_Recipe_Item;
import com.example.pocket_kitchen.ui.fragments.Honey_tip_MainFragment;
import com.example.pocket_kitchen.ui.fragments.My_Recipe_MainFragment;

import java.util.Objects;

import butterknife.ButterKnife;

public class Notice_Board_MainActivity extends AppCompatActivity {

    //프래그먼트는  xml레이아웃 파일 하나랑 자바소스 파일 하나로 정의할 수 있다.
    //이게 하나의 뷰처럼 쓸 수 있는데 뷰하고 약간 다른특성들이 있다.
    //엑티비티를 본떠 만들었기 떄문에 프래그먼트 매니저가 소스코드에서 담당한다.
    My_Recipe_MainFragment fragment1;
    Honey_tip_MainFragment fragment2;
    private FragmentManager fragmentManager;
    private ImageButton button1;
    public ImageButton button1_re;
    private boolean isFragment1 = false;
    private boolean isFragment2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_board_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        //프래그먼트는 뷰와 다르게 context를 매개변수로 넣어줄 필요가 없다.
        //fragment1 = new My_Recipe_MainFragment();
        // fragmentManager.beginTransaction().replace(R.id.container, fragment1).commit();

        // fragment2 = new MenuFragment();

        ImageButton button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //프래그먼트 추가하거나 할떄는 여러개 명령을 한꺼번에 쓸 수 있으므로
                //beginTransaction을 사용함
                //fragment1를 R.id.container에 넣어달라(add 또는 replace, replace는 기존에있던걸 대체해줌)
                //트랜잭션안에서 수행되는것이므로 마지막에 꼭 commit을 해줘야 실행이된다.
                onMoveFragment1();
                //  getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();/*프래그먼트 매니저가 프래그먼트를 담당한다!*/


                /*getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();*/

            }
        });

        ImageButton button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoveFragment2();
                // getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();/*프래그먼트 매니저가 프래그먼트를 담당한다!*/
            }
        });
    }

    public void onMoveFragment1() {
        ImageButton button1_re = findViewById(R.id.button1);
        button1_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment1 == null) {
                    fragment1 = new My_Recipe_MainFragment();
                    fragmentManager.beginTransaction().add(R.id.container, fragment1, "fragment1").commit();
                }

                if (fragment1 != null) {
                    fragmentManager.beginTransaction().show(fragment1).commit();
                    if (!isFragment1) isFragment1 = true;
                    else fragment1.update();
                }
                if (fragment2 != null) fragmentManager.beginTransaction().hide(fragment2).commit();
            }

        });
    }

    public void onMoveFragment2() {
        ImageButton button2_re = findViewById(R.id.button2);
        button2_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment2 == null) {
                    fragment2 = new Honey_tip_MainFragment();
                    fragmentManager.beginTransaction().add(R.id.container, fragment2, "fragment2").commit();
                }

                if (fragment1 != null) fragmentManager.beginTransaction().hide(fragment1).commit();
                if (fragment2 != null) {
                    fragmentManager.beginTransaction().show(fragment2).commit();
                    if (!isFragment2) isFragment2 = true;
                    else fragment2.update();
                }
            }

        });
    }
}

