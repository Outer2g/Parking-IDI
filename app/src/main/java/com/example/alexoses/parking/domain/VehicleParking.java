package com.example.alexoses.parking.domain;

import android.util.Pair;

import java.util.Date;

/**
 * Created by Alex on 24/10/2015.
 */
public class VehicleParking {
    private String numberPlate;
    private Date dataEntrada;
    private Pair<Integer,Integer> hora;

    public VehicleParking(){}

    public VehicleParking(String numberPlate,Date data,Pair<Integer,Integer> hora){
        this.dataEntrada = data;
        this.hora = hora;
        this.numberPlate = numberPlate;
    }
    public String getNumberPlate(){
        return this.numberPlate;
    }
    public Pair<Integer,Integer> getHora(){return this.hora;}
    public Date getDataEntrada(){return this.dataEntrada;}
    public void setNewNumberPlate(String numberPlate){
        this.numberPlate = numberPlate;
    }
}
