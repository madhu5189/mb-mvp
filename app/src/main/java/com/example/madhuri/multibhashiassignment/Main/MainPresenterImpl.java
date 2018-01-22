package com.example.madhuri.multibhashiassignment.Main;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class MainPresenterImpl implements MainPresenter, MainInteractor.valideDataListener{

    MainView mMainView;
    MainInteractor mMainInteractor;

    public MainPresenterImpl(MainView mainView){
        mMainView = mainView;
        mMainInteractor = new MainInteractorImpl();
    }
    @Override
    public void getLessonData() {
        mMainInteractor.fetchData(this);
    }

    @Override
    public void onDestroy() {
        if (mMainView != null) {
            mMainView = null;
        }
    }

    @Override
    public void onSuccess() {
        if (mMainView != null) {
            mMainView.navigateToLearn();
        }
    }

    @Override
    public void onFailure(String message) {
        if (mMainView != null) {
            mMainView.showAlert(message);
        }
    }
}
