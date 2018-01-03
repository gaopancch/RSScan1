package com.bandeng2.lilu.rq_scan.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bandeng2.lilu.rq_scan.R;
import com.bandeng2.lilu.rq_scan.activitys.ImageScanActivity;
import com.bandeng2.lilu.rq_scan.activitys.WebActivity;
import com.bandeng2.lilu.rq_scan.view.QRCodeScanButtonView;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.decode.DecodeUtils;

import java.util.regex.Pattern;

import javax.xml.transform.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class SacanFragment extends Fragment {

    private TextView titleText;
    private View contentView;
    private TextView resultText;
    private QRCodeScanButtonView qrCodeScanButtonView;
    private QRCodeScanButtonView qrCodeScanNativeImageButton;


    public SacanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_sacan, container, false);
        titleText = (TextView) contentView.findViewById(R.id.title_text);
        resultText =  (TextView) contentView.findViewById(R.id.result_textView);
        qrCodeScanButtonView = (QRCodeScanButtonView) contentView.findViewById(R.id.qr_button);
        qrCodeScanNativeImageButton = (QRCodeScanButtonView)contentView.findViewById(R.id.qr_scan_native_btn);
        qrCodeScanButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                 开启ZXing库中可以扫描的二维码的Activity
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                // 要有返回结果
                startActivityForResult(intent,1);
            }
        });

        qrCodeScanNativeImageButton.setText(getResources().getString(R.string.fragment_scan_go_scan_native_image));
        qrCodeScanNativeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ImageScanActivity.class);
                startActivityForResult(intent,ImageScanActivity.GET_IMAGE_REQUEST_CODE);
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

        }else if(requestCode == ImageScanActivity.GET_IMAGE_REQUEST_CODE){
            if(resultCode == GenerateFragment.SET_IMAGE_BG_OK){
                com.google.zxing.Result result =DecodeUtils.decodeBarcodeRGB(data.getStringExtra("imagepath"));
                if(result== null){
                    Toast.makeText(getActivity(),"无法识别",Toast.LENGTH_LONG).show();
                }else {
                    String resultContent = result.getText();
                    resultText.setText(resultContent);
                }
            }else{
                Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
            }
        }
    }


}
