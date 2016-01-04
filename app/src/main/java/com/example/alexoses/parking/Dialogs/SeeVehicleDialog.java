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
import android.widget.TextView;

import com.example.alexoses.parking.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 21/12/2015.
 */
public class SeeVehicleDialog extends android.support.v4.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.placa_dialog,null);
        builder.setView(rootView);

        //agafem la matricula del parametre
        final Bundle data = getArguments();
        String s = data.getString("matricula");
        TextView numberPlate = (TextView) rootView.findViewById(R.id.matriculaNumber);
        numberPlate.setText(s);

        // agafem la data
        s = data.getString("date");
        TextView dataText = (TextView) rootView.findViewById(R.id.dataInfo);
        dataText.setText(s);

        //adding "ok" button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        // adding "Lliberar" button which will cause to free the spot
        builder.setPositiveButton("Lliberar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShowTicketDialog newDialog = new ShowTicketDialog();
                Bundle  args = new Bundle();
                args.putString("matricula", data.getString("matricula"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                args.putString("dataIn", data.getString("date"));
                Date d= new Date();
                args.putString("dataOut", sdf.format(d));
                newDialog.setArguments(args);
                newDialog.show(getActivity().getSupportFragmentManager(),"Parking");

                //return
                Intent intent = new Intent().putExtra("spot",data.getInt("spot"));
                intent.putExtra("matricula",data.getString("matricula"));
                intent.putExtra("dataIn",data.getString("date"));
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
