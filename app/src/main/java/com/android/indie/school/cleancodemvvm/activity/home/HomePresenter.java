package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.base.BasePresenter;
import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.android.indie.school.cleancodemvvm.networking.NetworkError;
import com.android.indie.school.cleancodemvvm.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by herisulistiyanto on 12/9/16.
 */

class HomePresenter extends BasePresenter<HomeView, HomeViewModel> {

    private Service service;
    private CompositeSubscription subscriptions;

    HomePresenter(Service service) {
        this.service = service;
        subscriptions = new CompositeSubscription();
    }

    void showProgressWait() {
        viewModel.setInProgress(true);
    }

    void removeProgressWait() {
        viewModel.setInProgress(false);
    }

    void getCityList() {
        showProgressWait();
        final Subscription subscription = service.getCityList(new Service.GetCityListCallback() {
            @Override
            public void onSuccess(CityListResponse cityListResponse) {
                removeProgressWait();
                view.onSuccessFetchData(cityListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                removeProgressWait();
                view.onErrorFetchData(networkError.getAppErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    void reloadCityList() {

        if (subscriptions.hasSubscriptions()) {
            subscriptions.clear();
        }

        /*
        Edited by sendz
        Change to view.fetchData() so this part of code can be testable by mock because we only want
        to verify the fetchData is called instead of checking whole getCityList checking
         */
        view.fetchData();
    }

    void onStop() {
        if (!subscriptions.isUnsubscribed()) {
            /*
            * Edited by sendz
            * Changed to .clear() to avoid subscriptions issue such as infinite loading on subscriptions
             */
            subscriptions.clear();
        }
    }
}
