package com.tcheptang.alten.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation implements Serializable {

    /**
     * id, identifiant pour les réservations
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    @NotNull
    private Long id;

    @Column(name = "date_debut")
    @NotNull
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    @NotNull
    private LocalDate dateFin;

    @ManyToOne(optional = false)
    @JoinColumn(name="client_id")
    @NotNull
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name="chambre_id")
    private Chambre chambre;
}
