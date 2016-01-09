package com.example.alexoses.parking.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexoses.parking.Persistencia.CtrlBd;
import com.example.alexoses.parking.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 08/01/2016.
 */
public class PickDateDialog extends android.support.v4.app.DialogFragment {
    private boolean nothingNull(EditText a,EditText b,EditText c,EditText d){
        return (a.getText()!=null) && b.getText()!=null && c.getText()!=null && d.getText()!=null;
    }
    private final Fragment mainfragment =this;
    private final int DATA_IN_CODE =1;
    private final int DATA_OUT_CODE=2;
    private final int TIME_IN_CODE=3;
    private final int TIME_OUT_CODE=4;
    private View rootView;
    private TextView dataIn;
    private TextView dataOut;
    private TextView horaIn;
    private TextView horaOut;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DATA_IN_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int day = (int) bundle.get("day");
                    int month = (int) bundle.get("month")+1;
                    int year = (int) bundle.get("year");
                    String aux;
                    if(day/10==0) aux="0"+day+"-";
                    else aux = day+"-";
                    if(month/10==0) aux = aux+"0"+month+"-";
                    else aux= aux + month+"-";
                    dataIn.setText(aux+year);
                }
                break;
            case DATA_OUT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int day = (int) bundle.get("day");
                    int month = (int) bundle.get("month")+1;
                    int year = (int) bundle.get("year");
                    String aux;
                    if(day/10==0) aux="0"+day+"-";
                    else aux = day+"-";
                    if(month/10==0) aux = aux+"0"+month+"-";
                    else aux= aux + month+"-";
                    dataOut.setText(aux+year);
                }
                break;
            case TIME_IN_CODE:
                if(resultCode == Activity.RESULT_OK){

                    Bundle bundle = data.getExtras();
                    int hora = bundle.getInt("hora");
                    int min = bundle.getInt("min");
                    String aux;
                    if (hora/10 ==0) aux="0"+hora+":";
                    else aux = hora+":";
                    if(min/10==10) aux=aux+"0"+min+":";
                    else aux= aux+min+":";
                    horaIn.setText(aux+"00");
                }
                break;
            case TIME_OUT_CODE:
                if(resultCode == Activity.RESULT_OK){

                    Bundle bundle = data.getExtras();
                    int hora = bundle.getInt("hora");
                    int min = bundle.getInt("min");
                    String aux;
                    if (hora/10 ==0) aux="0"+hora+":";
                    else aux = hora+":";
                    if(min/10==10) aux=aux+"0"+min+":";
                    else aux= aux+min+":";
                    horaOut.setText(aux+"00");
                }
                break;
        }
    }

    private void prepareButtons(){

        Button today = (Button) rootView.findViewById(R.id.todayButton);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date aux = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String newDate = sdf.format(aux) + " 00:00:00";
                Intent intent = new Intent();
                intent.putExtra("dataIn", newDate);
                intent.putExtra("dataOut", sdf2.format(aux));
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });
        Button ara = (Button) rootView.findViewById(R.id.allTimeButton);
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date aux = new Date();
                Intent intent = new Intent();
                CtrlBd bd = new CtrlBd(getActivity());
                intent.putExtra("dataIn", sdf2.format(bd.getDataCreacio()));
                intent.putExtra("dataOut", sdf2.format(aux));
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });
        Button dIn = (Button) rootView.findViewById(R.id.selDataInButton);
        dIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataPicker newDialog = new MyDataPicker();
                newDialog.setTargetFragment(mainfragment, DATA_IN_CODE);
                newDialog.show(getFragmentManager(), "datapick");
            }
        });
        Button dOut = (Button) rootView.findViewById(R.id.selDataOutButton);
        dOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataPicker newDialog = new MyDataPicker();
                newDialog.setTargetFragment(mainfragment,DATA_OUT_CODE);
                newDialog.show(getFragmentManager(),"datapick");
            }
        });
        Button tIn = (Button) rootView.findViewById(R.id.horaInButton);
        tIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePicker newDialog = new MyTimePicker();
                newDialog.setTargetFragment(mainfragment,TIME_IN_CODE);
                newDialog.show(getFragmentManager(),"timepick");
            }
        });
        Button tOut = (Button) rootView.findViewById(R.id.horaOutButton);
        tOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePicker newDialog = new MyTimePicker();
                newDialog.setTargetFragment(mainfragment,TIME_OUT_CODE);
                newDialog.show(getFragmentManager(),"timepick");
            }
        });
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.pick_date, null);
        builder.setCancelable(false);
        builder.setView(rootView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        dataIn = (TextView) rootView.findViewById(R.id.dataInText);
        dataOut = (TextView) rootView.findViewById(R.id.dataOutText);
        horaIn = (TextView) rootView.findViewById(R.id.horaInText);
        horaOut = (TextView) rootView.findViewById(R.id.horaOutText);


        prepareButtons();

                //adding "ok" button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                String dIn = dataIn.getText() + " " +horaIn.getText();
                String dOut = dataOut.getText() +" "+horaOut.getText();
                intent.putExtra("dataIn", dIn);
                intent.putExtra("dataOut", dOut);
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