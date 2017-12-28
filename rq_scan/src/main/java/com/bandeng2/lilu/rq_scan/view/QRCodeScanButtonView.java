package com.bandeng2.lilu.rq_scan.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bandeng2.lilu.rq_scan.R;

/**
 * Created by letv on 2017/12/28.
 */

public class QRCodeScanButtonView extends LinearLayout {

    private ImageView imageView;

    private TextView textView;

    private Context mContext;

    private OnClickListener onClickListener;

    public QRCodeScanButtonView(Context context) {
        super(context);
        mContext =context;
        init();
    }

    public QRCodeScanButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext =context;
        init();

    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.qrcode_scan_button_view_layout, this, true);
        imageView = (ImageView) root.findViewById(R.id.qr_image_view);
        textView = (TextView) root.findViewById(R.id.qr_text_view);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });
    }

    public void setOnClickListener(OnClickListener l){
        onClickListener = l;
    }

}
