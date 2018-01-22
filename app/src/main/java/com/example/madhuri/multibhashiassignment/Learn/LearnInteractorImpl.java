package com.example.madhuri.multibhashiassignment.Learn;

import com.example.madhuri.multibhashiassignment.LessonModel.LessonDatum;
import com.example.madhuri.multibhashiassignment.LessonModel.LessonExample;
import com.example.madhuri.multibhashiassignment.LessonModel.RetrofitLessonDataAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class LearnInteractorImpl implements LearnInteractor {

    @Override
    public void fetchData(/*List<LessonDatum> lessonData, */final int counter, final validLessonListener lessonListener) {

        List<LessonDatum> lessonData;
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
                    LessonDatum lessonDatum = lessonData.get(counter);
                    String lessonType = lessonDatum.getType();
                    if (lessonType.equals("learn"))

                        lessonListener.onSuccessLesson(lessonDatum);
                    else
                        lessonListener.onFailureLesson("Oops! Something went wrong.");

                }

            }

            @Override
            public void onFailure(Call<LessonExample> call, Throwable t) {
            }
        });
//        String lessonType = lessonData.get(counter).getType();
//        if (lessonType.equals("learn"))
//
//            lessonListener.onSuccessLesson();
//        else
//            lessonListener.onFailureLesson("Oops! Something went wrong.");
    }

    @Override
    public void checkNextData(/*List<LessonDatum> lessonData, */final int counter, final validDataListener dataListener) {

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
                    LessonDatum lessonDatum = lessonData.get(counter);
                    String lessonType = lessonDatum.getType();
//                    if (lessonType.equals("learn"))
//
//                        dataListener.onSuccessData(lessonDatum);
//                    else
//                        dataListener.onFailureData("Oops! Something went wrong.");
                    if (counter < (lessonData.size() - 1) &&
                            lessonData.get(counter + 1).getType().equals("question"))
                        dataListener.onSuccessData();
                    else
                        dataListener.onFailureData("Lesson Completed!");

                }

            }

            @Override
            public void onFailure(Call<LessonExample> call, Throwable t) {
            }
        });
//        if (counter < (lessonData.size() - 1) &&
//                lessonData.get(counter + 1).getType().equals("question"))
//            dataListener.onSuccessData();
//        else
//            dataListener.onFailureData("Lesson Completed!");


    }
}
