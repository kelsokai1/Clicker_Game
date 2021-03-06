package edu.mssucis385.clickergame;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String PREFERENCE_THEME = "pref_theme";
    public static String PREFERENCE_MUSIC = "music_pref";
    private boolean isPlaying;
    private SharedPreferences sharedPrefs;

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREFERENCE_THEME)) {
            // Recreate the activity so the theme takes effect
            getActivity().recreate();
        }
        else if (key.equals(PREFERENCE_MUSIC)){
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        // Access the default shared prefs
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


    }

}