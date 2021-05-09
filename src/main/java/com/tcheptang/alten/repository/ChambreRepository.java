package com.tcheptang.alten.repository;

import com.tcheptang.alten.model.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    /**
     * retourne une chambre à partir de son id
     * @param id l'id de la chambre
     * @return la chambre correspondante
     */
    Optional<Chambre> findById(Long id);

    Chambre save(Chambre chambre);

    @Query("select c from Chambre c where c.id not in :idsChambreOccupees")
    Set<Chambre> findChambreDisponiblePeriode(Set<Long> idsChambreOccupees);
}
