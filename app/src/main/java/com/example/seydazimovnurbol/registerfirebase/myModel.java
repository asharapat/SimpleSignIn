package com.example.seydazimovnurbol.registerfirebase;

/**
 * Created by root on 11/30/17.
 */

public class myModel {

    public static myModel sharedData = new myModel();

    public int count = 0;

    public myModel() {

    }



    public void setCount(int count) {
        System.out.println("set Count");
        this.count = count;
    }

    public int getCount() {
        System.out.println("get Count");
        return count;
    }

}
