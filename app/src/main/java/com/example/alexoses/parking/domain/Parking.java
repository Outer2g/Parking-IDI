package com.example.alexoses.parking.domain;

import android.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Alex on 24/10/2015.
 */
public class Parking {
    //spot i is occupied by vehicle x
    private Vector<VehicleParking> vehicleParkings;
    private int totalSpots;
    private int spotsTaken;

    public Parking(int TOTAL_SPOTS){
        this.totalSpots = TOTAL_SPOTS;
        this.spotsTaken = 0;
        this.vehicleParkings = new Vector<VehicleParking>(totalSpots);
        for(int i =0; i<totalSpots;++i) vehicleParkings.add(null);
    }
    //0<Spot<totalSpots
    public void entersVehicle(String numberPlate, Calendar c, int spot) throws Exception {
        if (vehicleParkings.get(spot) == null) {
            Calendar aux = c.getInstance();
            Date data = new Date(aux.get(Calendar.YEAR), aux.get(Calendar.MONTH), aux.get(Calendar.DAY_OF_MONTH));
            Pair<Integer, Integer> p = new Pair<Integer, Integer>(aux.get(Calendar.HOUR), aux.get(Calendar.MINUTE));
            VehicleParking v = new VehicleParking(numberPlate, data, p);
            vehicleParkings.set(spot, v);
        }
        else throw new Exception("Spot already occupied, try another one");
    }
    public void leavesVehicle(int spot,Calendar c){
        //TODO create ticket save ticket bla bla bla
    }
    public Vector<Integer> getFreeSpots(){
        Vector<Integer> r = new Vector<Integer>();
        for (int i=0; i<totalSpots;++i){
            if (vehicleParkings.get(i) == null) r.add(i);
        }
        return r;
    }
    public int getTotalSpots(){ return totalSpots;}
    public Vector<VehicleParking> getSpots(){
        return vehicleParkings;
    }

}
