package com.example.alexoses.parking.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.alexoses.parking.R;

/**
 * Created by Alex on 08/01/2016.
 */
public class ShowRecaudacioDialog extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.recaudacio_dialog,null);
        builder.setView(rootView);
        TextView recau = (TextView) rootView.findViewById(R.id.recaudacioText);

        double recap = getArguments().getDouble("recau");
        recau.setText(Double.toString(recap));
        //adding "ok" button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }

        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
