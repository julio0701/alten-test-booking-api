package com.tcheptang.alten.tests;

import com.tcheptang.alten.model.Chambre;
import com.tcheptang.alten.model.Client;
import com.tcheptang.alten.model.Reservation;
import com.tcheptang.alten.services.ChambreService;
import com.tcheptang.alten.services.ClientService;
import com.tcheptang.alten.services.ReservationService;
import com.tcheptang.alten.util.Constante;
import com.tcheptang.alten.util.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class ReservationServiceTest {

    static ReservationService reservationService;
    Client client1 = Client.builder().id(1L).nomClient("Client Test 1").prenomClient("Client test 1").build();
    Chambre chambre1 = Chambre.builder().id(1L).nomChambre("Chambre Test 1").build();

    @Autowired
    public ReservationServiceTest(ReservationService reservationService, ClientService clientService, ChambreService chambreService){
        this.reservationService = reservationService;
    }

    @Test
    public void doitLeverUneExeptionCarLaDateDeDebutDoitEtreAuPlusTotLeLendemain(){
        Reservation resevationTest = Reservation.builder()
                .client(client1)
                .chambre(chambre1)
                .dateDebut(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR))
                .dateFin(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(2))
                .build();
        try{
            reservationService.creerReservation(resevationTest);
            fail();
        }catch (Exception e){
            assertEquals(StatusMessage.DATE_DEBUT_RESERVATION_TROP_TOT, e.getMessage());
        }
    }

    @Test
    public void doitLeverUneExeptionCarLeSejourNePeutDepasser3jours(){
        Reservation resevationTest = Reservation.builder()
                .client(client1)
                .chambre(chambre1)
                .dateDebut(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(1))
                .dateFin(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(4))
                .build();
        try{
            reservationService.creerReservation(resevationTest);
            fail();
        }catch (Exception e){
            assertEquals(StatusMessage.DUREE_SEJOUR_TROP_LONGUE, e.getMessage());
        }
    }

    @Test
    public void doitLeverUneExeptionCarLaReservationNePeutEtreFaitePlusDe30JoursAvant(){
        Reservation resevationTest = Reservation.builder()
                .client(client1)
                .chambre(chambre1)
                .dateDebut(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(31))
                .dateFin(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(32))
                .build();
        try{
            reservationService.creerReservation(resevationTest);
            fail();
        }catch (Exception e){
            assertEquals(StatusMessage.DATE_DEBUT_RESERVATION_TROP_TARD, e.getMessage());
        }
    }

    @Test
    public void doitLeverUneExeptionCarLaChambreEstDejaReserveeSurLaPeriode(){
        Reservation resevationPremiere = Reservation.builder()
                .client(client1)
                .chambre(chambre1)
                .dateDebut(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(10))
                .dateFin(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(12))
                .build();

        Reservation resevationTest = Reservation.builder()
                .client(client1)
                .chambre(chambre1)
                .dateDebut(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(11))
                .dateFin(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(13))
                .build();
        try{
            reservationService.creerReservation(resevationPremiere);
            reservationService.creerReservation(resevationTest);
            fail();
        }catch (Exception e){
            assertEquals(StatusMessage.CHAMBRE_DEJA_RESERVEE, e.getMessage());
        }
    }

    @Test
    public void doitLeverCreerUneReservationAvecUnIdentifiantSuperieurAZero(){
        Reservation resevationTest = Reservation.builder()
                .client(client1)
                .chambre(chambre1)
                .dateDebut(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(1))
                .dateFin(LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(3))
                .build();
        try{
            resevationTest = reservationService.creerReservation(resevationTest);
            assertTrue(resevationTest.getId() > 0);
        }catch (Exception e){
            fail();
        }
    }

    @AfterAll
    public static void afterAllTests(){
        try{
            log.debug("Purge après test");
            reservationService.supprimerTout();
        }catch (Exception e){
            log.debug("Erreur lors de la purge après test");
        }
    }
}