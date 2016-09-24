package com.example.androiddam.proyectofinalandroid.model;

import android.util.Log;

/**
 * Created by Juan on 24/09/2016.
 */

public final class Studies {
    private static final int LEVEL_PRIMARIA = 0;
    private static final int LEVEL_ESO = 1;
    private static final int LEVEL_CICLO_MEDIO = 2;
    private static final int LEVEL_BACHILLERATO = 3;
    private static final int LEVEL_CICLO_SUPERIOR = 4;
    private static final int LEVEL_UNIVERSITARIO = 5;

    private static final String STUDIES_PRIMARIA = "Primaria";
    private static final String STUDIES_ESO = "E.S.O";
    private static final String STUDIES_CICLO_MEDIO = "Ciclo de grado medio";
    private static final String STUDIES_BACHILLERATO = "Bachillerato";
    private static final String STUDIES_CICLO_SUPERIOR = "Ciclo de grado superior";
    private static final String STUDIES_UNIVERSITARIO = "Universitario";

    public static String getTextStudies(int level) {
        String study = "";
        switch (level) {
            case LEVEL_PRIMARIA:
                study = STUDIES_PRIMARIA;
                break;
            case LEVEL_ESO:
                study = STUDIES_ESO;
                break;
            case LEVEL_CICLO_MEDIO:
                study = STUDIES_CICLO_MEDIO;
                break;
            case LEVEL_BACHILLERATO:
                study = STUDIES_BACHILLERATO;
                break;
            case LEVEL_CICLO_SUPERIOR:
                study = STUDIES_CICLO_SUPERIOR;
                break;
            case LEVEL_UNIVERSITARIO:
                study = STUDIES_UNIVERSITARIO;
                break;
        }
        Log.d("FINISH: ", level + ": " + study);
        return study;
    }
}
