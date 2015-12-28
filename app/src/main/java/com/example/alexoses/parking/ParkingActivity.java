package com.example.alexoses.parking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        private final int TOTAL_SPOTS = 14;
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

        private void linkButtons(){
            places = new Vector<Button>(TOTAL_SPOTS);
            places.add((Button) rootView.findViewById(R.id.button1));
            places.add((Button) rootView.findViewById(R.id.button2));
            places.add((Button) rootView.findViewById(R.id.button3));
            places.add((Button) rootView.findViewById(R.id.button4));
            places.add((Button) rootView.findViewById(R.id.button5));
            places.add((Button) rootView.findViewById(R.id.button6));
            places.add((Button) rootView.findViewById(R.id.button7));
            places.add((Button) rootView.findViewById(R.id.button13));
            places.add((Button) rootView.findViewById(R.id.button14));
            places.add((Button) rootView.findViewById(R.id.button15));
            places.add((Button) rootView.findViewById(R.id.button16));
            places.add((Button) rootView.findViewById(R.id.button17));
            places.add((Button) rootView.findViewById(R.id.button18));
            places.add((Button) rootView.findViewById(R.id.button19));
            for(int i =0; i < TOTAL_SPOTS;++i) {
                places.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorDrawable color = (ColorDrawable) v.getBackground();
                        if (color.getColor() == Color.RED) v.setBackgroundColor(Color.GREEN);
                        else v.setBackgroundColor(Color.RED);


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
            }
        }
        private int getPlacaCode(View v){
            Button b = (Button) rootView.findViewById(v.getId());
            for (int i =0; i < places.size();++i) if (places.get(i) == b) return i;
            return -1;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_parking, container, false);
            parking = new Parking(TOTAL_SPOTS);
            linkButtons();
            return rootView;
        }
    }
}
