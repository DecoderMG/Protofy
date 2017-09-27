package com.underground.protofy.NonWhiteboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Protofy.protofy.R;
import com.underground.protofy.Whiteboard.MainActivity;

/**
 * A fragment containing our main app intro screen where the user can choose to read the guide or start creating their layout.
 */
public class IntroScreenActivityFragment extends Fragment {

    private View rootView;
    private Button whiteboardButton, guideButton;

    public IntroScreenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.intro_screen, container, false);
        guideButton = (Button) rootView.findViewById(R.id.guide_button);
        whiteboardButton = (Button) rootView.findViewById(R.id.whiteboard_btn);

        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideButton.setTextColor(Color.WHITE);
                guideButton.setBackgroundColor(Color.parseColor("#ff24aaf2"));
                Intent intent = new Intent(getActivity().getApplicationContext(), GuideActivity.class);
                startActivity(intent);
            }
        });
        whiteboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteboardButton.setTextColor(Color.WHITE);
                whiteboardButton.setBackgroundColor(Color.parseColor("#ff24aaf2"));
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void onResume() {
        super.onResume();
        whiteboardButton.setTextColor(Color.parseColor("#ff24aaf2"));
        whiteboardButton.setBackgroundColor(Color.parseColor("#333333"));
        guideButton.setTextColor(Color.parseColor("#ff24aaf2"));
        guideButton.setBackgroundColor(Color.parseColor("#333333"));
    }
}
