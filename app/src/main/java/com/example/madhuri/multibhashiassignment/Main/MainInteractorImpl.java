package com.example.madhuri.multibhashiassignment.Main;

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

public class MainInteractorImpl implements MainInteractor{

    @Override
    public void fetchData(final valideDataListener listener) {

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
                    listener.onSuccess();


                }
                else {
                    listener.onFailure("No data available");
                }
            }

            @Override
            public void onFailure(Call<LessonExample> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
