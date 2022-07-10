package cinema.controller;

import cinema.exceptionhandling.InvalidTokenException;
import cinema.exceptionhandling.NumberOutOfBoundsException;
import cinema.exceptionhandling.SeatUnavailableException;
import cinema.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class Controller {

    @Autowired
    private CinemaRoom cinemaRoom;

    @GetMapping("/seats")
    SeatsResponse getSeats() {

        int i = 0;

        SeatsResponse seatsResponse = new SeatsResponse(cinemaRoom.getTotal_rows(), cinemaRoom.getTotal_columns(), new ArrayList<>());

        //build ArrayList of available seats for return
        for (Seat s : cinemaRoom.getSeatList()) {

            if (cinemaRoom.getAvailability().get(i)) {
                seatsResponse.getAvailable_seats().add(s);
            }

            i++;
        }

        return seatsResponse;
    }

    @PostMapping("/purchase")
    Ticket purchaseTicket(@RequestBody Seat seat) throws NumberOutOfBoundsException, SeatUnavailableException {

        //check for valid row & column
        if (seat.getRow() > 9 || seat.getRow() < 1 || seat.getColumn() > 9 || seat.getColumn() < 1) {
            throw new NumberOutOfBoundsException("The number of a row or a column is out of bounds!");
        }

        //String index of row + column concatenation
        String index = String.valueOf(seat.getRow()) + (seat.getColumn());

        //check for availability of seat
        if (!cinemaRoom.getAvailability().get(cinemaRoom.getIndexMap().get(index))) {
            throw new SeatUnavailableException("The ticket has been already purchased!");
        }

        Ticket ticket = new Ticket(UUID.randomUUID(), cinemaRoom.getSeatList().get(cinemaRoom.getIndexMap().get(index)));
        //map token to tokenIndex
        cinemaRoom.getTokenIndexMap().put(ticket.getToken(), cinemaRoom.getIndexMap().get(index));
        //add ticket price to statistics current_income
        cinemaRoom.getStatistics().setCurrent_income(
                cinemaRoom.getStatistics().getCurrent_income() + ticket.getTicket().getPrice()
        );
        //set ticket as purchased
        cinemaRoom.getAvailability().set(cinemaRoom.getIndexMap().get(index), false);
        //increment statistics number_of_purchased_tickets
        cinemaRoom.getStatistics().setNumber_of_purchased_tickets(
                cinemaRoom.getStatistics().getNumber_of_purchased_tickets() + 1
        );
        //adjust statistics number_of_available_seats
        cinemaRoom.getStatistics().setNumber_of_available_seats(
                cinemaRoom.getStatistics().getNumber_of_available_seats() - 1
        );

    return ticket;
    }

    @PostMapping("/return")
    ReturnTicket returnTicket(@RequestBody Ticket ticket) throws InvalidTokenException {

        //check tokenIndexMap for token key
        if(!cinemaRoom.getTokenIndexMap().containsKey(ticket.getToken())) {
            throw new InvalidTokenException("Wrong token!");
        }

        //set ticket as available
        cinemaRoom.getAvailability().set(cinemaRoom.getTokenIndexMap().get(ticket.getToken()), true);
        //adjust statistics current_income for refund
        cinemaRoom.getStatistics().setCurrent_income(
                cinemaRoom.getStatistics().getCurrent_income() -
                cinemaRoom.getSeatList().get(cinemaRoom.getTokenIndexMap().get(ticket.getToken())).getPrice()
        );
        //adjust statistics number_of_purchased_tickets
        cinemaRoom.getStatistics().setNumber_of_purchased_tickets(
                cinemaRoom.getStatistics().getNumber_of_purchased_tickets() - 1
        );
        //adjust statistics number_of_available_seats
        cinemaRoom.getStatistics().setNumber_of_available_seats(
                cinemaRoom.getStatistics().getNumber_of_available_seats() + 1
        );

        return new ReturnTicket(cinemaRoom.getSeatList().get(cinemaRoom.getTokenIndexMap().get(ticket.getToken())));
    }

    @PostMapping("/stats")
    Statistics getStats(@RequestParam(required = false) String password) throws AuthenticationException {

        //check for null param and password value
        if(password == null || !password.equals("super_secret")) {
            throw new AuthenticationException("The password is wrong!");
        }

        return cinemaRoom.getStatistics();
    }
}
