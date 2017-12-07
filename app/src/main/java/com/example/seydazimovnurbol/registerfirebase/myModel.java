package com.example.seydazimovnurbol.registerfirebase;

public class myModel {

    public static myModel sharedData = new myModel();

    public int count = 0;





    public void setCount(int count) {
        System.out.println("set Count");
        this.count = count;
    }

    public int getCount() {
        System.out.println("get Count");
        return count;
    }

}
