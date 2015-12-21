package com.example.alexoses.parking;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


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

        private Button but ;
        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_parking, container, false);
            LinearLayout lay = (LinearLayout) rootView.findViewById(R.id.Fila1);
            but = new Button(getActivity());
            but.setBackgroundColor(Color.RED);
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            but.setHeight(p.y/2);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColorDrawable color = (ColorDrawable) but.getBackground();
                    if (color.getColor() == Color.RED) but.setBackgroundColor(Color.GREEN);
                    else but.setBackgroundColor(Color.RED);
                    new MyDialog().show(getFragmentManager(),"hi");
                }
            });
            but.setText("");
            lay.addView(but);

            return rootView;
        }
    }
}
