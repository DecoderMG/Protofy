package com.underground.protofy.Preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.Protofy.protofy.R;

import java.util.List;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {
    public static Toolbar mToolBar;
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);


            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View content = root.getChildAt(0);
        LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.activity_prefs, null);

        root.removeAllViews();
        toolbarContainer.addView(content);
        root.addView(toolbarContainer);

        mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
        mToolBar.setTitle(getTitle());
        mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        if (fragmentName.equals(ButtonPreferenceFragment.class.getName())) {
            return true;
        } else if (fragmentName.equals(GeneralPreferenceFragment.class.getName())) {
            return true;
        } else if (fragmentName.equals(TextViewPreferenceFragment.class.getName())) {
            return true;
        } else if (fragmentName.equals(EditTextPreferenceFragment.class.getName())) {
            return true;
        } else if (fragmentName.equals(ImageViewPreferenceFragment.class.getName())) {
            return true;
        } else {
            return fragmentName.equals(RelativeLayoutPreferenceFragment.class.getName());
        }
        //return NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            LinearLayout toolbarContainer = (LinearLayout) View.inflate(getActivity(), R.layout.activity_prefs, null);

            mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
            mToolBar.setTitle("General Settings");
            mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }

    /**
     * This fragment shows textview preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class TextViewPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_textview);

            LinearLayout toolbarContainer = (LinearLayout) View.inflate(getActivity(), R.layout.activity_prefs, null);

            mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
            mToolBar.setTitle(getActivity().getTitle());
            mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            //bindPreferenceSummaryToValue(findPreference("textview_default_text"));
            bindPreferenceSummaryToValue(findPreference("textview_default_text"));
            bindPreferenceSummaryToValue(findPreference("textview_default_text_size"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_text_size"));
            bindPreferenceSummaryToValue(findPreference("textview_default_line_spacing"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_line_spacing"));
            bindPreferenceSummaryToValue(findPreference("pref_key_textview_default_font"));
            bindPreferenceSummaryToValue(findPreference("pref_key_textview_default_emphasis"));

            bindPreferenceSummaryToValue(findPreference("textview_default_max_width"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_height"));

            bindPreferenceSummaryToValue(findPreference("textview_default_top_padding"));
            bindPreferenceSummaryToValue(findPreference("textview_default_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("textview_default_left_padding"));
            bindPreferenceSummaryToValue(findPreference("textview_default_right_padding"));

            bindPreferenceSummaryToValue(findPreference("textview_default_max_top_padding"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_left_padding"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_right_padding"));

            bindPreferenceSummaryToValue(findPreference("textview_default_top_margin"));
            bindPreferenceSummaryToValue(findPreference("textview_default_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("textview_default_left_margin"));
            bindPreferenceSummaryToValue(findPreference("textview_default_right_margin"));

            bindPreferenceSummaryToValue(findPreference("textview_default_max_top_margin"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_left_margin"));
            bindPreferenceSummaryToValue(findPreference("textview_default_max_right_margin"));

            bindPreferenceSummaryToValue(findPreference("textview_default_parent_relation"));
            bindPreferenceSummaryToValue(findPreference("textview_default_width_wrapping"));
            bindPreferenceSummaryToValue(findPreference("textview_default_height_wrapping"));
        }
    }

    /**
     * This fragment shows button preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ButtonPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_button);
            LinearLayout toolbarContainer = (LinearLayout) View.inflate(getActivity(), R.layout.activity_prefs, null);

            mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
            mToolBar.setTitle(getActivity().getTitle());
            mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });


            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("button_default_text"));
            bindPreferenceSummaryToValue(findPreference("button_default_text_size"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_text_size"));
            bindPreferenceSummaryToValue(findPreference("button_default_line_spacing"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_line_spacing"));
            bindPreferenceSummaryToValue(findPreference("pref_key_button_default_font"));
            bindPreferenceSummaryToValue(findPreference("pref_key_button_default_emphasis"));

            bindPreferenceSummaryToValue(findPreference("button_default_max_width"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_height"));

            bindPreferenceSummaryToValue(findPreference("button_default_top_padding"));
            bindPreferenceSummaryToValue(findPreference("button_default_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("button_default_left_padding"));
            bindPreferenceSummaryToValue(findPreference("button_default_right_padding"));

            bindPreferenceSummaryToValue(findPreference("button_default_max_top_padding"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_left_padding"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_right_padding"));

            bindPreferenceSummaryToValue(findPreference("button_default_top_margin"));
            bindPreferenceSummaryToValue(findPreference("button_default_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("button_default_left_margin"));
            bindPreferenceSummaryToValue(findPreference("button_default_right_margin"));

            bindPreferenceSummaryToValue(findPreference("button_default_max_top_margin"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_left_margin"));
            bindPreferenceSummaryToValue(findPreference("button_default_max_right_margin"));

            bindPreferenceSummaryToValue(findPreference("button_default_parent_relation"));
            bindPreferenceSummaryToValue(findPreference("button_default_width_wrapping"));
            bindPreferenceSummaryToValue(findPreference("button_default_height_wrapping"));
        }
    }

    /**
     * This fragment shows edittext preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class EditTextPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_edittext);

            LinearLayout toolbarContainer = (LinearLayout) View.inflate(getActivity(), R.layout.activity_prefs, null);

            mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
            mToolBar.setTitle(getActivity().getTitle());
            mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            //bindPreferenceSummaryToValue(findPreference("sync_frequency"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_text"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_text_size"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_text_size"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_line_spacing"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_line_spacing"));
            bindPreferenceSummaryToValue(findPreference("pref_key_edittext_default_font"));
            bindPreferenceSummaryToValue(findPreference("pref_key_edittext_default_emphasis"));

            bindPreferenceSummaryToValue(findPreference("edittext_default_max_width"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_height"));

            bindPreferenceSummaryToValue(findPreference("edittext_default_top_padding"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_left_padding"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_right_padding"));

            bindPreferenceSummaryToValue(findPreference("edittext_default_max_top_padding"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_left_padding"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_right_padding"));

            bindPreferenceSummaryToValue(findPreference("edittext_default_top_margin"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_left_margin"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_right_margin"));

            bindPreferenceSummaryToValue(findPreference("edittext_default_max_top_margin"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_left_margin"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_max_right_margin"));

            bindPreferenceSummaryToValue(findPreference("edittext_default_parent_relation"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_width_wrapping"));
            bindPreferenceSummaryToValue(findPreference("edittext_default_height_wrapping"));
        }
    }
    /**
     * This fragment shows imageview preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ImageViewPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_imageview);

            LinearLayout toolbarContainer = (LinearLayout) View.inflate(getActivity(), R.layout.activity_prefs, null);

            mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
            mToolBar.setTitle(getActivity().getTitle());
            mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            bindPreferenceSummaryToValue(findPreference("imageview_default_width"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_height"));

            bindPreferenceSummaryToValue(findPreference("imageview_default_max_width"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_height"));

            bindPreferenceSummaryToValue(findPreference("imageview_default_top_padding"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_left_padding"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_right_padding"));

            bindPreferenceSummaryToValue(findPreference("imageview_default_max_top_padding"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_left_padding"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_right_padding"));

            bindPreferenceSummaryToValue(findPreference("imageview_default_top_margin"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_left_margin"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_right_margin"));

            bindPreferenceSummaryToValue(findPreference("imageview_default_max_top_margin"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_left_margin"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_max_right_margin"));

            bindPreferenceSummaryToValue(findPreference("imageview_default_parent_relation"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_width_wrapping"));
            bindPreferenceSummaryToValue(findPreference("imageview_default_height_wrapping"));
        }
    }

    /**
     * This fragment shows imageview preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class RelativeLayoutPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_relativelayout);

            LinearLayout toolbarContainer = (LinearLayout) View.inflate(getActivity(), R.layout.activity_prefs, null);

            mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
            mToolBar.setTitle(getActivity().getTitle());
            mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            //bindPreferenceSummaryToValue(findPreference("relativelayout_default_width"));
            //bindPreferenceSummaryToValue(findPreference("relativelayout_default_height"));

           // bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_width"));
            //bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_height"));

            bindPreferenceSummaryToValue(findPreference("relativelayout_default_top_padding"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_left_padding"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_right_padding"));

            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_top_padding"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_bottom_padding"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_left_padding"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_right_padding"));

            bindPreferenceSummaryToValue(findPreference("relativelayout_default_top_margin"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_left_margin"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_right_margin"));

            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_top_margin"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_bottom_margin"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_left_margin"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_max_right_margin"));

            bindPreferenceSummaryToValue(findPreference("relativelayout_default_parent_relation"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_width_wrapping"));
            bindPreferenceSummaryToValue(findPreference("relativelayout_default_height_wrapping"));
        }
    }
}
