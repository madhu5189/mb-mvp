package com.example.madhuri.multibhashiassignment.Question;

import com.example.madhuri.multibhashiassignment.LessonModel.LessonDatum;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface QuestionInteractor {

    interface validLessonListener{
        void onSuccessLesson(LessonDatum lessonData);
        void onFailureLesson(String message);
    }

    interface validDataListener{
        void onSuccessData();
        void onFailureData(String message);
    }

    interface validAudioSample{
        void onSuccessRecording(Double similarity);
        void onFailureRecording(String message);
    }

    void fetchData(/*List<LessonDatum> lessonData, */int counter, QuestionInteractor.validLessonListener lessonListener);

    void checkNextData(/*List<LessonDatum> lessonData,*/ int counter, QuestionInteractor.validDataListener dataListener);

    void validateAudioRecorded(/*List<LessonDatum> lessonData, */int counter, String audio, QuestionInteractor.validAudioSample audioListener);
}
