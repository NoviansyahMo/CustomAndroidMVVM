package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.models.CityListData;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemViewModelTest {

    @Test
    public void testShouldReturnCorrectCityName() throws Exception {
        CityListData data = new CityListData();
        data.setName("Jogja");
        ItemViewModel viewModel = new ItemViewModel(data);
        assertThat(viewModel.getCityName(), is("Jogja"));
    }

    @Test
    public void testShouldReturnCorrectCityDescription() throws Exception {
        CityListData data = new CityListData();
        data.setDescription("Never Ending Asia");
        ItemViewModel viewModel = new ItemViewModel(data);
        assertThat(viewModel.getDescription(), is("Never Ending Asia"));
    }

    @Test
    public void testHuldReturnCorrectImage() throws Exception {
        CityListData data = new CityListData();
        data.setBackground("file:///image/jogja.jpg");
        ItemViewModel viewModel = new ItemViewModel(data);
        assertThat(viewModel.getBackground(), is("file:///image/jogja.jpg"));
    }
}