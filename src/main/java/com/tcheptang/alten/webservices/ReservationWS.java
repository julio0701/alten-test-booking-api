package com.tcheptang.alten.webservices;

import com.tcheptang.alten.dto.ReservationDTO;
import com.tcheptang.alten.dto.ReservationInDTO;
import com.tcheptang.alten.model.Chambre;
import com.tcheptang.alten.model.Client;
import com.tcheptang.alten.model.Reservation;
import com.tcheptang.alten.services.ChambreService;
import com.tcheptang.alten.services.ClientService;
import com.tcheptang.alten.services.ReservationService;
import com.tcheptang.alten.util.Constante;
import com.tcheptang.alten.util.StatusMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.tcheptang.alten.ApiRegistration.PREFIX_WEB_SERVICE;
import static com.tcheptang.alten.ApiRegistration.URL_SERVICE_RESERVATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequestMapping(value = PREFIX_WEB_SERVICE + URL_SERVICE_RESERVATION)
@RestController
@Slf4j
public class ReservationWS {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ChambreService chambreService;

    @ApiOperation(value = "Créer une réservation",
            notes = "Création d'une réservation")
    @PostMapping(value = "/creer", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity creerReservation(@RequestBody ReservationInDTO reservationDto) throws Exception {
        try {
            Optional<Client> client = this.clientService.findById(reservationDto.getIdClient());
            Optional<Chambre> chambre = this.chambreService.findById(reservationDto.getIdChambre());
            if (client.isPresent() && chambre.isPresent()) {
                Reservation reservation = Reservation.builder()
                        .client(client.get())
                        .chambre(chambre.get())
                        .dateDebut(LocalDate.parse(reservationDto.getDateDebut(), Constante.DATE_FORMATEUR))
                        .dateFin(LocalDate.parse(reservationDto.getDateFin(), Constante.DATE_FORMATEUR))
                        .build();
                reservation = this.reservationService.creerReservation(reservation);
                return reservation != null
                        ? ResponseEntity.status(HttpStatus.OK).body(reservation)
                        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.ERREUR_CREATION_RESERVATION);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.CHAMBRE_CLIENT_NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/modifier", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity modifierReservation(@RequestBody ReservationDTO reservationDto) throws Exception {
        try {
            Reservation nouvelleReservation = Reservation.builder()
                    .id(reservationDto.getId())
                    .dateDebut(LocalDate.parse(reservationDto.getDateDebut(), Constante.DATE_FORMATEUR))
                    .dateFin(LocalDate.parse(reservationDto.getDateFin(), Constante.DATE_FORMATEUR))
                    .build();
                Reservation reservation = this.reservationService.modifierReservation(nouvelleReservation);
                return reservation != null
                        ? ResponseEntity.status(HttpStatus.OK).body(reservation)
                        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.ERREUR_CREATION_RESERVATION);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/supprimer", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity supprimerReservation(
            @ApiParam(name =  "id",
            type = "Integer",
            value = "identifiant de la chambre à supprimer",
            example = "2",
            required = true)@RequestParam(value = "id",  required = true) Long id) throws Exception {
        try {
            return this.reservationService.supprimerReservation(id)
                    ? ResponseEntity.status(HttpStatus.OK).body(StatusMessage.SUPPRESSION_RESERVATION_REUSSIE)
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.ERREUR_SUPPRESSION_RESERVATION);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/all",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getReservations() throws Exception {
        try{
            List<Reservation> listReservation = this.reservationService.getReservationList();
            return CollectionUtils.isEmpty(listReservation)
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusMessage.AUCUNE_RESERVATION_ENREGISTREE)
                    : ResponseEntity.status(HttpStatus.OK).body(listReservation);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.ERROR_SERVER_MESSAGE);
        }
    }
}
