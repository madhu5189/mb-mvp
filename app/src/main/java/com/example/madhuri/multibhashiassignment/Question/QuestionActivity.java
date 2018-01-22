package com.example.madhuri.multibhashiassignment.Question;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhuri.multibhashiassignment.Learn.LearnActivity;
import com.example.madhuri.multibhashiassignment.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity implements QuestionView{

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private ImageView ivBGImageQuestion;
    private TextView tvSuggestionHeadphonesQn;
    private TextView tvLessonQuestion;
    private TextView tvLessonQuestionScript;
    private ImageButton ibtnPlayLearn;
    private FloatingActionButton fabNext;
    private ImageButton ibtnRecordAudio;
    private int counter;
    private String audio = null;
    SharedPreferences sharedpreferences;

    QuesionPresenter mQuesionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mQuesionPresenter = new QuestionPresenterImpl(this);

        sharedpreferences = getSharedPreferences(getString(R.string.mypref),
                Context.MODE_PRIVATE);
        counter = sharedpreferences.getInt(getString(R.string.counter), 0);

        ivBGImageQuestion = (ImageView) findViewById(R.id.ivBGImageQn);
        tvSuggestionHeadphonesQn = (TextView) findViewById(R.id.tvSugnHeadphonesQn);
        tvLessonQuestion = (TextView) findViewById(R.id.tvLessonQuestion);
        tvLessonQuestionScript = (TextView) findViewById(R.id.tvLessonQuestionScript);
        ibtnPlayLearn = (ImageButton) findViewById(R.id.ibtnPlayLearn);
        fabNext = (FloatingActionButton) findViewById(R.id.fabNext);
        ibtnRecordAudio = (ImageButton) findViewById(R.id.ibtnRecordAudio);

        ibtnPlayLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuesionPresenter.getQuestionLessonData(counter);
            }
        });

        ibtnPlayLearn.performClick();
// Record to the external cache directory for visibility
//        mFileName = getExternalCacheDir().getAbsolutePath();
//        mFileName += "/audiorecordtest.3gp";
        ibtnRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askSpeechInput();
            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuesionPresenter.checkNextLesson(counter);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    audio = result.get(0);

                    mQuesionPresenter.checkRecordedAudio(audio);
                }
                break;
            }

        }
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
    }

    @Override
    public void recordAudio() {

    }

    @Override
    public void checkPronunctiation(String conceptname) {

    }

    @Override
    public void setQuestionText(String conceptname) {
        tvLessonQuestion.setText(conceptname);
    }

    @Override
    public void setScriptText(String scriptText) {
        tvLessonQuestionScript.setText(scriptText);
    }

    @Override
    public void navigateToLearn() {
        Intent intent = new Intent(QuestionActivity.this, LearnActivity.class);
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
        mQuesionPresenter.onDestroy();
    }
}
