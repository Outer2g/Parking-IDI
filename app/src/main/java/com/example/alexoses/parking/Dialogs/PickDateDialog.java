package com.example.alexoses.parking.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.alexoses.parking.R;

import java.text.SimpleDateFormat;

/**
 * Created by Alex on 08/01/2016.
 */
public class PickDateDialog extends android.support.v4.app.DialogFragment {
    private boolean nothingNull(EditText a,EditText b,EditText c,EditText d){
        return (a.getText()!=null) && b.getText()!=null && c.getText()!=null && d.getText()!=null;
    }
    private EditText diaIn;
    private EditText mesIn;
    private EditText yearIn;
    private EditText horaIn;
    private EditText minIn;


    private EditText diaOut;
    private EditText mesOut;
    private EditText yearOut;
    private EditText horaOut;
    private EditText minOut;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.pick_date, null);
        builder.setCancelable(false);
        builder.setView(rootView);
        diaIn = (EditText) rootView.findViewById(R.id.dataInDiaText);
        mesIn = (EditText) rootView.findViewById(R.id.dataInMesText);
        yearIn = (EditText) rootView.findViewById(R.id.dataInYearText);
        horaIn = (EditText) rootView.findViewById(R.id.horaInText);
        minIn = (EditText) rootView.findViewById(R.id.minInText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        diaOut = (EditText) rootView.findViewById(R.id.dataOutDiaText);
        mesOut = (EditText) rootView.findViewById(R.id.dataOutMesText);
        yearOut = (EditText) rootView.findViewById(R.id.dataOutYearText);
        horaOut = (EditText) rootView.findViewById(R.id.horaOutText);
        minOut = (EditText) rootView.findViewById(R.id.minOutText);


                //adding "ok" button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                String dataIn = diaIn.getText() + "-" + mesIn.getText() + "-" + yearIn.getText() + " " + horaIn.getText() + ":" + minIn.getText() + ":00";
                Log.e("DATAIN", dataIn);
                String dataOut = diaOut.getText() + "-"+ mesOut.getText() + "-"+ yearOut.getText() + " "+horaOut.getText()+":"+minOut.getText()+":00";
                Log.e("DATAOUT",dataOut);
                intent.putExtra("dataIn", dataIn);
                intent.putExtra("dataOut", dataOut);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }

        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}