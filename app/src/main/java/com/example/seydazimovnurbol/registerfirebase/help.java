package com.example.seydazimovnurbol.registerfirebase;

/**
 * Created by Zhandos on 06-Dec-17.
 */

public class help {

    public static help sharedD = new help();

    public int count = 0;





    public void setHelp(int Help) {
        System.out.println("set Help");
        this.count = Help;
    }

    public int getHelp() {
        System.out.println("get Help");
        return count;
    }
}
