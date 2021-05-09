package com.tcheptang.alten.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chambre implements Serializable {

    /**
     * id, identifiant pour les chambres
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chambre")
    private Long id;

    @Column(name = "nom_chambre")
    @NotNull
    private String nomChambre;

    @JsonIgnore
    @OneToMany(mappedBy = "chambre", fetch = FetchType.LAZY)
    private List<Reservation> reservationList = new ArrayList<>();
}
