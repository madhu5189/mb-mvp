package com.example.madhuri.multibhashiassignment.Question;

import com.example.madhuri.multibhashiassignment.LessonModel.LessonDatum;

import java.util.List;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class QuestionPresenterImpl implements QuesionPresenter, QuestionInteractor.validDataListener, QuestionInteractor.validLessonListener, QuestionInteractor.validAudioSample {

    private QuestionView mQuestionView;
    private QuestionInteractor mQuestionInteractor;
    List<LessonDatum> lessonData;
    private int counter;

    public QuestionPresenterImpl(QuestionView mQuestionView) {
        this.mQuestionView = mQuestionView;
        mQuestionInteractor = new QuestionInteractorImpl();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.akshaycrt2k.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitLessonDataAPI service = retrofit.create(RetrofitLessonDataAPI.class);
//
//        Call<LessonExample> call = service.getLessonExample();
//
//        call.enqueue(new Callback<LessonExample>() {
//            @Override
//            public void onResponse(Call<LessonExample> call, Response<LessonExample> response) {
//                LessonExample responseLesson = response.body();
//
//                if (responseLesson != null) {
//                    //get data node from the api json response
//                    lessonData = responseLesson.getLessonData();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LessonExample> call, Throwable t) {
//            }
//        });
    }

    @Override
    public void getQuestionLessonData(int counter) {
        this.counter = counter;
        mQuestionInteractor.fetchData(/*lessonData, */counter, this);
    }

    @Override
    public void checkRecordedAudio(String audio) {
        this.counter = counter;
        mQuestionInteractor.validateAudioRecorded(/*lessonData, */counter, audio, this);
    }

    @Override
    public void checkNextLesson(int counter) {
        this.counter = counter;
        mQuestionInteractor.checkNextData(/*lessonData, */counter, this);
    }

    @Override
    public void onDestroy() {
        if (mQuestionView != null) {
            mQuestionView = null;
        }
    }

    @Override
    public void onSuccessLesson(LessonDatum lessonDatum) {
//        LessonDatum lessonDatum = lessonData.get(counter);

        mQuestionView.setQuestionText(lessonDatum.getConceptName());

        mQuestionView.setScriptText(lessonDatum.getTargetScript());

        mQuestionView.checkPronunctiation(lessonDatum.getConceptName());

        String url = lessonDatum.getAudioUrl(); // your URL here
        mQuestionView.playAudio(url);
    }

    @Override
    public void onFailureLesson(String message) {
        mQuestionView.showAlert(message);
    }

    @Override
    public void onSuccessData() {
        mQuestionView.navigateToLearn();
    }

    @Override
    public void onFailureData(String message) {
        mQuestionView.navigateToEnd();
    }

    @Override
    public void onSuccessRecording(Double similarity) {
        mQuestionView.showAlert(similarity*100 + "% match found!");
    }

    @Override
    public void onFailureRecording(String message) {
        mQuestionView.showAlert(message);
    }
}
