package com.xiyan.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class BitmapUtils {

    /**
     * 多张图片拼接9宫格
     *
     * @param margin
     * @param bitmaps
     * @return
     */
    public static Bitmap addBitmaps(int margin, Bitmap... bitmaps) {
        int row = 1;
        int col = 0;
        int width = 0;
        int height = 0;
        int totalHeight = 0;
        int length = bitmaps.length;
        if (length > 3) {
            row = length % 3 == 0 ? length / 3 : length / 3 + 1;
            col = 3;
        } else {
            row = 1;
            col = length;
        }
        for (int i = 0; i < length; i++) {
            height = Math.max(height, bitmaps[i].getHeight());
        }
        totalHeight = height * row;
        totalHeight += (row - 1) * margin;

        for (int i = 0; i < col; i++) {
            width += bitmaps[i].getWidth();
            width += margin;
        }
        width -= margin;
        Bitmap result = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        for (int i = 0; i < row; i++) {
            int left = 0;
            for (int i1 = 0; i1 < col; i1++) {
                if (i * col + i1 >= length) {
                    break;
                }
                if (i > 0) {
                    if (i1 > 0) {
                        left += bitmaps[i * col + i1 - 1].getWidth();
                        left += margin;
                        int top = (height + margin) * i;
                        canvas.drawBitmap(bitmaps[i * col + i1], left, top, null);
                    } else {
                        left = 0;
                        left += margin;
                        int top = (height + margin) * i;
                        canvas.drawBitmap(bitmaps[i * col + i1], left, top, null);
                    }
                } else {
                    //第1行
                    if (i1 > 0) {
                        left += bitmaps[i1 - 1].getWidth();
                        left += margin;
                        canvas.drawBitmap(bitmaps[i1], left, 0, null);
                    } else {
                        left = 0;
                        left += margin;
                        canvas.drawBitmap(bitmaps[i1], left, 0, null);
                    }
                }
            }
        }
        return result;
    }

    public static Bitmap merge2Bitmaps(int margin, Bitmap... bmps) {
        int length = bmps.length;
        int height = 0, width = 0;
        for (Bitmap bmp : bmps) {
            height = Math.max(height, bmp.getHeight());
            width += bmp.getWidth();
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        for (int i = 0; i < bmps.length; i++) {
            if (i == 0) {
                canvas.drawBitmap(bmps[i],0,0,null);
            } else {
                canvas.drawBitmap(bmps[i],bmps[i-1].getWidth(),0,null);
            }
        }
        return result;
    }

    public static Bitmap merge3Bitmaps(int margin, Bitmap... bmps) {
        int length = bmps.length;
        int height = 0, width = 0;
        int mod=length%3;
        int row=2;
        if(mod==0){
            row=2;
        }else{
            row=(length-1)/3+2;
        }
        for (Bitmap bmp : bmps) {
            width = Math.max(width, bmp.getWidth());
        }
        height=bmps[0].getHeight()+bmps[0].getHeight()/2*(row-1);
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        for (int i = 0; i < bmps.length; i++) {
            if (i == 0) {
                canvas.drawBitmap(bmps[i],0,0,null);
            } else {
                int left=0,top=0;
                if(i==1 || i==3 || i==5){
                   left=0;
                   if(i==1){
                       top=bmps[0].getHeight();
                   }else if(i==3){
                       top=bmps[0].getHeight()+bmps[0].getHeight()/2;
                   }else if(i==5){
                       top=bmps[0].getHeight()*2;
                   }
                }else{
                    left=width/2;
                    if(i==2){
                        top=bmps[0].getHeight();
                    }else if(i==4){
                        top=bmps[0].getHeight()+bmps[0].getHeight()/2;
                    }else if(i==6){
                        top=bmps[0].getHeight()*2;
                    }
                }
                Bitmap newSizeBitmap = getNewSizeBitmap(bmps[i], width / 2, bmps[0].getHeight() / 2);
                canvas.drawBitmap(newSizeBitmap,left,top,null);
            }
        }
        return result;
    }
    public static Bitmap getNewSizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bit1Scale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
        return bit1Scale;
    }
}
