package com.tcheptang.alten.services;

import com.tcheptang.alten.model.Chambre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface ChambreService {

    /**
     * retourne une chambre à partir de son id
     * @param id l'id de la chambre
     * @return la chambre correspondante
     */
    Optional<Chambre> findById(Long id);

    Chambre save(Chambre chambre);

    public Set<Chambre> checkDisponibiliteHotel(LocalDate dateDebut, LocalDate dateFin);

}
