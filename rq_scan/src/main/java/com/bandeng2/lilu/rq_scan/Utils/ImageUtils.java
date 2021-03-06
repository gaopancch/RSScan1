package com.bandeng2.lilu.rq_scan.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.bandeng2.lilu.rq_scan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by letv on 2017/12/28.
 */

public class ImageUtils {
        //保存文件到指定路径
        public static boolean saveImageToGallery(Context context, Bitmap bmp,String imageName) {
            if(TextUtils.isEmpty(imageName)){
                imageName = "扫仙儿-"+System.currentTimeMillis();
            }
            // 首先保存图片
            String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "扫仙儿/";
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = imageName + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                //通过io流的方式来压缩保存图片
                boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();

                //把文件插入到系统图库
                //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                if (isSuccess) {
                    Toast.makeText(context,context.getString(R.string.image_saved), Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(context,context.getString(R.string.error_read_write_sd_permission), Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,context.getString(R.string.error_read_write_sd_permission), Toast.LENGTH_SHORT).show();
            return false;
        }
}
