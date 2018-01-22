package com.example.madhuri.multibhashiassignment.Question;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.madhuri.multibhashiassignment.LessonModel.LessonDatum;
import com.example.madhuri.multibhashiassignment.LessonModel.LessonExample;
import com.example.madhuri.multibhashiassignment.LessonModel.RetrofitLessonDataAPI;

import java.io.IOException;
import java.util.List;

import info.debatty.java.stringsimilarity.JaroWinkler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class QuestionInteractorImpl implements QuestionInteractor {

    private static final String LOG_TAG = "AudioRecordTest";

    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;


    @Override
    public void fetchData(/*List<LessonDatum> lessonData, */final int counter, final validLessonListener lessonListener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.akshaycrt2k.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitLessonDataAPI service = retrofit.create(RetrofitLessonDataAPI.class);

        Call<LessonExample> call = service.getLessonExample();

        call.enqueue(new Callback<LessonExample>() {
            @Override
            public void onResponse(Call<LessonExample> call, Response<LessonExample> response) {
                LessonExample responseLesson = response.body();

                if (responseLesson != null) {
                    //get data node from the api json response
                    List<LessonDatum> lessonData = responseLesson.getLessonData();
                    String lessonType = lessonData.get(counter).getType();
                    if (lessonType.equals("question"))

                        lessonListener.onSuccessLesson(lessonData.get(counter));
                    else
                        lessonListener.onFailureLesson("Oops! Something went wrong.");
                }

            }

            @Override
            public void onFailure(Call<LessonExample> call, Throwable t) {
            }
        });
//        String lessonType = lessonData.get(counter).getType();
//        if (lessonType.equals("question"))
//
//            lessonListener.onSuccessLesson();
//        else
//            lessonListener.onFailureLesson("Oops! Something went wrong.");
    }

    @Override
    public void checkNextData(/*List<LessonDatum> lessonData, */ final int counter, final validDataListener dataListener) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.akshaycrt2k.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitLessonDataAPI service = retrofit.create(RetrofitLessonDataAPI.class);

        Call<LessonExample> call = service.getLessonExample();

        call.enqueue(new Callback<LessonExample>() {
            @Override
            public void onResponse(Call<LessonExample> call, Response<LessonExample> response) {
                LessonExample responseLesson = response.body();

                if (responseLesson != null) {
                    //get data node from the api json response
                    List<LessonDatum> lessonData = responseLesson.getLessonData();
                    if (counter< (lessonData.size()-1) &&
                            lessonData.get(counter+1).getType().equals("learn"))
                        dataListener.onSuccessData();
                    else
                        dataListener.onFailureData("Lesson Completed!");
                }

            }

            @Override
            public void onFailure(Call<LessonExample> call, Throwable t) {
            }
        });
//                    if (counter< (lessonData.size()-1) &&
//                            lessonData.get(counter+1).getType().equals("learn"))
//                        dataListener.onSuccessData();
//                    else
//                        dataListener.onFailureData("Lesson Completed!");

    }

    @Override
    public void validateAudioRecorded(/*List<LessonDatum> lessonData,*/ final int counter, final String audio, final validAudioSample audioListener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.akshaycrt2k.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitLessonDataAPI service = retrofit.create(RetrofitLessonDataAPI.class);

        Call<LessonExample> call = service.getLessonExample();

        call.enqueue(new Callback<LessonExample>() {
            @Override
            public void onResponse(Call<LessonExample> call, Response<LessonExample> response) {
                LessonExample responseLesson = response.body();

                if (responseLesson != null) {
                    //get data node from the api json response
                    List<LessonDatum> lessonData = responseLesson.getLessonData();
                    JaroWinkler jw = new JaroWinkler();
                    double similarity = jw.similarity(audio.toLowerCase(), lessonData.get(counter).getPronunciation().toLowerCase());


                    if (similarity > 0.70){
                        audioListener.onSuccessRecording(similarity);
                    }
                    else
                        audioListener.onFailureRecording("Sorry, try again. \n Only" + similarity*100 + "% match found.");
                }

            }

            @Override
            public void onFailure(Call<LessonExample> call, Throwable t) {
            }
        });
//        JaroWinkler jw = new JaroWinkler();
//        double similarity = jw.similarity(audio.toLowerCase(), lessonData.get(counter).getPronunciation().toLowerCase());
//
//
//        if (similarity > 0.80){
//            audioListener.onSuccessRecording(similarity);
//        }
//        else
//            audioListener.onFailureRecording("Sorry, try again. \n Only" + similarity*100 + "% match found.");
    }

    void checkAudio(){

    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
