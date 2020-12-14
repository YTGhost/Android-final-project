package com.bjtu.campus_information_platform.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bjtu.campus_information_platform.R;

public class ProfileAbout extends AppCompatActivity {

    private LinearLayout msg;
    private LinearLayout statement;
    private LinearLayout complaint;
    private LinearLayout customerService;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_about);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        msg = (LinearLayout) findViewById(R.id.msg);
        statement = (LinearLayout) findViewById(R.id.statement);
        complaint = (LinearLayout) findViewById(R.id.complaint);
        customerService = (LinearLayout) findViewById(R.id.customerService);

        msg.setOnClickListener(v -> {
            Toast.makeText(this, "暂时没有用户协议啦", Toast.LENGTH_SHORT).show();
        });

        statement.setOnClickListener(v -> {
            Toast.makeText(this, "也暂时没有隐私声明啦", Toast.LENGTH_SHORT).show();
        });

        complaint.setOnClickListener(v -> {
            Toast.makeText(this, "投诉请联系客服_(:3」∠)_", Toast.LENGTH_SHORT).show();
        });

        customerService.setOnClickListener(v -> {
            Toast.makeText(this, "客服就在台上hhh", Toast.LENGTH_SHORT).show();
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
