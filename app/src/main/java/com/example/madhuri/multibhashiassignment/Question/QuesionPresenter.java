package com.example.madhuri.multibhashiassignment.Question;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface QuesionPresenter{

    void getQuestionLessonData(int counter);
    void checkRecordedAudio(String audio);
    void checkNextLesson(int counter);
    void onDestroy();

}
