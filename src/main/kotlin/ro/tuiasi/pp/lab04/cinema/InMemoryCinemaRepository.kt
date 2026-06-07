package ro.tuiasi.pp.lab04.cinema

/**
 * Implementare in-memory a [CinemaRepository].
 *
 * Stochează filmele, scaunele și biletele în liste din memorie.
 * Utilă pentru teste și prototipuri.
 */
class InMemoryCinemaRepository : CinemaRepository {

    // Stocarea datelor în memorie
    private val filme = mutableListOf<Movie>()
    private val scaune = mutableListOf<Seat>()
    private val bilete = mutableListOf<Ticket>()

    override fun addMovie(movie: Movie) {
        filme.add(movie)
    }

    override fun findMovie(id: Int): Movie? {
        return filme.find { it.id == id }
    }

    override fun addSeat(seat: Seat) {
        scaune.add(seat)
    }

    override fun findSeat(row: Int, number: Int): Seat? {
        return scaune.find { it.row == row && it.number == number }
    }

    override fun updateSeat(seat: Seat) {
        val index = scaune.indexOfFirst { it.row == seat.row && it.number == seat.number }
        if (index != -1) scaune.set(index, seat)
    }

    override fun saveTicket(ticket: Ticket) {
        bilete.add(ticket)
    }

    override fun findTicket(movie: Movie, seat: Seat): Ticket? {
        return bilete.find {
            it.movie.id == movie.id &&
                    it.seat.row == seat.row &&
                    it.seat.number == seat.number
        }
    }

    override fun deleteTicket(ticket: Ticket) {
        bilete.remove(ticket)
    }

    override fun allSeats(movie: Movie): List<Seat> {
        return scaune.toList()
    }
}
