package hv.anwb.nl.wegenwachtthegame2;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;


public class MyPreferenceFragment extends PreferenceFragment {

    private String TAG = "MyPreferenceFragment";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings_fragment);
        setupSettings();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setupSettings() {
        Log.i(TAG, "setupSettings()");
        setupAppVersionPreference();
    }

    private void setupAppVersionPreference() {
        Log.i(TAG, "setupAppVersionPreference()");
        Preference versie = findPreference("versie");
        String versienaam = "";
        try {
            versienaam = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(TAG, "Versie naam niet opgehaald" + e);
        }
        versie.setSummary(versienaam);
    }

}