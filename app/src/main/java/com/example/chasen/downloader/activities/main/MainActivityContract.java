package com.example.chasen.downloader.activities.main;

import android.content.Context;

/**
 * Created by chasen on 18-3-29.
 * 主界面的契约
 */

public interface MainActivityContract {

    interface MainView {
        void showCheckPermissionResult();
    }

    interface MainPresent {
        void checkPermission(Context context);

        void checkPermissionResult(Context context, int requestCode);
    }
}
