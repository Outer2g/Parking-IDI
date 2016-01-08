package com.example.alexoses.parking;

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

import com.example.alexoses.parking.Dialogs.ShowRecaudacioDialog;
import com.example.alexoses.parking.Persistencia.CtrlBd;

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
        private void linkButtons(){
            Button recaudacio = (Button) rootView.findViewById(R.id.recaudacioButton);
            recaudacio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecaudacio();
                }
            });
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            bd = new CtrlBd(getActivity());
            dataInicial = bd.getDataCreacio();
            dataFinal = new Date();
            data = bd.getLog();
            rootView = inflater.inflate(R.layout.fragment_registre, container, false);
            mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list,R.id.list,bd.getLog());
            ListView listView = (ListView) rootView.findViewById(R.id.listView);
            listView.setAdapter(mAdapter);
            linkButtons();
            return rootView;
        }
    }
}
