package com.xindihuang.accountingapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

//@author: Xindi Huang 16518350 zy18350@nottingham.edu.cn

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private TickerView amountText;
    private TextView dateText;
    private int currentPagerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil.getInstance().mainActivity = this;

        getSupportActionBar().setElevation(0);    //remove the shadow of action bar

        amountText = (TickerView)findViewById(R.id.amount_text);
        amountText.setCharacterLists(TickerUtils.provideNumberList());
        dateText=(TextView) findViewById(R.id.date_text);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(pagerAdapter.getLastIndex()); //display today's record when log in

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivityForResult(intent,1);
            }
        });
        updateHeader();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.reload();
        updateHeader();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPagerPosition = position;
        updateHeader();
    }

    public void updateHeader(){
        String amount = String.valueOf(pagerAdapter.getTotalCost(currentPagerPosition));
        amountText.setText(amount);
        String date = pagerAdapter.getDateStr(currentPagerPosition);
        dateText.setText(DateUtil.getWeekDay(date));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
