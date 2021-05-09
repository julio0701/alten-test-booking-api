package com.tcheptang.alten.repository;

import com.tcheptang.alten.model.Chambre;
import com.tcheptang.alten.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select c.id from Reservation r inner join r.chambre c where" +
            "    (r.dateDebut >= :date_debut and r.dateFin <= :date_fin) " +
            " or (r.dateDebut <= :date_debut and r.dateFin <= :date_fin and r.dateFin >= :date_debut) " +
            " or (r.dateDebut >= :date_debut and r.dateDebut <= :date_fin and r.dateFin >= :date_fin) " )
    Set<Long> getIdChambreReserveesPeriode(@Param("date_debut") LocalDate dateDebutReservation, @Param("date_fin") LocalDate dateFinReservation);
}
