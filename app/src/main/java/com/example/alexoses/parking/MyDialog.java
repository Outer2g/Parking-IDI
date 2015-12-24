package com.example.alexoses.parking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alex on 21/12/2015.
 */
public class MyDialog extends android.support.v4.app.DialogFragment {
    private EditText field;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.placa_dialog,null);
        builder.setView(rootView);
        Bundle data = getArguments();
        String s = data.getString("matricula");
        field = (EditText) rootView.findViewById(R.id.matriculaIn);
        TextView t = (TextView) rootView.findViewById(R.id.textView);
        t.setText(s);

        //adding "ok" button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent().putExtra("matriculaNova",field.getText());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
