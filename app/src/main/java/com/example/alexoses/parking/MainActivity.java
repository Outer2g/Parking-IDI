package com.example.alexoses.parking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.alexoses.parking.Persistencia.CtrlBd;


public class MainActivity extends ActionBarActivity {

    private void startParking(){
        Intent s = new Intent(this,ParkingActivity.class);
        startActivity(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button parking = (Button) findViewById(R.id.parkingButton);
        parking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startParking();
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_reset){
            CtrlBd bd = new CtrlBd(this);
            bd.reset();
        }

        return super.onOptionsItemSelected(item);
    }
}
