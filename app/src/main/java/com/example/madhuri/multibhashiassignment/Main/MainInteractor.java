package com.example.madhuri.multibhashiassignment.Main;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public interface MainInteractor {

    interface valideDataListener{
        void onSuccess();
        void onFailure(String message);
    }

    void fetchData(valideDataListener listener);
}
