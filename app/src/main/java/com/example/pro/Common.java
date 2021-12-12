package com.example.pro;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Common {

    public static final int GRID_COUNT = 1;

    private static final String CHANNEL_NAME = "HASEEB";


    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
            File.separator + "WhatsApp/Media/.Statuses");

    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

    public static String APP_DIR;

    public static void copyFile(Status status, Context context, RelativeLayout container, String s) {
        File file = new File(s);
        APP_DIR = s;
        System.out.println(s);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Snackbar.make(container, "Something went wrong", Snackbar.LENGTH_SHORT).show();
            }
        }

        File direct = new File(Environment.getExternalStorageDirectory() + "/WhatsSaver");

        if (!direct.exists()) {
            File Directory = new File("/sdcard/WhatsSaver/");
            Directory.mkdirs();
        }

        String fileName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        if (status.isVideo()) {
            fileName = "VID_" + currentDateTime + ".mp4";
        } else {
            fileName = "IMG_" + currentDateTime + ".jpg";
        }
        File file1 = new File("/sdcard/WhatsSaver/", fileName);
        Uri fileUri= Uri.fromFile(status.getFile());
        String filePath = status.getFile().getPath();
        //Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        saveFile(fileUri, file1);
//        try {
//            FileOutputStream out = new FileOutputStream(file1);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //File destFile = new File(file + File.separator + "/files/"+fileName);
        //System.out.println(destFile.toString());


       // MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, fileName , "None");
        Snackbar.make(container, "Image Saved!!!", Snackbar.LENGTH_SHORT).show();
        showNotification(context, fileName);

    }

    private static void showNotification(Context context, String fileName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel(context);
        }


        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, CHANNEL_NAME);

        notification.setSmallIcon(R.drawable.ic_file_download_black)
                .setContentTitle(fileName)
                .setContentText("File Saved to Gallery")
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(new Random().nextInt(), notification.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void makeNotificationChannel(Context context) {

        NotificationChannel channel = new NotificationChannel(Common.CHANNEL_NAME, "Saved", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setShowBadge(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }


    private static void saveFile(Uri sourceUri, File destination){
        try {
            File source = new File(sourceUri.getPath());
            FileChannel src = new FileInputStream(source).getChannel();
            FileChannel dst = new FileOutputStream(destination).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } catch (IOException ex) {
            ex.printStackTrace();
    }
}

}
