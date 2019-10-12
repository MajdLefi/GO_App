package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar ;
    TextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(FLAG_FULLSCREEN,
                FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_bar) ;
        textView = findViewById(R.id.text_view) ;

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation () ;
    }

    public void progressAnimation () {

        ProgressBarAnimation anim = new ProgressBarAnimation(this , progressBar ,textView ,0f , 100f ) ;
        anim.setDuration(4000);
        progressBar.setAnimation(anim) ;


    }


}

