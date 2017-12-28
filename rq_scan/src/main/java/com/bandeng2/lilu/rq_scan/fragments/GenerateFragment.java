package com.bandeng2.lilu.rq_scan.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bandeng2.lilu.rq_scan.R;
import com.bandeng2.lilu.rq_scan.Utils.ImageUtils;
import com.bandeng2.lilu.rq_scan.activitys.ImageScanActivity;
import com.bandeng2.lilu.rq_scan.activitys.WebActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateFragment extends Fragment {

    private EditText editText;//需要生成二维码的内容输入框
    private Button buttonGenerate;//生成二维码按钮
    private Button buttonCenterChange;//自定义二维码中心图片按钮
    private View contentView;//从xml获取控件用view
    private ImageView image;//显示二维码用的imageView
    private AlertDialog.Builder saveQRImageAlertBulider;//保存二维码到本地时候的对话框
    private EditText saveNameEditText;//保存二维码到本时候对话框的中的命名输入框
    private Bitmap qrBitmap;//保存二维码时候，当前二维码的图片
    private Bitmap qrBgBitmap;//二维码中心的图片
    public static  int SET_IMAGE_BG_OK = 3301;
    public static  int SET_IMAGE_BG_error = 3301;
    private Activity parentActivity;


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
        buttonCenterChange = (Button)contentView.findViewById(R.id.button_change_center_image);
        image = (ImageView) contentView.findViewById(R.id.imageView);
        parentActivity = getActivity();
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create(view);
            }
        });
        buttonCenterChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentActivity, ImageScanActivity.class);
                startActivityForResult(intent,ImageScanActivity.GET_IMAGE_REQUEST_CODE);

            }
        });
        qrBgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.saoxianer);
        //默认二维码图片为 扫仙儿 内容 加 本程序启动图标
        qrBitmap = EncodingUtils.createQRCode(getString(R.string.app_name),500,500,qrBgBitmap);
        image.setImageBitmap(qrBitmap);
        //长按二维码保存二维码到本地图片
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                saveNameEditText = new EditText(parentActivity);
                saveQRImageAlertBulider = new AlertDialog.Builder(parentActivity)
                        .setTitle(parentActivity.getString(R.string.qr_image_saved))
                        .setMessage(parentActivity.getString(R.string.qr_image_saved_message))
                        .setView(saveNameEditText)
                        .setPositiveButton(parentActivity.getString(R.string.assuer), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                        saveImageToNative(bitmap,saveNameEditText.getText().toString());
                                ImageUtils.saveImageToGallery(parentActivity,
                                        qrBitmap,saveNameEditText.getText().toString());
                            }
                        });
//                AlertDialog alertDialog =
                        saveQRImageAlertBulider.show();
//                alertDialog.dismiss();
                return false;
            }
        });
        return contentView;
    }

    /**生产二维码*/
    private void create(View view){
        String result = editText.getText().toString();
        if (!TextUtils.isEmpty(result)){
            qrBitmap = EncodingUtils.createQRCode(result,500,500,
                    qrBgBitmap);
            image.setImageBitmap(qrBitmap);
        }else{
            if(view == null){
                qrBitmap = EncodingUtils.createQRCode(parentActivity.getString(R.string.app_name),500,500,
                        qrBgBitmap);
                image.setImageBitmap(qrBitmap);
            }else {
                // 提示
                Toast.makeText(parentActivity, parentActivity.getString(R.string.input_not_null), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageScanActivity.GET_IMAGE_REQUEST_CODE){
            if(resultCode == SET_IMAGE_BG_OK){
                qrBgBitmap = BitmapFactory.decodeFile(data.getStringExtra("imagepath"));
                create(null);
                Toast.makeText(parentActivity,"设置成功",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(parentActivity,"设置失败",Toast.LENGTH_LONG).show();
            }
        }
    }

}
