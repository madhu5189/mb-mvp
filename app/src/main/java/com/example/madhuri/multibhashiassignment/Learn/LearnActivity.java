package com.example.madhuri.multibhashiassignment.Learn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhuri.multibhashiassignment.Question.QuestionActivity;
import com.example.madhuri.multibhashiassignment.R;

import java.io.IOException;

public class LearnActivity extends AppCompatActivity implements LearnView{

    private ImageView ivBGImage;
    private TextView tvSuggestionHeadphones;
    private TextView tvLessonQuestion;
    private TextView tvLessonQuestionScript;
    private ImageButton ibtnPlayLearn;
    private FloatingActionButton fabNext;
    private int counter;
    SharedPreferences sharedpreferences;
    private String type;
    private String conceptName;
    private String targetScript;
    private String pronunciation;
    private String audioUrl;

    private LearnPresenter mLearnPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        mLearnPresenter = new LearnPresenterImpl(this);

        sharedpreferences = getSharedPreferences(getString(R.string.mypref),
                Context.MODE_PRIVATE);
        counter = sharedpreferences.getInt(getString(R.string.counter), 0);
        ivBGImage = (ImageView) findViewById(R.id.ivBGImage);
        tvSuggestionHeadphones = (TextView) findViewById(R.id.tvSuggestionHeadphones);
        tvLessonQuestion = (TextView) findViewById(R.id.tvLessonQuestion);
        tvLessonQuestionScript = (TextView) findViewById(R.id.tvLessonQuestionScript);
        ibtnPlayLearn = (ImageButton) findViewById(R.id.ibtnPlayLearn);
        fabNext = (FloatingActionButton) findViewById(R.id.fabNext);

        ibtnPlayLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLearnPresenter.getLearnLessonData(counter);
            }
        });

        ibtnPlayLearn.performClick();

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLearnPresenter.checkNextLesson(counter);
            }
        });
    }

    @Override
    public void playAudio(String url) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @Override
    public void setQuestionText(String conceptName) {
        tvLessonQuestion.setText(conceptName);
    }

    @Override
    public void setScriptText(String scriptText) {
        tvLessonQuestionScript.setText(scriptText);
    }

    @Override
    public void navigateToQuestion() {
        Intent intent = new Intent(LearnActivity.this, QuestionActivity.class);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(getString(R.string.counter), counter+1);
        editor.commit();
        startActivity(intent);
    }

    @Override
    public void navigateToEnd() {
        onStop();
    }

    @Override
    public void showAlert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLearnPresenter.onDestroy();
    }
}
