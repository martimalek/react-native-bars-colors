package com.barscolors;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.Map;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class BarsColorsModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "BarsColors";
    private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
    private static final String ERROR_NO_ACTIVITY_MESSAGE = "Tried to change the navigation bar while not attached to an Activity";
    private Window window = null;

    public BarsColorsModule(ReactApplicationContext context) {
        super(context);
    }

    public void setNavigationBarTheme(Activity activity, Boolean light) {
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Window window = activity.getWindow();
            int flags = window.getDecorView().getSystemUiVisibility();
            if (light) flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            else flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }

    private void runColorAnimation(Integer fromColor, Integer toColor, final RunnableWithArg<ValueAnimator> runnable) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                runnable.run(animator);
            }
        });
        colorAnimation.start();
    }

    private void prepareWindowAndRun(Runnable runnable, Promise promise) {
        if (getCurrentActivity() != null) {
            try {
                window = getCurrentActivity().getWindow();
                runOnUiThread(runnable);
            } catch (Exception e) {
                promise.reject("error", e);
            }
        } else promise.reject(ERROR_NO_ACTIVITY, new Throwable(ERROR_NO_ACTIVITY_MESSAGE));
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return super.getConstants();
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void changeNavBarColor(final String navbarColor, final String statusBarColor, final Boolean light, final Promise promise) {
        prepareWindowAndRun(() -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            Integer navbarColorFrom = window.getNavigationBarColor();
            Integer statusBarColorFrom = window.getStatusBarColor();
            try {
                Integer navbarColorTo = Color.parseColor(HexFormatter.format(navbarColor));
                Integer statusBarColorTo = Color.parseColor(HexFormatter.format(statusBarColor));

                runColorAnimation(navbarColorFrom, navbarColorTo, (ValueAnimator animator) -> {
                    window.setNavigationBarColor((Integer) animator.getAnimatedValue());
                });

                runColorAnimation(statusBarColorFrom, statusBarColorTo, (ValueAnimator animator) -> {
                    window.setStatusBarColor((Integer) animator.getAnimatedValue());
                });
            } catch (Exception e) {
                promise.reject("error", e);
            }


            setNavigationBarTheme(getCurrentActivity(), light);
            promise.resolve(true);
        }, promise);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void changeNavBarColor(final String color, final Boolean light, final Promise promise) {
        prepareWindowAndRun(() -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            Integer statusBarColorFrom = window.getStatusBarColor();
            Integer navbarColorFrom = window.getNavigationBarColor();

            try {
                Integer colorTo = Color.parseColor(HexFormatter.format(color));

                runColorAnimation(navbarColorFrom, colorTo, (ValueAnimator animator) -> {
                    window.setNavigationBarColor((Integer) animator.getAnimatedValue());
                });

                runColorAnimation(statusBarColorFrom, colorTo, (ValueAnimator animator) -> {
                    window.setStatusBarColor((Integer) animator.getAnimatedValue());
                });
            } catch (Exception e) {
                promise.reject("error", e);
            }

            setNavigationBarTheme(getCurrentActivity(), light);
            promise.resolve(true);
        }, promise);
    }
}
