package com.mapp.intentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mapp.intentexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);

    }

    public void onClickExplicitIntent(){
        Intent myIntent = new Intent(this, ImplicitIntent.class);
        startActivity(myIntent);
    }

    public void onClickImplicitIntent(){
        Uri webpage = Uri.parse("https://www.android.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    public void onClickPendingIntent(){

    }

    public void onClickStickyIntent(){

    }

}