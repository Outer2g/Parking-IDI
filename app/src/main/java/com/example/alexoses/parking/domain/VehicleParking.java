package com.example.alexoses.parking.domain;

import java.util.Date;

/**
 * Created by Alex on 24/10/2015.
 */
public class VehicleParking {
    private String numberPlate;
    private Date dataEntrada;

    public VehicleParking(){}

    public VehicleParking(String numberPlate,Date data){
        this.dataEntrada = data;
        this.numberPlate = numberPlate;
    }
    public String getNumberPlate(){
        return this.numberPlate;
    }

    public Date getDataEntrada(){return this.dataEntrada;}
    public void setNewNumberPlate(String numberPlate){
        this.numberPlate = numberPlate;
    }
}
