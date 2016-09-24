package com.example.androiddam.proyectofinalandroid.io;

import android.util.Log;

/**
 * Created by Juan on 24/09/2016.
 */

public class TypeJob {
    private static final int VALUE_SERVICE = 1;
    private static final int VALUE_HOURS = 2;

    public static final String NAME_SERVICE = "servico";
    public static final String NAME_HOURS = "hora";

    public static String getTypeJob(int value) {
        String result = "";
        switch (value) {
            case VALUE_SERVICE:
                result = NAME_SERVICE;
                break;
            case VALUE_HOURS:
                result = NAME_HOURS;
                break;
        }
        Log.d("FINISH", "TIPO: " + result);
        return "por " + result;
    }
}
