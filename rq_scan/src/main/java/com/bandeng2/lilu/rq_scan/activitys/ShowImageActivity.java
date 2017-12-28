package com.bandeng2.lilu.rq_scan.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bandeng2.lilu.rq_scan.R;
import com.bandeng2.lilu.rq_scan.adapters.ChildAdapter;
import com.bandeng2.lilu.rq_scan.fragments.GenerateFragment;

import java.util.List;

public class ShowImageActivity extends Activity {

    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        mGridView = (GridView) findViewById(R.id.child_grid);
        list = getIntent().getStringArrayListExtra("data");

        adapter = new ChildAdapter(this, list, mGridView);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imagepath = list.get(position);
                mIntent = new Intent();
                if(TextUtils.isEmpty(imagepath)){
                    mIntent.putExtra("imagepath", "");
                    setResult(GenerateFragment.SET_IMAGE_BG_error, mIntent);
                }else{
                    mIntent.putExtra("imagepath", list.get(position));
                    setResult(GenerateFragment.SET_IMAGE_BG_OK, mIntent);

                }
                finish();
            }
        });

    }

//    @Override
//    public void onBackPressed() {
////        Toast.makeText(this, "选中 " + adapter.getSelectItems().size() + " item", Toast.LENGTH_LONG).show();
//        super.onBackPressed();
//    }

    @Override
    protected void onDestroy() {
        if(mIntent == null){
            Intent mIntent = new Intent();
            mIntent.putExtra("imagepath", "");
            // 设置结果，并进行传送
            setResult(GenerateFragment.SET_IMAGE_BG_error, mIntent);
        }
        super.onDestroy();
    }
}

