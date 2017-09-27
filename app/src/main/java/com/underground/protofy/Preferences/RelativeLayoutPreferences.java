package com.underground.protofy.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

/**
 * Created by Dakota on 10/18/2015.
 * Handles default RelativeLayout preferences.
 */
public class RelativeLayoutPreferences {
    public static final String KEY_PREF_RELATIVELAYOUT_COLOR = "relativelayout_default_color";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_TOP_PADDING = "relativelayout_default_max_top_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_BOTTOM_PADDING = "relativelayout_default_max_bottom_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_LEFT_PADDING = "relativelayout_default_max_left_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_RIGHT_PADDING = "relativelayout_default_max_right_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_TOP_PADDING = "relativelayout_default_top_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_BOTTOM_PADDING = "relativelayout_default_bottom_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_LEFT_PADDING = "relativelayout_default_left_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_RIGHT_PADDING = "relativelayout_default_right_padding";
    public static final String KEY_PREF_RELATIVELAYOUT_PARENT_RELATION = "relativelayout_default_parent_relation";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_TOP_MARGIN = "relativelayout_default_max_top_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_BOTTOM_MARGIN = "relativelayout_default_max_bottom_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_LEFT_MARGIN = "relativelayout_default_max_left_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_RIGHT_MARGIN = "relativelayout_default_max_right_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_TOP_MARGIN = "relativelayout_default_top_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_BOTTOM_MARGIN = "relativelayout_default_bottom_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_LEFT_MARGIN = "relativelayout_default_left_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_RIGHT_MARGIN = "relativelayout_default_right_margin";
    public static final String KEY_PREF_RELATIVELAYOUT_LAYOUT_WRAPPING_WIDTH = "relativelayout_default_width_wrapping";
    public static final String KEY_PREF_RELATIVELAYOUT_LAYOUT_WRAPPING_HEIGHT = "relativelayout_default_height_wrapping";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_WIDTH = "relativelayout_default_max_width";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_WIDTH = "relativelayout_default_width";
    public static final String KEY_PREF_RELATIVELAYOUT_MAX_HEIGHT = "relativelayout_default_max_height";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_HEIGHT = "relativelayout_default_height";
    public static final String KEY_PREF_RELATIVELAYOUT_DEFAULT_TINT_MODE = "relativelayout_default_tint_mode";

    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public RelativeLayoutPreferences(Context context) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this._prefsEditor = _sharedPrefs.edit();
    }

    //RelativeLayout Setting Options

    public int getRelativeLayoutColor() {
        return _sharedPrefs.getInt(KEY_PREF_RELATIVELAYOUT_COLOR, Color.parseColor("#333333"));
    }

    public int getMaxRelativeLayoutTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_TOP_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxRelativeLayoutBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_BOTTOM_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxRelativeLayoutLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_LEFT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxRelativeLayoutRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_RIGHT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultRelativeLayoutTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_TOP_PADDING, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutTopPadding(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_TOP_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultRelativeLayoutBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_BOTTOM_PADDING, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutBottomPadding(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_BOTTOM_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultRelativeLayoutLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_LEFT_PADDING, "24");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutLeftPadding(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_LEFT_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultRelativeLayoutRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_RIGHT_PADDING, "24");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutRightPadding(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_RIGHT_PADDING, text);
        _prefsEditor.commit();
    }

    public String getRelativeLayoutParentRelation() {
        return _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_PARENT_RELATION, "0");
    }

    public int getMaxRelativeLayoutTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_TOP_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxRelativeLayoutBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_BOTTOM_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxRelativeLayoutLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_LEFT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxRelativeLayoutRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_RIGHT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultRelativeLayoutTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_TOP_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutTopMargin(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_TOP_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultRelativeLayoutBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_BOTTOM_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutBottomMargin(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_BOTTOM_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultRelativeLayoutLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_LEFT_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutLeftMargin(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_LEFT_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultRelativeLayoutRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_RIGHT_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultRelativeLayoutRightMargin(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_RIGHT_MARGIN, text);
        _prefsEditor.commit();
    }

    public String getRelativeLayoutLayoutWrappingWidth() {
        return _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_LAYOUT_WRAPPING_WIDTH, "0");
    }

    public String getRelativeLayoutLayoutWrappingHeight() {
        return _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_LAYOUT_WRAPPING_HEIGHT, "0");
    }

    public void setMaxWidth(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_MAX_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getMaxWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_WIDTH, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultWidth(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getDefaultWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_WIDTH, "100");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setMaxHeight(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_MAX_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getMaxHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_MAX_HEIGHT, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultHeight(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getDefaultHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_HEIGHT, "100");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultTintMode(String text) {
        _prefsEditor.putString(KEY_PREF_RELATIVELAYOUT_DEFAULT_TINT_MODE, text);
        _prefsEditor.commit();
    }
    public String getDefaultTintMode() {
        return _sharedPrefs.getString(KEY_PREF_RELATIVELAYOUT_DEFAULT_TINT_MODE, "0");
    }
}
