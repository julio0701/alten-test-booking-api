package com.tcheptang.alten.services.impl;

import com.tcheptang.alten.model.Reservation;
import com.tcheptang.alten.repository.ReservationRepository;
import com.tcheptang.alten.services.ReservationService;
import com.tcheptang.alten.util.Constante;
import com.tcheptang.alten.util.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Override
    public boolean checkConformiteReservation(Long chambreId, LocalDate dateDebut, LocalDate dateFin) throws Exception{
        // On vérifie que la date de fin est après la date de début
        if(dateDebut.isAfter(dateFin)){
            throw new Exception(StatusMessage.DATE_DEBUT_APRES_DATE_FIN);
        }

        // On vérifie que la réservation commence au plus tôt le lendemain du jour où on a fait la réservation
        LocalDate today = LocalDate.now();
        if(!dateDebut.isAfter(today)){
            throw new Exception(StatusMessage.DATE_DEBUT_RESERVATION_TROP_TOT);
        }

        // On vérifie que la réservation commence dans un délai de 30 jours par rapport à la date où est faite la réservation
        if(dateDebut.isAfter(today.plusDays(Constante.DELAI_MAX_RESERVATION))){
            throw new Exception(StatusMessage.DATE_DEBUT_RESERVATION_TROP_TARD);
        }

        // On vérifie que le séjour ne fait pas plus de 3 jours
        if(dateDebut.until(dateFin, ChronoUnit.DAYS) > Constante.DUREE_MAX_RESERVATION - 1){
            throw new Exception(StatusMessage.DUREE_SEJOUR_TROP_LONGUE);
        }

        // On vérifie que la chambre n'est pas occupée
        if(this.checkReservationChambre(chambreId,dateDebut,dateFin)){
            throw new Exception(StatusMessage.CHAMBRE_DEJA_RESERVEE);
        }

        return true;
    }

    @Override
    public boolean checkReservationChambre(Long chambreId, LocalDate dateDebut, LocalDate dateFin) {
        Set<Long> idsChambreReservees = this.reservationRepository.getIdChambreReserveesPeriode(dateDebut, dateFin);
        return CollectionUtils.isEmpty(idsChambreReservees) ? false : idsChambreReservees.contains(chambreId);
    }

    @Override
    public boolean checkReservationChambreForUpdate(Long chambreId, LocalDate acienneDateDebut, LocalDate aciennedateFin, LocalDate nouvelleDateDebut, LocalDate nouvelledateFin) throws Exception{
        if(nouvelleDateDebut.isAfter(nouvelledateFin)){
            throw new Exception(StatusMessage.DATE_DEBUT_APRES_DATE_FIN);
        }

        // cas 1: le nouvelle intervalle est inclus dans l'ancien
        if(nouvelleDateDebut.isAfter(acienneDateDebut) && nouvelledateFin.isBefore(aciennedateFin)){
            return true;
        }

        // cas 2: la nouvelle date de début est avant l'ancienne date de début et la nouvelle date de fin est dans l'ancien intervalle
        if(nouvelleDateDebut.isBefore(acienneDateDebut) && nouvelledateFin.isAfter(acienneDateDebut) && nouvelledateFin.isBefore(aciennedateFin)){
            return checkConformiteReservation(chambreId, nouvelleDateDebut, acienneDateDebut);
        }

        // cas 3: la nouvelle date de début est dans l'ancien intervalle et la nouvelle date de fin est après l'ancienne date de fin
        if(nouvelleDateDebut.isAfter(acienneDateDebut) && nouvelleDateDebut.isBefore(aciennedateFin) && nouvelledateFin.isAfter(aciennedateFin)){
            return checkConformiteReservation(chambreId, aciennedateFin, nouvelledateFin);
        }

        // cas 4: le nouvel intervalle est à droite de l'ancien intervalle
        if(nouvelleDateDebut.isAfter(aciennedateFin) && nouvelledateFin.isAfter(aciennedateFin)){
            return checkConformiteReservation(chambreId, nouvelleDateDebut, nouvelledateFin);
        }

        // cas 5: le nouvel intervalle est à gauche de l'ancien intervalle
        if(nouvelleDateDebut.isBefore(acienneDateDebut) && nouvelledateFin.isBefore(acienneDateDebut)){
            return checkConformiteReservation(chambreId, nouvelleDateDebut, nouvelledateFin);
        }

        return true;
    }

    @Override
    public Set<Long> getIdChambreReserveesPeriode(LocalDate dateDebut, LocalDate dateFin) {
        return this.reservationRepository.getIdChambreReserveesPeriode(dateDebut, dateFin);
    }

    @Override
    @Transactional
    public Reservation creerReservation(Reservation reservation) throws Exception{
        Reservation createReservation = null;
        if(checkConformiteReservation(reservation.getChambre().getId(), reservation.getDateDebut(), reservation.getDateFin())){
            createReservation = reservationRepository.save(reservation);
        }
        return createReservation;
    }

    @Override
    @Transactional
    public Reservation modifierReservation(Reservation nouvelleReservation) throws Exception {
        Long idReservationAModifier = nouvelleReservation.getId();
        Optional<Reservation> reservationOpt = reservationRepository.findById(idReservationAModifier);
        Reservation modifiedReservation = null;
        if(reservationOpt.isPresent() && checkReservationChambreForUpdate(reservationOpt.get().getChambre().getId(), reservationOpt.get().getDateDebut(), reservationOpt.get().getDateFin(), nouvelleReservation.getDateDebut(), nouvelleReservation.getDateFin())){
            modifiedReservation = reservationOpt.get();
            modifiedReservation.setDateDebut(nouvelleReservation.getDateDebut());
            modifiedReservation.setDateFin(nouvelleReservation.getDateFin());
            reservationRepository.save(modifiedReservation);
        }
        return modifiedReservation;
    }

    @Override
    @Transactional
    public boolean supprimerReservation(Long idReservation) throws Exception {
        Optional<Reservation> reservationOpt = this.reservationRepository.findById(idReservation);
        if(reservationOpt.isPresent()){
            this.reservationRepository.delete(reservationOpt.get());
        }else{
            throw new Exception(StatusMessage.RESERVATION_A_SUPPRIMER_INTROUVABLE);
        }
        return true;
    }

    @Override
    public List<Reservation> getReservationList() {
        return this.reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return this.reservationRepository.findById(id);
    }

    @Override
    public void supprimerTout() {
        this.reservationRepository.deleteAll();
    }
}
