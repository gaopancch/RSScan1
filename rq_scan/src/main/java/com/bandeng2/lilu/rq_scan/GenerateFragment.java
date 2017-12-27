package com.bandeng2.lilu.rq_scan;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xys.libzxing.zxing.encoding.EncodingUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateFragment extends Fragment {

    private EditText editText;
    private Button buttonGenerate;
    private View contentView;
    private ImageView image;


    public GenerateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_generate, container, false);
        editText = (EditText) contentView.findViewById(R.id.editText);
        buttonGenerate = (Button) contentView.findViewById(R.id.button_generate);
        image = (ImageView) contentView.findViewById(R.id.imageView);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create(view);
            }
        });
        return contentView;
    }

    /**生产二维码*/
    private void create(View view){
        String result = editText.getText().toString();
        if (!TextUtils.isEmpty(result)){
            Bitmap bitmap = EncodingUtils.createQRCode(result,500,500,
                    BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
            image.setImageBitmap(bitmap);
        }else{
            // 提示
            Toast.makeText(getActivity(),"输入不能为空",Toast.LENGTH_LONG).show();

        }

    }

}
