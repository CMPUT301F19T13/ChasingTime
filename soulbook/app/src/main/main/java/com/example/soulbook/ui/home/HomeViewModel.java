package com.example.soulbook.ui.home;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.soulbook.R;

/**
 * This class shows Home page View
 */
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    /**
     * shows the fragment in home page if exist
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}