package com.xys.libzxing.zxing.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

/**
 * Created by letv on 2018/1/3.
 */

public class DecodeUtils {

    /**
     * 解析二维码（使用解析RGB编码数据的方式）
     *
     * @param path
     * @return
     */
    public static Result decodeBarcodeRGB(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        Bitmap barcode = BitmapFactory.decodeFile(path, opts);
        Result result = decodeBarcodeRGB(barcode);
        barcode.recycle();
        barcode = null;
        return result;
    }

    /**
     * 解析二维码 （使用解析RGB编码数据的方式）
     *
     * @param barcode
     * @return
     */
    public static Result decodeBarcodeRGB(Bitmap barcode) {
        int width = barcode.getWidth();
        int height = barcode.getHeight();
        int[] data = new int[width * height];
        barcode.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        barcode.recycle();
        barcode = null;
        return result;
    }
}
