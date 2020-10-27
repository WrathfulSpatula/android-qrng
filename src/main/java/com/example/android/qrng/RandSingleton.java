package com.example.android.qrng;

import java.util.BitSet;

public class RandSingleton {

    private static final RandSingleton instance = new RandSingleton();

    public BitSet randBools;
    public int randSize;
    public int randBoolOffset;

    //private constructor to avoid client applications to use constructor
    private RandSingleton(){}

    public static RandSingleton getInstance(){
        return instance;
    }
}