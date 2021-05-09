package com.tcheptang.alten.util;

import java.time.format.DateTimeFormatter;

public class Constante {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT= "dd/MM/yyyy HH:mm";
    public static final String HEURE_DEBUT_JOURNEE = " 00:00";
    public static final String HEURE_FIN_JOURNEE= " 23:59";
    public static final DateTimeFormatter DATE_FORMATEUR = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final int DELAI_MAX_RESERVATION  = 30;
    public static final int DUREE_MAX_RESERVATION  = 3;
}
