package com.example.madhuri.multibhashiassignment.Learn;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface LearnView {
    void playAudio(String url);
    void setQuestionText(String conceptname);
    void setScriptText(String scriptText);
    void navigateToQuestion();
    void navigateToEnd();
    void showAlert(String message);
}
