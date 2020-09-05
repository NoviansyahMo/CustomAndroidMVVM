package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.android.indie.school.cleancodemvvm.networking.NetworkError;
import com.android.indie.school.cleancodemvvm.networking.Service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    private static final Subscription SUBSCRIPTION = new Subscription() {
        @Override
        public void unsubscribe() {

        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    };

    @Mock
    HomeViewModel homeViewModel;

    @Mock
    ItemViewModel itemViewModel;

    @Mock
    Service service;

    @Mock
    HomeView view;

    private CompositeSubscription subscriptions;

    private HomePresenter presenter;

    @Before
    public void setUp() throws Exception {
        subscriptions = new CompositeSubscription();
        presenter = new HomePresenter(service);
        presenter.setView(view);
        presenter.setViewModel(homeViewModel);
    }

    @Test
    public void testShouldInvokeHomeViewModelSetInProgressTrueWhenShowProgressWaitTriggered() throws Exception {
        presenter.showProgressWait();
        verify(homeViewModel).setInProgress(true);
    }

    @Test
    public void testShouldInvokeHomeViewModelSetInProgressFalseWhenRemoveProgressWaitTriggered() throws Exception {
        presenter.removeProgressWait();
        verify(homeViewModel).setInProgress(false);
    }

    @Test
    public void testShouldGetCityList() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Service.GetCityListCallback) invocation.getArguments()[0])
                        .onSuccess(new CityListResponse());
                return SUBSCRIPTION;
            }
        }).when(service).getCityList(any(Service.GetCityListCallback.class));

        presenter.getCityList();
        verify(homeViewModel).setInProgress(true);
        verify(homeViewModel).setInProgress(false);
        verify(view).onSuccessFetchData(new CityListResponse());
    }

    @Test
    public void testShouldGiveErrorResponseIfGetCityListFailed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Service.GetCityListCallback) invocation.getArguments()[0])
                        .onError(new NetworkError(new Throwable("Something went wrong! Please try again.")));
                return SUBSCRIPTION;
            }
        }).when(service).getCityList(any(Service.GetCityListCallback.class));

        presenter.getCityList();
        verify(homeViewModel).setInProgress(true);
        verify(homeViewModel).setInProgress(false);
        verify(view).onErrorFetchData("Something went wrong! Please try again.");
    }

    @Test
    public void testShouldInvokeViewFetchDataWhenReloadCityListTriggered() throws Exception {
        presenter.reloadCityList();

        verify(view).fetchData();
    }

    @Test
    public void testShouldClearSubscription() throws Exception {
        presenter.onStop();
        assertThat(subscriptions.hasSubscriptions(), is(false));
    }
}