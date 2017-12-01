package com.rackspira.epenting.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rackspira.epenting.R;

/**
 * Created by kal on 30/11/17.
 */

public class ViewHelper {

    public static void dialogProgress(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);

        ProgressBar mProgressBar = dialog.findViewById(R.id.progressBar);

        dialog.show();
    }
}
