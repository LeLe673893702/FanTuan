package com.anonymouser.book.utlis;

import android.widget.Toast;

import com.anonymouser.book.BookApp;

/**
 *
 * @author
 * @date 2018/11/19
 *
 */
public class ToastUtil {
    private static ToastUtil mInstance;
    private Toast mToast;

    public static ToastUtil getInstance() {
        if (mInstance == null) {
            mInstance = new ToastUtil();
        }

        return mInstance;
    }


    public void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(BookApp.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showToast(int resID) {
        showToast(BookApp.getContext().getResources().getString(resID));
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
