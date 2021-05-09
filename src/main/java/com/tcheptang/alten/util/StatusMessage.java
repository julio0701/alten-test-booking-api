package com.tcheptang.alten.util;

/**
 * Permet une meilleur gestion des messages remontés aux utilisateurs
 */
public final class StatusMessage {

    public static final String ERROR_SERVER_MESSAGE = "Erreur serveur";
    public static final String CHAMBRE_NON_TROUVE_MESSAGE = "Chambre non trouvée";
    public static final String CHAMBRE_NON_TROUVE_HOTEL_MESSAGE = "Aucune chambre disponible pour la période sélectionée";
    public static final String DATE_DEBUT_APRES_DATE_FIN = "La date de début de réservation ne peut être postérieure à la date de fin de réservation";
    public static final String DATE_DEBUT_RESERVATION_TROP_TOT = "La date de début de réservation doit être au plus tôt le lendemain";
    public static final String DATE_DEBUT_RESERVATION_TROP_TARD = "La date de début de réservation doit être au tard dans un délai de 30 jours";
    public static final String DUREE_SEJOUR_TROP_LONGUE = "Le séjour ne peut dépasser 3 jours";
    public static final String CHAMBRE_DEJA_RESERVEE = "La chambre est déja réservée pour cette période";
    public static final String AUCUNE_RESERVATION_ENREGISTREE = "Aucune réservation enregistrée";
    public static final String CHAMBRE_CLIENT_NOT_FOUND = "Client et/ou chambre inexistant(e) dans le système";
    public static final String ERREUR_CREATION_RESERVATION = "Problème lors de l'enregistrement";
    public static final String ERREUR_SUPPRESSION_RESERVATION = "Problème lors de la suppression de la réservation";
    public static final String SUPPRESSION_RESERVATION_REUSSIE = "Réservation supprimée avec succès";
    public static final String RESERVATION_A_SUPPRIMER_INTROUVABLE= "La réservation que vous tentez de supprimer est introuvable";


}
