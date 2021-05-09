package com.tcheptang.alten.util;

import com.tcheptang.alten.model.Chambre;
import com.tcheptang.alten.model.Client;
import com.tcheptang.alten.model.Reservation;
import com.tcheptang.alten.repository.ChambreRepository;
import com.tcheptang.alten.services.ChambreService;
import com.tcheptang.alten.services.ClientService;
import com.tcheptang.alten.services.ReservationService;
import org.apache.catalina.session.JDBCStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Initialize implements InitializingBean {

    @Autowired
    private ChambreService chambreService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Chambre chambre1 = Chambre.builder()
                .id(1L)
                .nomChambre("Chambre 1")
                .build();
        this.chambreService.save(chambre1);

        Client client1 = Client.builder()
                .id(1L)
                .nomClient("Client 1")
                .prenomClient("Client 1")
                .build();
        this.clientService.save(client1);

        Client client2 = Client.builder()
                .id(1L)
                .nomClient("Client 2")
                .prenomClient("Client 2")
                .build();
        this.clientService.save(client2);

        LocalDate dateDebut = LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(4);
        LocalDate dateFin = LocalDate.parse(LocalDate.now().format(Constante.DATE_FORMATEUR), Constante.DATE_FORMATEUR).plusDays(6);

        Reservation reservation1 = Reservation.builder()
                .chambre(chambre1)
                .client(client1)
                .dateDebut(dateDebut)
                .dateFin(dateFin).id(1L)
                .build();
        this.reservationService.creerReservation(reservation1);
    }
}
