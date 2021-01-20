package com.edumate.draknightwallpaper;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.annotation.Nullable;

public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
