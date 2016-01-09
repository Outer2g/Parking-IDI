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
 * Created by Alex on 21/12/2015.
 */
public class AjudaDialog extends android.support.v4.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Loading custom dialog Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.ajuda_dialog,null);
        builder.setView(rootView);
        TextView textView = (TextView) rootView.findViewById(R.id.ajudaText);
        textView.setText("Aplicació creada per : Alex Osés per la Facultad d'Informatica de Barcelona CURS TARDOR 2015-2016.\n\n\n" +
                "En aquesta aplicacio es simula el comportament d'un parking amb 14 places. Per afegir un vehicle, cal clickar a la plaça" +
                "desitjada o fer click al botó \"Entrada\" el qual asignara una plaça, al introduir la matricula i acceptar, veurem que la " +
                "plaça ara esta de color vermell (esta ocupada) si cliquem aquesta plaça podem veure informacio sobre el vehicle que esta" +
                "aparcat i podem també simular la seva sortida, mostrant el ticket. Tambe disposem d'un botó \"Registre\" amb el qual accedirem" +
                "al registre del parking entre dues dates. Aquestes dates es poden modificar clicant en el botó inferior. A la part superior de la pantalla" +
                "observem informacio sobre el parking en aquestes dues dates, aixi com les dues dates mateixes, asota d'aquesta informacio" +
                "tenim una llista de tots els moviments del parking entre les dues dates.\n\n\n" +
                "per fer un reset del parking i començar una simulacio completament nova hem de clickar en els tres punts de la part superior dreta" +
                "de la pantalla i clickar a \"reset\" IMPORTANT: aixo fara perdre totes les dades fins ara entrades");

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
