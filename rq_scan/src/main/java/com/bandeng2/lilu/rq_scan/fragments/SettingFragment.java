package com.bandeng2.lilu.rq_scan.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bandeng2.lilu.rq_scan.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private View contentView;
    private Button buttonToM;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        contentView = inflater.inflate(R.layout.fragment_setting, container, false);
        buttonToM = (Button) contentView.findViewById(R.id.button_to_m);
        buttonToM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return contentView;
    }

}
