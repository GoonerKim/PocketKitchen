package com.example.pocket_kitchen.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.ui.adapters.ContentsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NoticeActivity extends AppCompatActivity {

    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ContentsPagerAdapter mContentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        mContext = getApplicationContext();

        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView(R.drawable.my_recipe,"나만의\n레시피")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView(R.drawable.honey_tip,"생활의\n꿀팁")));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);
        mContentPagerAdapter = new ContentsPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mContentPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private View createTabView(int img, String tabName) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView txt_name = tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        ImageView imageView = tabView.findViewById(R.id.imageV);
        imageView.setImageResource(img);
        return tabView;
    }
}