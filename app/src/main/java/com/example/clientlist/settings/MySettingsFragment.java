package com.example.clientlist.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.clientlist.R;

public class MySettingsFragment  extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_settings, rootKey);
    }
}
