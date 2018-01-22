package com.example.madhuri.multibhashiassignment.LessonModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface RetrofitLessonDataAPI {

    @GET("getLessonData.php")
    Call<LessonExample> getLessonExample();

}
