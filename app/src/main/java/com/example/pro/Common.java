package com.example.pro;

import android.content.Context;
import android.os.Environment;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {

    public static final int GRID_COUNT = 2;


    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
            File.separator + "WhatsApp/Media/.Statuses");

    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

    public static String APP_DIR;

    public static void copyFile(Status status, Context context, RelativeLayout container) {

        File file = new File(Common.APP_DIR);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Snackbar.make(container, "Something went wrong", Snackbar.LENGTH_SHORT).show();
            }
        }

        String fileName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        if (status.isVideo()) {
            fileName = "VID_" + currentDateTime + ".mp4";
        } else {
            fileName = "IMG_" + currentDateTime + ".jpg";
        }

        File destFile = new File(file + File.separator + fileName);

        try {

            org.apache.commons.io.FileUtils.copyFile(status.getFile(), destFile);
            destFile.setLastModified(System.currentTimeMillis());
            new SingleMediaScanner(context, file);
            //showNotification(context, container, destFile, status);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
