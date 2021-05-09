package com.tcheptang.alten.services;

import com.tcheptang.alten.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReservationService {

    boolean checkConformiteReservation(Long chambreId, LocalDate dateDebut, LocalDate dateFin) throws Exception;
    boolean checkReservationChambre(Long chambreId, LocalDate dateDebut, LocalDate dateFin);
    boolean checkReservationChambreForUpdate(Long chambreId, LocalDate acienneDateDebut, LocalDate aciennedateFin, LocalDate nouvelleDateDebut, LocalDate nouvelledateFin) throws Exception;
    Set<Long> getIdChambreReserveesPeriode(LocalDate dateDebut, LocalDate dateFin);
    Reservation creerReservation(Reservation reservation1) throws Exception;
    Reservation modifierReservation(Reservation reservation1) throws Exception;
    boolean supprimerReservation(Long idReservation) throws Exception;
    List<Reservation> getReservationList();
    Optional<Reservation> findById(Long id);
    void supprimerTout();
}
