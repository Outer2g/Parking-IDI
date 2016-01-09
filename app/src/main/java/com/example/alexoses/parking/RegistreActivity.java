package com.example.alexoses.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexoses.parking.Dialogs.PickDateDialog;
import com.example.alexoses.parking.Dialogs.ShowRecaudacioDialog;
import com.example.alexoses.parking.Persistencia.CtrlBd;

import java.text.DecimalFormat;
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
        getSupportActionBar().hide();

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
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Bundle args = data.getExtras();
                        try {
                            dataInicial = sdf.parse(args.getString("dataIn"));
                            dataFinal = sdf.parse(args.getString("dataOut"));
                            if(dataInicial.after(dataFinal)) throw new Exception();
                            mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list,R.id.list,bd.getLog(dataInicial,dataFinal));
                            mAdapter.notifyDataSetChanged();
                            ListView listView = (ListView) rootView.findViewById(R.id.listView);
                            listView.setAdapter(mAdapter);
                            updateInfo();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(),"la data no es correcte",Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

        private void linkButtons(){
            Button select = (Button) rootView.findViewById(R.id.selectButton);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PickDateDialog newDialog = new PickDateDialog();
                    newDialog.setTargetFragment(mainFragment, PICK_DATA_CODE);
                    newDialog.show(getActivity().getSupportFragmentManager(), "Parking");
                }
            });
        }
        private void updateInfo(){
            TextView info = (TextView) rootView.findViewById(R.id.infoLogText);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            double recaudacio =bd.getRecaudacio(dataInicial,dataFinal);
            info.setText("Registre desde la data: "+sdf.format(dataInicial)+" fins a: "+sdf.format(dataFinal) +
                    " recaudacio: "+new DecimalFormat("##.##").format(recaudacio));

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
