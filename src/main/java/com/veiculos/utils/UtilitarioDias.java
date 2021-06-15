package com.veiculos.utils;

public final class UtilitarioDias {

    public static final int SEG = 1;
    public static final int TER = 2;
    public static final int QUA = 3;
    public static final int QUI = 4;
    public static final int SEX = 5;

    public static final String SEG_EXT = "segunda-feira";
    public static final String TER_EXT = "terca-feira";
    public static final String QUA_EXT = "quarta-feira";
    public static final String QUI_EXT = "quinta-feira";
    public static final String SEX_EXT = "sexta-feira";

    public static int getDiaRodizio(String substr_ano) {

        int diaRodizio = 0;

        switch (Integer.parseInt(substr_ano)) {
            case 0:
            case 1:
                diaRodizio = SEG;
                break;
            case 2:
            case 3:
                diaRodizio = TER;
                break;
            case 4:
            case 5:
                diaRodizio = QUA;
                break;
            case 6:
            case 7:
                diaRodizio = QUI;
                break;
            case 8:
            case 9:
                diaRodizio = SEX;
                break;
        }

        return diaRodizio;
    }

    public static String getDiaExtenso(int dia) {

        String dia_extenso = "";

        switch (dia) {
            case SEG:
                dia_extenso = SEG_EXT;
                break;
            case TER:
                dia_extenso = TER_EXT;
                break;
            case QUA:
                dia_extenso = QUA_EXT;
                break;
            case QUI:
                dia_extenso = QUI_EXT;
                break;
            case SEX:
                dia_extenso = SEX_EXT;
                break;
        }
        return dia_extenso;
    }

}
