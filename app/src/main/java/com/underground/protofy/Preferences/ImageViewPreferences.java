package com.underground.protofy.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Dakota on 10/17/2015.
 * Handles ImageView default preferences.
 */
public class ImageViewPreferences {
    public static final String KEY_PREF_IMAGEVIEW_MAX_TOP_PADDING = "imageview_default_max_top_padding";
    public static final String KEY_PREF_IMAGEVIEW_MAX_BOTTOM_PADDING = "imageview_default_max_bottom_padding";
    public static final String KEY_PREF_IMAGEVIEW_MAX_LEFT_PADDING = "imageview_default_max_left_padding";
    public static final String KEY_PREF_IMAGEVIEW_MAX_RIGHT_PADDING = "imageview_default_max_right_padding";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_TOP_PADDING = "imageview_default_top_padding";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_BOTTOM_PADDING = "imageview_default_bottom_padding";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_LEFT_PADDING = "imageview_default_left_padding";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_RIGHT_PADDING = "imageview_default_right_padding";
    public static final String KEY_PREF_IMAGEVIEW_PARENT_RELATION = "imageview_default_parent_relation";
    public static final String KEY_PREF_IMAGEVIEW_MAX_TOP_MARGIN = "imageview_default_max_top_margin";
    public static final String KEY_PREF_IMAGEVIEW_MAX_BOTTOM_MARGIN = "imageview_default_max_bottom_margin";
    public static final String KEY_PREF_IMAGEVIEW_MAX_LEFT_MARGIN = "imageview_default_max_left_margin";
    public static final String KEY_PREF_IMAGEVIEW_MAX_RIGHT_MARGIN = "imageview_default_max_right_margin";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_TOP_MARGIN = "imageview_default_top_margin";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_BOTTOM_MARGIN = "imageview_default_bottom_margin";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_LEFT_MARGIN = "imageview_default_left_margin";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_RIGHT_MARGIN = "imageview_default_right_margin";
    public static final String KEY_PREF_IMAGEVIEW_LAYOUT_WRAPPING_WIDTH = "imageview_default_width_wrapping";
    public static final String KEY_PREF_IMAGEVIEW_LAYOUT_WRAPPING_HEIGHT = "imageview_default_height_wrapping";
    public static final String KEY_PREF_IMAGEVIEW_MAX_WIDTH = "imageview_default_max_width";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_WIDTH = "imageview_default_width";
    public static final String KEY_PREF_IMAGEVIEW_MAX_HEIGHT = "imageview_default_max_height";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_HEIGHT = "imageview_default_height";
    public static final String KEY_PREF_IMAGEVIEW_DEFAULT_TINT_MODE = "imageview_default_tint_mode";

    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public ImageViewPreferences(Context context) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this._prefsEditor = _sharedPrefs.edit();
    }

    //ImageView Setting Options

    public int getMaxImageViewTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_TOP_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxImageViewBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_BOTTOM_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxImageViewLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_LEFT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxImageViewRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_RIGHT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultImageViewTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_TOP_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewTopPadding(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_TOP_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultImageViewBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_BOTTOM_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewBottomPadding(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_BOTTOM_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultImageViewLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_LEFT_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewLeftPadding(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_LEFT_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultImageViewRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_RIGHT_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewRightPadding(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_RIGHT_PADDING, text);
        _prefsEditor.commit();
    }

    public String getImageViewParentRelation() {
        return _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_PARENT_RELATION, "0");
    }

    public int getMaxImageViewTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_TOP_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxImageViewBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_BOTTOM_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxImageViewLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_LEFT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxImageViewRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_RIGHT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultImageViewTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_TOP_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewTopMargin(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_TOP_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultImageViewBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_BOTTOM_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewBottomMargin(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_BOTTOM_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultImageViewLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_LEFT_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewLeftMargin(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_LEFT_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultImageViewRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_RIGHT_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultImageViewRightMargin(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_RIGHT_MARGIN, text);
        _prefsEditor.commit();
    }

    public String getImageViewLayoutWrappingWidth() {
        return _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_LAYOUT_WRAPPING_WIDTH, "0");
    }

    public String getImageViewLayoutWrappingHeight() {
        return _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_LAYOUT_WRAPPING_HEIGHT, "0");
    }

    public void setMaxWidth(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_MAX_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getMaxWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_WIDTH, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultWidth(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getDefaultWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_WIDTH, "100");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setMaxHeight(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_MAX_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getMaxHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_MAX_HEIGHT, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultHeight(String text) {
        _prefsEditor.putString(KEY_PREF_IMAGEVIEW_DEFAULT_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getDefaultHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_HEIGHT, "100");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public String getImageViewDefaultScaleType() {
        return _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_PARENT_RELATION, "0");
    }
    public String getDefaultTintMode() {
        return _sharedPrefs.getString(KEY_PREF_IMAGEVIEW_DEFAULT_TINT_MODE,"0");
    }
}
