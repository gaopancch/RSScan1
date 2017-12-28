package com.bandeng2.lilu.rq_scan;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bandeng2.lilu.rq_scan.fragments.GenerateFragment;
import com.bandeng2.lilu.rq_scan.fragments.SacanFragment;
import com.bandeng2.lilu.rq_scan.fragments.SettingFragment;

public class MainNActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button button;
    private TabHost tabHost;
    private GenerateFragment generateFragment;
    private SettingFragment settingFragment;
    private SacanFragment scanFragment;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);
//        button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(MainNActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
        getCameraPermission();
        verifyStoragePermissions(this);
        tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                System.out.println("current tabid=" + tabId);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (TextUtils.equals("scan", tabId)) {
                    //add/replace fragment first
                    scanFragment = new SacanFragment();
                    ft.replace(android.R.id.tabcontent, (Fragment) scanFragment, "frag");
                } else if (TextUtils.equals("generate", tabId)) {
                    //add/replace fragment second
                    generateFragment =  new GenerateFragment();
                    ft.replace(android.R.id.tabcontent, generateFragment, "frag");
                } else if (TextUtils.equals("setting", tabId)) {
                    //add/replace fragment third
                    settingFragment = new SettingFragment();
                    ft.replace(android.R.id.tabcontent, settingFragment, "frag");
                }
                ft.commit();
            }
        });
        tabHost.addTab(tabHost.newTabSpec("scan").setIndicator("scan")
                .setContent(new DummyTabFactory(this)));
        tabHost.addTab(tabHost.newTabSpec("generate").setIndicator("generate")//setIndicator 设置标签样式
                .setContent(new DummyTabFactory(this))); //setContent 点击标签后触发
        tabHost.addTab(tabHost.newTabSpec("setting").setIndicator("setting")
                .setContent(new DummyTabFactory(this)));


    }

    static class DummyTabFactory implements TabHost.TabContentFactory {
        private Context context;
        public DummyTabFactory(Context ctx) {
            this.context = ctx;
        }
        @Override
        public View createTabContent(String tag) {//创建宽高均为0的view
            View v = new ImageView(context);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    private void getCameraPermission()
    {
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(MainNActivity.this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(MainNActivity.this,
                        new String[]{android.Manifest.permission.CAMERA},2);
            }else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
            }
        }else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
        }
    }


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
