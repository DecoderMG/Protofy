package com.underground.protofy.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Dakota on 9/13/2015.
 * Handles controlling app states as well as tutorial progress.
 */
public class AppPreferences {
    public static final String KEY_PREFS_TUTORIAL_STATE = "tutorial_completed";
    public static final String KEY_PREFS_INTRO_STATE = "intro_completed";
    public static final String KEY_PREFS_FIRST_APP_START = "first_app_start";

    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public AppPreferences(Context context) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this._prefsEditor = _sharedPrefs.edit();
    }

    //General settings page
    public boolean getTutorialState() {
        return _sharedPrefs.getBoolean(KEY_PREFS_TUTORIAL_STATE, true);
    }

    public boolean getIntroState() {
        return _sharedPrefs.getBoolean(KEY_PREFS_INTRO_STATE, true);
    }

    public boolean isFirstAppStart() {
        return _sharedPrefs.getBoolean(KEY_PREFS_FIRST_APP_START, true);
    }

    public void saveTutorialState(boolean text) {
        _prefsEditor.putBoolean(KEY_PREFS_TUTORIAL_STATE, text);
        _prefsEditor.commit();
    }

    public void saveIntroState(boolean text) {
        _prefsEditor.putBoolean(KEY_PREFS_INTRO_STATE, text);
        _prefsEditor.commit();
    }

    public void saveFirstAppStart(boolean text) {
        _prefsEditor.putBoolean(KEY_PREFS_FIRST_APP_START, text);
        _prefsEditor.commit();
    }
}