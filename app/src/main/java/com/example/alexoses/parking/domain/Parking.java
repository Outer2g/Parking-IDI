package com.example.alexoses.parking.domain;

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
    public void entersVehicle(String numberPlate, Date c, int spot) throws Exception {
        if (vehicleParkings.get(spot) == null) {
            VehicleParking v = new VehicleParking(numberPlate, c);
            vehicleParkings.set(spot, v);
        }
        else throw new Exception("Spot already occupied, try another one");
    }
    public void leavesVehicle(int spot,Date c){
        //TODO create ticket save ticket bla bla bla
        vehicleParkings.set(spot,null);
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
