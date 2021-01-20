package com.edumate.draknightwallpaper;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.DrawableRes;

import java.util.Calendar;

public class MyWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new MyWallpaperEngine();
    }

    class MyWallpaperEngine extends Engine {
        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        private int width;
        int height;
        private boolean visible = true;
        private boolean touchEnabled;

        MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", false);
            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if (touchEnabled) {
                float x = event.getX();
                float y = event.getY();
                SurfaceHolder holder = getSurfaceHolder();
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas();
                    if (canvas != null) {
                        // trySomething
                        drawPic(canvas);
                    }
                } finally {
                    if (canvas != null)
                        holder.unlockCanvasAndPost(canvas);
                }
                super.onTouchEvent(event);
            }
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawPic(canvas);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 5000);
            }
        }

        private void drawPic(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            Drawable d = getResources().getDrawable(getPic());
            d.setBounds(0, 0, width, height);
            d.draw(canvas);
        }

        private @DrawableRes
        int getPic() {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (hour == 5 || hour == 19)
                return R.drawable.dwan;
            else if (hour > 5 && hour < 19)
                return R.drawable.day;
            else
                return R.drawable.night;
        }
    }

}
