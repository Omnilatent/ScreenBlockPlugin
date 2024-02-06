package com.omnilatent.screenblock;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogUtil {
    public static Dialog dialogLoading;

    public static void showDialog(Activity activity) {
        showDialog(activity, 5000);
    }

    public static void showDialog(Activity activity, int setCancelableDelay) {
        if (dialogLoading == null) {
            dialogLoading = new Dialog(activity, android.R.style.Theme_Light);
            dialogLoading.setCancelable(false);
            dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogLoading.setContentView(R.layout.dialog_loading_ad);
            dialogLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    onDialogDismissed();
                }
            });
            dialogLoading.show();
        }
        if (setCancelableDelay > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dialogLoading != null) {
                        dialogLoading.setCancelable(true);
                    }
                }
            }, setCancelableDelay);
        }
    }

    public static void showDialogDismissOnResume(Activity activity) {
        if (dialogLoading == null) {
            dialogLoading = new Dialog(activity, android.R.style.Theme_Light);
            dialogLoading.setCancelable(false);
            dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogLoading.setContentView(R.layout.dialog_loading_ad);
            dialogLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    onDialogDismissed();
                }
            });
            dialogLoading.show();
        }
        activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity mActivity) {
                if (mActivity == activity) {
                    new Handler().postDelayed(() -> {
                        closeDialog();
                        activity.getApplication().unregisterActivityLifecycleCallbacks(this);
                    }, 200);
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    public static void closeDialog() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            onDialogDismissed();
        }
    }

    private static void onDialogDismissed() {
        dialogLoading = null;
    }
}
