package com.example.alexoses.parking.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.alexoses.parking.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 04/01/2016.
 */
public class ShowTicketDialog extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.ticket_dialog,null);

        TextView numberPlate = (TextView) rootView.findViewById(R.id.matriculaNumberTicket);
        TextView dataIn = (TextView) rootView.findViewById(R.id.dataEntrada);
        TextView dataOut = (TextView) rootView.findViewById(R.id.dataSortida);
        TextView cost = (TextView) rootView.findViewById(R.id.preuTicket);

        builder.setView(rootView);
        Bundle args = getArguments();
        String matricula = args.getString("matricula");
        numberPlate.setText(matricula);
        dataIn.setText(args.getString("dataIn"));
        dataOut.setText(args.getString("dataOut"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            Date entrada = sdf.parse(args.getString("dataIn"));
            Date sortida = sdf.parse(args.getString("dataOut"));
            int dies = (sortida.getDay()-entrada.getDay())*24*60;
            int hores = (sortida.getHours()-entrada.getHours())*60;
            int mins = sortida.getMinutes()-entrada.getMinutes()+1;
            double costt = dies+hores+mins;
            cost.setText(String.valueOf(costt*0.02));
        } catch (ParseException e) {
            Log.e("Show Ticket",e.getMessage());
        }


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
