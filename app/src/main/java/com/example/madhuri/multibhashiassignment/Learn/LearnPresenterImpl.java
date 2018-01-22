package com.example.madhuri.multibhashiassignment.Learn;

import com.example.madhuri.multibhashiassignment.LessonModel.LessonDatum;

import java.util.List;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class LearnPresenterImpl implements LearnPresenter, LearnInteractor.validDataListener, LearnInteractor.validLessonListener {

    private LearnView mLearnView;
    private LearnInteractor mLearnInteractor;
    List<LessonDatum> lessonData;
    private int counter;

    public LearnPresenterImpl(final LearnView mLearnView) {
        this.mLearnView = mLearnView;
        this.mLearnInteractor = new LearnInteractorImpl();

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
//                mLearnView.showAlert(t.getMessage());
//            }
//        });
    }

    @Override
    public void getLearnLessonData(int counter) {

        this.counter = counter;
        mLearnInteractor.fetchData(/*lessonData, */counter, this);

    }

    @Override
    public void checkNextLesson(int counter) {
        this.counter = counter;
        mLearnInteractor.checkNextData(/*lessonData, */counter, this);
    }

    @Override
    public void onDestroy() {
        if (mLearnView != null) {
            mLearnView = null;
        }
    }


    @Override
    public void onSuccessLesson(LessonDatum lessonDatum) {
//        LessonDatum lessonDatum = lessonData.get(counter);

        mLearnView.setQuestionText(lessonDatum.getConceptName());

        mLearnView.setScriptText(lessonDatum.getTargetScript());

        String url = lessonDatum.getAudioUrl(); // your URL here
        mLearnView.playAudio(url);
    }

    @Override
    public void onFailureLesson(String message) {
        mLearnView.showAlert(message);
    }

    @Override
    public void onSuccessData() {
        mLearnView.navigateToQuestion();
    }

    @Override
    public void onFailureData(String message) {
        mLearnView.navigateToEnd();

    }
}
