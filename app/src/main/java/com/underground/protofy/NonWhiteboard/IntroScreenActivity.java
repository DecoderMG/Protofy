package com.underground.protofy.NonWhiteboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.Protofy.protofy.R;
import com.underground.protofy.Preferences.AppPreferences;
import com.underground.protofy.Preferences.ButtonPreferences;
import com.underground.protofy.Preferences.EditTextPreferences;
import com.underground.protofy.Preferences.SettingsActivity;
import com.underground.protofy.Preferences.TextViewPreferences;

/**
 * Activity to display intro fragments.
 */
public class IntroScreenActivity extends AppCompatActivity {

    protected AppPreferences appPrefs;
    protected boolean tutorialState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        appPrefs = new AppPreferences(this);
        tutorialState = appPrefs.getIntroState();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if(appPrefs.isFirstAppStart()) {
            TextViewPreferences tvPrefs = new TextViewPreferences(this);
            tvPrefs.setMaxWidth(Float.toString(metrics.xdpi));
            tvPrefs.setMaxHeight(Float.toString(metrics.ydpi));

            ButtonPreferences bPrefs = new ButtonPreferences(this);
            bPrefs.setMaxWidth(Float.toString(metrics.xdpi));
            bPrefs.setMaxHeight(Float.toString(metrics.ydpi));

            EditTextPreferences edPrefs = new EditTextPreferences(this);
            edPrefs.setMaxWidth(Float.toString(metrics.xdpi));
            edPrefs.setMaxHeight(Float.toString(metrics.ydpi));

            appPrefs.saveFirstAppStart(false);
        }

        if (tutorialState) {
            Intent intent = new Intent(this, TourActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
