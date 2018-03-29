package com.example.chasen.downloader.activities.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by chasen on 18-3-29.
 * <p>
 * MainActivity的present
 */

public class MainActivityPresent implements MainActivityContract.MainPresent {

    private static final int REQUEST_CODE = 1;

    private MainActivityContract.MainView mView;

    public MainActivityPresent(MainActivityContract.MainView view) {
        this.mView = view;
    }

    @Override
    public void checkPermission(Context context) {
        boolean isGranted =
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }

    }

    @Override
    public void checkPermissionResult(Context context, int requestCode) {
        if (requestCode == REQUEST_CODE) {
            boolean isGranted =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            if (!isGranted) {
                mView.showCheckPermissionResult();
            }
        }
    }
}
