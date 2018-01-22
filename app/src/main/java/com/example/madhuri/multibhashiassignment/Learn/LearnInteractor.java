package com.example.madhuri.multibhashiassignment.Learn;

import com.example.madhuri.multibhashiassignment.LessonModel.LessonDatum;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface LearnInteractor {

    interface validLessonListener{
        void onSuccessLesson(LessonDatum lessonDatum);
        void onFailureLesson(String message);
    }

    interface validDataListener{
        void onSuccessData();
        void onFailureData(String message);
    }

    void fetchData(/*List<LessonDatum> lessonData,*/ int counter, validLessonListener lessonListener);

    void checkNextData(/*List<LessonDatum> lessonData,*/ int counter, validDataListener dataListener);
}
