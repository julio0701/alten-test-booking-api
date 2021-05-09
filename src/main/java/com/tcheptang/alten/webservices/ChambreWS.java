package com.tcheptang.alten.webservices;

import com.tcheptang.alten.model.Chambre;
import com.tcheptang.alten.services.ChambreService;
import com.tcheptang.alten.util.Constante;
import com.tcheptang.alten.util.StatusMessage;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.tcheptang.alten.ApiRegistration.PREFIX_WEB_SERVICE;
import static com.tcheptang.alten.ApiRegistration.URL_SERVICE_CHAMBRE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = PREFIX_WEB_SERVICE + URL_SERVICE_CHAMBRE)
@RestController
@Slf4j
public class ChambreWS {

    @Autowired
    private ChambreService chambreService;

    @GetMapping(value = "{id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getChambre(
            @ApiParam(name =  "id",
            type = "Integer",
            value = "identifiant d'une chambre",
            example = "1",
            required = true) @PathVariable(value = "id") Long id) throws Exception {
        try{
            Optional<Chambre> chambre = this.chambreService.findById(id);
            return chambre.isPresent()
                    ? ResponseEntity.status(HttpStatus.OK).body(chambre.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusMessage.CHAMBRE_NON_TROUVE_MESSAGE);

        }catch (Exception e){
            //logguer
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.ERROR_SERVER_MESSAGE);
        }
    }

    @GetMapping(value = "disponibles",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getChambresDisponibles(
            @ApiParam(name =  "date_debut",
            type = "String",
            value = "Date de début de la période",
            example = "07/05/2021",
            required = true) @RequestParam(value = "date_debut",  required = true) @DateTimeFormat(pattern = Constante.DATE_FORMAT) String date_debut,

            @ApiParam(name =  "date_fin",
            type = "String",
            value = "Date de fin de la période",
            example = "09/05/2021",
            required = true) @RequestParam(value = "date_fin",  required = true) @DateTimeFormat(pattern = Constante.DATE_FORMAT) String date_fin) throws Exception {

        try{
            LocalDate dateDebut = LocalDate.parse(date_debut, Constante.DATE_FORMATEUR);
            LocalDate dateFin = LocalDate.parse(date_fin, Constante.DATE_FORMATEUR);
            Set<Chambre> chambresDisponibles = this.chambreService.checkDisponibiliteHotel(dateDebut,dateFin);

            return CollectionUtils.isEmpty(chambresDisponibles)
                    ? ResponseEntity.status(HttpStatus.OK).body(StatusMessage.CHAMBRE_NON_TROUVE_HOTEL_MESSAGE)
                    : ResponseEntity.status(HttpStatus.OK).body(chambresDisponibles);

        }catch (Exception e){
            //logguer
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StatusMessage.ERROR_SERVER_MESSAGE);
        }
    }

}
