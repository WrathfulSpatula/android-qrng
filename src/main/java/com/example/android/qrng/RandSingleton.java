package com.example.android.qrng;

public class RandSingleton {

    private static final RandSingleton instance = new RandSingleton();

    public boolean[] randBools;
    public int randBoolOffset;

    //private constructor to avoid client applications to use constructor
    private RandSingleton(){}

    public static RandSingleton getInstance(){
        return instance;
    }
}