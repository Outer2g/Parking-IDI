package com.example.alexoses.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexoses.parking.Dialogs.PickDateDialog;
import com.example.alexoses.parking.Dialogs.ShowRecaudacioDialog;
import com.example.alexoses.parking.Persistencia.CtrlBd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RegistreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        View rootView;
        private CtrlBd bd;
        private ArrayAdapter<String> mAdapter;
        private List<String> data;
        Date dataInicial,dataFinal;
        private final int PICK_DATA_CODE =1;
        private final Fragment mainFragment = this;

        public PlaceholderFragment() {
        }
        private void showRecaudacio(){
            ShowRecaudacioDialog newDialog = new ShowRecaudacioDialog();
            Bundle  args = new Bundle();
            double aux = bd.getRecaudacio(dataInicial,new Date());
            Log.e("SK", String.valueOf(aux));
            args.putDouble("recau",aux);
            newDialog.setArguments(args);
            newDialog.show(getActivity().getSupportFragmentManager(),"Parking");
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case PICK_DATA_CODE:
                    if (resultCode == Activity.RESULT_OK) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                        Bundle args = data.getExtras();
                        try {
                            dataInicial = sdf.parse(args.getString("dataIn"));
                            dataFinal = sdf.parse(args.getString("dataOut"));
                            mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list,R.id.list,bd.getLog(dataInicial,dataFinal));
                            mAdapter.notifyDataSetChanged();
                            ListView listView = (ListView) rootView.findViewById(R.id.listView);
                            listView.setAdapter(mAdapter);
                        } catch (Exception e) {
                            Log.e("NO AGAFO RE","SAD");
                        }
                    }
                    break;
            }
        }

        private void linkButtons(){
            Button recaudacio = (Button) rootView.findViewById(R.id.recaudacioButton);
            recaudacio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecaudacio();
                }
            });
            Button ara = (Button) rootView.findViewById(R.id.totalButton);
            ara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataInicial = bd.getDataCreacio();
                    dataFinal = new Date();
                    mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list,R.id.list,bd.getLog(dataInicial,dataFinal));

                    mAdapter.notifyDataSetChanged();
                    ListView listView = (ListView) rootView.findViewById(R.id.listView);
                    listView.setAdapter(mAdapter);
                }
            });
            Button select = (Button) rootView.findViewById(R.id.selectButton);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PickDateDialog newDialog = new PickDateDialog();
                    newDialog.setTargetFragment(mainFragment, PICK_DATA_CODE);
                    newDialog.show(getActivity().getSupportFragmentManager(),"Parking");
                }
            });
            Button today = (Button) rootView.findViewById(R.id.todayButton);
            today.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date aux = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String newDate = sdf.format(aux) + " 00:00:00";
                    dataFinal = aux;
                    try {
                        dataInicial = sdf2.parse(newDate);
                    } catch (ParseException e) {
                        Log.e("NO NEW","NO NEW");
                    }
                    mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list,R.id.list,bd.getLog(dataInicial,dataFinal));

                    mAdapter.notifyDataSetChanged();
                    ListView listView = (ListView) rootView.findViewById(R.id.listView);
                    listView.setAdapter(mAdapter);
                }
            });
        }
        private void updateInfo(){
            TextView info = (TextView) rootView.findViewById(R.id.infoLogText);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            info.setText("Registre desde la data: "+sdf.format(dataInicial)+" fins a: "+sdf.format(dataFinal) + " recaudacio: "+bd.getRecaudacio(dataInicial,dataFinal));

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            bd = new CtrlBd(getActivity());
            dataInicial = bd.getDataCreacio();
            dataFinal = new Date();
            data = bd.getLog(dataInicial,dataFinal);
            rootView = inflater.inflate(R.layout.fragment_registre, container, false);
            mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list,R.id.list,bd.getLog(dataInicial,dataFinal));
            updateInfo();
            ListView listView = (ListView) rootView.findViewById(R.id.listView);
            listView.setAdapter(mAdapter);
            linkButtons();

            return rootView;
        }
    }
}
