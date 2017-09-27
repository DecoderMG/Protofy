package com.underground.protofy.NonWhiteboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Protofy.protofy.R;

/**
 * Fragment containing the guidebook view.
 */
public class GuideActivityFragment extends Fragment {

    public GuideActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guidelayout, container, false);
    }
}
