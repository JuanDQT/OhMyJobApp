package com.example.androiddam.proyectofinalandroid.model;

import android.util.Log;

/**
 * Created by Juan on 24/09/2016.
 */

public final class Days {
    private static final int[] LEVEL_DAYS = new int[]{1, 2, 3, 4, 5, 6, 7};

    private static final String RESULT_WEEKDAY = "Entre semana";
    private static final String RESULT_EVERYDAY = "Todos los dias";
    private static final String RESULT_WEEKEND = "Fines de semana";

    private static final String[] DAYS_NAME = new String[]{"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

    public static String getTextDay(String days) {
        //String[] dayStringArray = "1,7".split(",");
        String[] dayStringArray = days.split(",");
        Log.d("FINISH", "datstringarray: "+dayStringArray.toString());
        Log.d("FINISH", "datstringarray size: "+dayStringArray.length);
        Log.d("FINISH", "datstringarray[0]: "+dayStringArray[0]);
        String result = "";

        if (days.contains(LEVEL_DAYS[0]+"") && days.contains(LEVEL_DAYS[1]+"") && days.contains(LEVEL_DAYS[2]+"") && days.contains(LEVEL_DAYS[3]+"") && days.contains(LEVEL_DAYS[4]+"") && days.contains(LEVEL_DAYS[5]+"") &&days.contains(LEVEL_DAYS[6]+""))
            result = RESULT_EVERYDAY;
        else if (days.contains(LEVEL_DAYS[0]+"") && days.contains(LEVEL_DAYS[1]+"") && days.contains(LEVEL_DAYS[2]+"") && days.contains(LEVEL_DAYS[3]+"") && days.contains(LEVEL_DAYS[4]+""))
            result = RESULT_WEEKDAY;
        else if (days.contains(LEVEL_DAYS[5]+"") && days.contains(LEVEL_DAYS[6]+""))
            result = RESULT_WEEKEND;
        else {

            for (String s : dayStringArray) {
                Log.d("FINISH", "N: " + s);
                Log.d("FINISH", "DAY: " + DAYS_NAME[Integer.parseInt(s)-1]);
                result += DAYS_NAME[Integer.parseInt(s) - 1].substring(0,2) + ", ";
            }
            result = result.substring(0, result.length() - 2);
        }

        Log.d("FINISH", result);

        return result;
    }
}
