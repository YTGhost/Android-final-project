package com.bjtu.campus_information_platform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bjtu.campus_information_platform.R;


public class ProfileHistory  extends AppCompatActivity {
    /* private Button bt1;*/



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_history);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


      /*  bt1 = (Button) findViewById(R.id.back);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   MyApplication.fragmentManager.beginTransaction().replace(R.id.view_pager_bottom, MyApplication.profile_Fragment_History)
                        .commit();*//*
               finish();
            }
        });*/
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
