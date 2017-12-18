package com.fyt.loki.fyt.Tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by ergas on 11/13/2017.
 */

public class LoginProcessDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
        return builder.setTitle("Logging in").setMessage("logging in process").create();
    }
}
