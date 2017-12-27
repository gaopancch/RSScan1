package com.bandeng2.lilu.rq_scan;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class SacanFragment extends Fragment {

    private Button buttonToScan;
    private View contentView;
    private TextView resultText;


    public SacanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_sacan, container, false);
        buttonToScan = (Button) contentView.findViewById(R.id.button2);
        resultText =  (TextView) contentView.findViewById(R.id.result_textView);
        buttonToScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                 开启ZXing库中可以扫描的二维码的Activity
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                // 要有返回结果
                startActivityForResult(intent,1);
            }
        });
        return contentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            // 获取返回结果
            String result = data.getStringExtra("result");
            // 将结果设置给TextView
            resultText.setText(result);
            Pattern pattern = Pattern
                    .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
            if(pattern.matcher(result).matches()){
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",result);
                startActivity(intent);
            }

        }
    }

}
