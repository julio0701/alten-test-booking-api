package com.tcheptang.alten.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReservationInDTO {

    @NotNull
    private Long idChambre;

    @NotNull
    private Long idClient;

    @NotNull
    private String dateDebut;

    @NotNull
    private String dateFin;
}
