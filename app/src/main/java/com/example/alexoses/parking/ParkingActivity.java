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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alexoses.parking.Dialogs.AjudaDialog;
import com.example.alexoses.parking.Dialogs.NewVehicleDialog;
import com.example.alexoses.parking.Dialogs.SeeVehicleDialog;
import com.example.alexoses.parking.Dialogs.ShowTicketDialog;
import com.example.alexoses.parking.Persistencia.CtrlBd;
import com.example.alexoses.parking.domain.Parking;
import com.example.alexoses.parking.domain.VehicleParking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006064")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset){
            CtrlBd bd = new CtrlBd(this);
            bd.reset();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        return super.onOptionsItemSelected(item);
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
        private final int MAT_FREE_CODE = 2;
        private final int verd = Color.parseColor("#ff00dd45");
        private final int vermell = Color.parseColor("#ffd40005");

        private CtrlBd bd;
        private int splaca;
        public PlaceholderFragment() {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode){
                case MAT_REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK){
                        Bundle bundle = data.getExtras();
                        String matricula = bundle.getString("mat");
                        int spot = bundle.getInt("spot");
                        Date calendar = new Date();
                        try {
                            parking.entersVehicle(matricula, calendar, spot);
                            bd.insertCar(new VehicleParking(matricula,calendar),spot);
                            places.get(spot).setBackgroundColor(vermell);
                        } catch (Exception e) {
                            Log.e("PARKING",e.getMessage());
                        }
                    }
                    break;
                case MAT_FREE_CODE:
                    if (resultCode == Activity.RESULT_OK){
                        int spot = data.getExtras().getInt("spot");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date sortida = new Date();
                        Bundle args = new Bundle();
                        args.putString("dataIn",sdf.format(parking.getSpots().get(spot).getDataEntrada()));
                        args.putString("dataOut",sdf.format(sortida));
                        bd.delCar(spot,sortida,calculaPreu(args),data.getExtras().getString("matricula"));
                        places.get(spot).setBackgroundColor(verd);
                        parking.leavesVehicle(spot, new Date());
                        splaca = spot;
                    }
            }
        }

        private Double calculaPreu(Bundle args){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                Date entrada = sdf.parse(args.getString("dataIn"));
                Date sortida = sdf.parse(args.getString("dataOut"));
                int dies = (sortida.getDay()-entrada.getDay())*24*60;
                int hores = (sortida.getHours()-entrada.getHours())*60;
                int mins = sortida.getMinutes()-entrada.getMinutes()+1;
                Log.e("ZZ","dies: "+dies+" hores: "+hores+" mins: "+mins+"  sortida: "+sortida.getHours()+" entrada: "+entrada.getHours());;
                double costt = dies+hores+mins;
                return costt*2/100;
            } catch (ParseException e) {
                Log.e("Show Ticket",e.getMessage());
            }
            return -1.0;
        }
        private void showVehicle(int spot){
            SeeVehicleDialog newDialog = new SeeVehicleDialog();
            Bundle  args = new Bundle();
            args.putString("matricula", parking.getSpots().get(spot).getNumberPlate());
            args.putInt("spot", spot);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            args.putString("date", sdf.format(parking.getSpots().get(spot).getDataEntrada()));
            newDialog.setArguments(args);
            newDialog.setTargetFragment(mainFragment, MAT_FREE_CODE);
            newDialog.show(getActivity().getSupportFragmentManager(),"Parking");

        }
        private void showTicket(Bundle data){
            ShowTicketDialog newDialog = new ShowTicketDialog();
            Bundle  args = new Bundle();
            args.putString("matricula", data.getString("matricula"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            args.putString("dataIn", data.getString("dataIn"));
            Date d= new Date();
            args.putString("dataOut", sdf.format(d));
            newDialog.setArguments(args);
            newDialog.setTargetFragment(mainFragment, MAT_REQUEST_CODE);
            newDialog.show(getActivity().getSupportFragmentManager(),"Parking");
        }
        private void enterVehicle(int spot){
            NewVehicleDialog newDialog = new NewVehicleDialog();
            Bundle args = new Bundle();
            args.putInt("spot",spot);
            newDialog.setArguments(args);
            newDialog.setTargetFragment(mainFragment, MAT_REQUEST_CODE);
            newDialog.show(getActivity().getSupportFragmentManager(),"Parking");
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
                        int spot = getPlacaCode(v);
                        int colorInt = verd;

                        int i1 = color.getColor();
                        if (i1 == vermell) {
                            showVehicle(spot);

                        } else if (i1 == colorInt) {
                            enterVehicle(spot);

                        }
                    }
                });

            }
        }
        private int getPlacaCode(View v){
            Button b = (Button) rootView.findViewById(v.getId());
            for (int i =0; i < places.size();++i) if (places.get(i) == b) return i;
            return -1;
        }
        private void syncParking(){
            HashMap<Integer,VehicleParking> vehicles = bd.getVehiclesPark();
            for(int i =0;i<places.size();++i){
                VehicleParking v = vehicles.get(i);
                if (v!=null){
                    try {
                        parking.entersVehicle(v.getNumberPlate(),v.getDataEntrada(),i);
                    } catch (Exception e) {
                        Log.e("SYNC",e.getMessage());
                    }
                    places.get(i).setBackgroundColor(vermell);
                }
            }
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_parking, container, false);
            bd = new CtrlBd(getActivity());
            parking = new Parking(TOTAL_SPOTS);
            linkButtons();
            syncParking();
            splaca = 0;
            Button entrada = (Button) rootView.findViewById(R.id.entradaButton);
            entrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean b = true;
                    while (b){
                        if (parking.getSpots().get(splaca) != null) ++splaca;
                        else {
                            enterVehicle(splaca);
                            b = false;
                        }
                        if (splaca>= TOTAL_SPOTS){
                            Toast.makeText(getActivity(),"Parking ple",Toast.LENGTH_SHORT);
                            b = false;
                        }
                    }
                }
            });
            Button registre = (Button) rootView.findViewById(R.id.registreButton);
            registre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),RegistreActivity.class);
                    startActivity(intent);
                }
            });
            Button aj = (Button) rootView.findViewById(R.id.helpButton);
            aj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AjudaDialog newDialog = new AjudaDialog();
                    newDialog.show(getActivity().getSupportFragmentManager(),"Ajuda");
                }
            });

            return rootView;
        }
    }
}
