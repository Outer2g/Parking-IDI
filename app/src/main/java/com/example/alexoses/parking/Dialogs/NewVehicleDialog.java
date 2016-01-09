package com.example.alexoses.parking.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.alexoses.parking.R;

/**
 * Created by Alex on 21/12/2015.
 */
public class NewVehicleDialog extends android.support.v4.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.placa_dialog_new,null);
        builder.setView(rootView);
        Bundle args = getArguments();
        final int spot = args.getInt("spot");
        final EditText field = (EditText) rootView.findViewById(R.id.editText);

        //adding "ok" button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //TODO matricula valida
                    Intent intent = new Intent();
                    String s = String.valueOf(field.getText());
                    intent.putExtra("mat", s);
                    intent.putExtra("spot", spot);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, new Intent());
                dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}