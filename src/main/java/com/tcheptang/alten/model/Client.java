package com.tcheptang.alten.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client implements Serializable {

    /**
     * id, identifiant pour les clients
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    @Column(name = "nom_client")
    @NotNull
    private String nomClient;

    @Column(name = "premon_client")
    private String prenomClient;
}
