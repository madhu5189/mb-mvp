package com.example.madhuri.multibhashiassignment.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.madhuri.multibhashiassignment.Learn.LearnActivity;
import com.example.madhuri.multibhashiassignment.R;

public class MainActivity extends AppCompatActivity implements MainView{

    private Button btnStart;

    private MainPresenter mMainPresenter;

    public static final String lesson = "learn";
    public static final String pronunciation = "";
    public static final String scriptText = "";
    public static final String url = "";
    public static final int counter = 0;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(getString(R.string.mypref),
                Context.MODE_PRIVATE);
        mMainPresenter = new MainPresenterImpl(this);

        btnStart = (Button) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresenter.getLessonData();
            }
        });
    }

    @Override
    public void navigateToLearn() {
        Intent intent = new Intent(MainActivity.this, LearnActivity.class);
        int i = 0;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(getString(R.string.counter), i);
        editor.apply();
        startActivity(intent);
    }

    @Override
    public void showAlert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }
}
