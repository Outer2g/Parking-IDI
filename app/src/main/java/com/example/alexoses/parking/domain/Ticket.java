package com.example.alexoses.parking.domain;

import android.util.Pair;

import java.util.Date;

/**
 * Created by Alex on 24/10/2015.
 */
public class Ticket {
    private final double pricePerMinute = 0.02;
    private double cost;
    private Date entryDate;
    private Date leavingDate;
    private Pair<Integer,Integer> entryTime;
    private Pair<Integer,Integer> leavingTime;
    //A car can only stay for a day, for the moment
    public Ticket (Pair<Integer,Integer> entryTime,Pair<Integer,Integer> leavingTime){
        //how much for the hours spent
        int minutesEntry;
        int minutesLeav;
        minutesEntry = entryTime.first*60 + entryTime.second;
        minutesLeav = leavingTime.first*60 + entryTime.second;
        cost = (minutesLeav-minutesEntry) * pricePerMinute;
    }
    public double getCost(){return cost;}
    public Pair<Integer,Integer> getEntryTime(){return entryTime;}
    public Pair<Integer,Integer> getLeavingTime(){return leavingTime;}
    public double getPricePerMinute(){return pricePerMinute;}
}
