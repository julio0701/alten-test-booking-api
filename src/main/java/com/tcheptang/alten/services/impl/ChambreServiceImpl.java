package com.tcheptang.alten.services.impl;

import com.tcheptang.alten.model.Chambre;
import com.tcheptang.alten.repository.ChambreRepository;
import com.tcheptang.alten.services.ChambreService;
import com.tcheptang.alten.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class ChambreServiceImpl implements ChambreService {

    private final ChambreRepository chambreRepository;
    private final ReservationService reservationService;

    @Autowired
    public ChambreServiceImpl(ChambreRepository chambreRepository, ReservationService reservationService){
        this.chambreRepository = chambreRepository;
        this.reservationService = reservationService;
    }

    @Override
    public Optional<Chambre> findById(Long id) {
        return chambreRepository.findById(id);
    }

    @Override
    public Chambre save(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public Set<Chambre> checkDisponibiliteHotel(LocalDate dateDebut, LocalDate dateFin) {
        Set<Long> idsChambreReservees = this.reservationService.getIdChambreReserveesPeriode(dateDebut, dateFin);
        return this.chambreRepository.findChambreDisponiblePeriode(idsChambreReservees);
    }
}
