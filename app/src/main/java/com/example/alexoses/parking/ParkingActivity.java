package com.example.alexoses.parking;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.alexoses.parking.domain.Parking;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;


public class ParkingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Vector<Button> places;
        View rootView;
        private Parking parking;
        private final Fragment mainFragment = this;
        private final int TOTAL_SPOTS = 12;
        private final int MAT_REQUEST_CODE = 1;
        public PlaceholderFragment() {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode){
                case MAT_REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK){
                        Bundle bundle = data.getExtras();
                        String matricula = bundle.getString("matriculaNova");
                        Calendar c = new GregorianCalendar(2015,3,28);
                        try {
                            parking.entersVehicle(matricula,c,2);
                        } catch (Exception e) {
                            Log.e("PARKING",e.getMessage());
                        }
                    }
                    break;
            }
        }

        private Button createButton(){
            final Button but = new Button(getActivity());
            but.setBackgroundColor(Color.RED);
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                but.setHeight(p.y / 3);
                but.setWidth(p.x / 12);
            }
            else {
                but.setHeight(p.y / 12);
                but.setWidth(p.x / 3);
            }

            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColorDrawable color = (ColorDrawable) but.getBackground();
                    if (color.getColor() == Color.RED) but.setBackgroundColor(Color.GREEN);
                    else but.setBackgroundColor(Color.RED);

                    MyDialog newDialog = new MyDialog();
                    Bundle  args = new Bundle();
                    try{
                        args.putString("matricula", parking.getSpots().get(2).getNumberPlate());
                    }
                    catch (Exception e){
                        args.putString("matricula","introdueixi nova matricula");
                    }
                    newDialog.setArguments(args);
                    newDialog.setTargetFragment(mainFragment, MAT_REQUEST_CODE);
                    newDialog.show(getActivity().getSupportFragmentManager(),"hi");
                }
            });
            but.setText("");
            places.add(but);
            return  but;
        }
        private View horiSpacer(){
            View v = new View(getActivity());
            v.setLayoutParams(new ViewGroup.LayoutParams(2, 0));
            return v;
        }
        private View vertiSpacer(){
            View v = new View(getActivity());
            v.setLayoutParams(new ViewGroup.LayoutParams(0,2));
            return v;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_parking, container, false);
            places = new Vector<Button>(TOTAL_SPOTS);
            parking = new Parking(TOTAL_SPOTS);

            //Agafo les dos files/columnes i les omplo amb botons
            LinearLayout fila1 = (LinearLayout) rootView.findViewById(R.id.fila1);
            LinearLayout fila2 = (LinearLayout) rootView.findViewById(R.id.fila2);
            for (int i =0;i<12;++i){
                //TODO CORRECT
                if (i<6){
                    fila1.addView(createButton());
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        fila1.addView(vertiSpacer());
                    else fila1.addView(horiSpacer());
                }
                else {
                    fila2.addView(createButton());
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        fila2.addView(vertiSpacer());
                    else fila2.addView(horiSpacer());
                }
            }
            return rootView;
        }
    }
}
