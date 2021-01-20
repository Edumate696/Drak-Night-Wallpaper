package com.edumate.draknightwallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 16) {
            String p = getPackageName();
            String c = getPackageName() + ".MyWallpaperService";
            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
        } else {
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
        startActivityForResult(intent, 1);
        finish();
    }
}
