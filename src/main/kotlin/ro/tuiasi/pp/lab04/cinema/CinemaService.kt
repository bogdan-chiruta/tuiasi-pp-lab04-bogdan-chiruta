package ro.tuiasi.pp.lab04.cinema

/**
 * Serviciu cu logica business a aplicației cinema.
 *
 * Respectă principiul responsabilității unice (SRP): conține doar
 * logica de rezervare, anulare și interogare, delegând persistența
 * către [CinemaRepository].
 *
 * @property repo Repository-ul folosit pentru persistarea datelor
 */
class CinemaService(private val repo: CinemaRepository) {

    /**
     * Rezervă un bilet pentru [movie] la scaunul [seat].
     *
     * @param movie Filmul pentru care se face rezervarea
     * @param seat Scaunul dorit
     * @param price Prețul biletului
     * @return Biletul creat și salvat
     * @throws IllegalStateException dacă scaunul nu este disponibil
     */
    fun bookTicket(movie: Movie, seat: Seat, price: Double): Ticket {
        if (!seat.isAvailable)
            throw IllegalStateException("Scaunul ${seat.row}/${seat.number} nu este disponibil")

        val occupiedSeat = seat.copy(isAvailable = false)
        repo.updateSeat(occupiedSeat)

        val ticket = Ticket(movie, occupiedSeat, price)
        repo.saveTicket(ticket)

        return ticket
    }

    /**
     * Anulează un bilet și eliberează scaunul.
     *
     * @param ticket Biletul de anulat
     */
    fun cancelTicket(ticket: Ticket) {
        repo.deleteTicket(ticket)
        repo.updateSeat(ticket.seat.copy(isAvailable = true))
    }

    /**
     * Returnează lista scaunelor disponibile pentru [movie].
     *
     * @param movie Filmul pentru care se caută scaune
     * @return Lista scaunelor cu `isAvailable = true`
     */
    fun availableSeats(movie: Movie): List<Seat> {
        return repo.allSeats(movie).filter { it.isAvailable }
    }
}
