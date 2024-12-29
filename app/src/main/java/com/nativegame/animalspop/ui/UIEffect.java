package com.nativegame.animalspop.ui;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Oscar Liang on 2022/09/18
 */

public class UIEffect {

    private static final BounceInterpolator BOUNCE_INTERPOLATOR = new BounceInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator();

    private UIEffect() {
    }

    public static void createColorFilter(View view) {
        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        view.invalidate();
    }

    public static void clearColorFilter(View view) {
        view.getBackground().clearColorFilter();
        view.invalidate();
    }

    public static void createButtonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.animate()
                                .setStartDelay(0)
                                .setDuration(300)
                                .scaleX(0.8f)
                                .scaleY(0.8f)
                                .setInterpolator(BOUNCE_INTERPOLATOR);
                        createColorFilter(view);
                        break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        button.animate()
                                .setStartDelay(0)
                                .setDuration(300)
                                .scaleX(1)
                                .scaleY(1)
                                .setInterpolator(BOUNCE_INTERPOLATOR);
                        clearColorFilter(view);
                        break;
                }
                return false;
            }
        });
    }

    public static void createPopUpEffect(View view) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .setStartDelay(300)
                .setDuration(200)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(OVERSHOOT_INTERPOLATOR);
    }

    public static void createPopUpEffect(View view, long popOrder) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .setStartDelay(300 + 75 * popOrder)
                .setDuration(200)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(OVERSHOOT_INTERPOLATOR);
    }

}
