package com.underground.protofy.NonWhiteboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.Protofy.protofy.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.underground.protofy.Preferences.AppPreferences;

/**
 * Handles displaying introductory tour of the app features.
 */
public class TourActivity extends AppIntro2 {

    protected AppPreferences appPrefs;
    protected boolean tutorialState;


    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.

        appPrefs = new AppPreferences(this);
        tutorialState = appPrefs.getIntroState();

        addSlide(AppIntroFragment.newInstance("Welcome Designers", "This is Protofy, A WYSIWYG editor for android. Please note, Protofy is currently in Early Access!",
                R.drawable.protofy_icon, Color.parseColor("#333333")));
        addSlide(AppIntroFragment.newInstance("The Edge Menu", "The Edge Menu will be your one stop shop for all of your layout needs. The Edge menu is split into Categories and control systems",
                R.drawable.edgemenucut, Color.parseColor("#ff9503")));
        addSlide(AppIntroFragment.newInstance("Edge Menu Categories", "Every view besides the base layout has three categories text options, appearance, and layout. Each Category has it's own options and can be displayed by clicking on a Greyed out Category.",
                R.drawable.layoutoptionscategoriescut, Color.parseColor("#24aaf2")));
        addSlide(AppIntroFragment.newInstance("Edge Menu Control Systems", "The current category\'s options (control systems) will be visible on the left side of the screen. Please visit the app documentation to know what each control system will do.",
                R.drawable.layoutoptionspreviewcut, Color.parseColor("#f99503")));
        addSlide(AppIntroFragment.newInstance("Double-Click To Your New Layout", "Queueing up the Edge Menu is always achieved using a quick double-click on any view you want to modify (even the base layout)",
                R.drawable.doubleclick, Color.parseColor("#24aaf2")));
        addSlide(AppIntroFragment.newInstance("All Control Systems Have A Preview", "Every option will have a preview of the selected view with real-time value changes.",
                R.drawable.viewpreviewcut, Color.parseColor("#ff9503")));
        addSlide(AppIntroFragment.newInstance("Early Access", "Protofy is like a good bottle wine, it gets better with age. This mean there will be multiple future updates and Protofy will constantly expand at no additional cost to early adopters!",
                R.drawable.earlyaccesscut, Color.parseColor("#333333")));


        // Hide Skip/Done button
        showDoneButton(true);
    }

    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        // Currently not displayed.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        if (tutorialState) {
            AppPreferences app = new AppPreferences(this);
            app.saveIntroState(false);
        }
        tutorialState = false;
        Intent intent = new Intent(this, IntroScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}