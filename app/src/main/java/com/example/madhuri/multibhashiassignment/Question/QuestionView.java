package com.example.madhuri.multibhashiassignment.Question;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface QuestionView {

    void playAudio(String url);
    void recordAudio();
    void checkPronunctiation(String conceptName);
    void setQuestionText(String conceptname);
    void setScriptText(String scriptText);
    void navigateToLearn();
    void navigateToEnd();
    void showAlert(String message);
}
