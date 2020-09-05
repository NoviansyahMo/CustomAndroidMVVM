package com.android.indie.school.cleancodemvvm.activity.home;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HomeViewModelTest {

    private HomeViewModel viewModel;
    @Before
    public void setUp() throws Exception {
        viewModel = new HomeViewModel();
    }

    @Test
    public void testCheckIsInProgressTrue() throws Exception {
        viewModel.setInProgress(true);
        assertThat(viewModel.isInProgress(), is(true));
    }

    @Test
    public void testCheckIsInProgressFalse() throws Exception {
        viewModel.setInProgress(false);
        assertThat(viewModel.isInProgress(), is(false));
    }
}