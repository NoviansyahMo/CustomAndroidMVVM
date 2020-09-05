package com.android.indie.school.cleancodemvvm.activity.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public class HomeViewModel extends BaseObservable {

    private boolean isInProgress = false;

    @Bindable
    public boolean isInProgress() {
        return isInProgress;
    }

    void setInProgress(boolean inProgress) {
        this.isInProgress = inProgress;
        notifyPropertyChanged(BR.inProgress);
    }

}
