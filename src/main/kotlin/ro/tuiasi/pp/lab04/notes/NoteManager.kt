package ro.tuiasi.pp.lab04.notes

import java.time.LocalDateTime
import java.util.UUID

/**
 * Manager business pentru operații cu notițe.
 *
 * Conține logica aplicației și delegă persistarea către [NoteRepository].
 * Respectă principiul responsabilității unice (SRP).
 *
 * @property repo Repository-ul folosit pentru persistare
 */
class NoteManager(private val repo: NoteRepository) {

    /**
     * Creează o notiță nouă cu ID generat automat și data curentă.
     *
     * @param author Autorul notiței
     * @param content Conținutul notiței
     * @return Notița creată și salvată
     */
    fun createNote(author: String, content: String): Note {
        val id = UUID.randomUUID().toString()
        val data = LocalDateTime.now()
        val nota = Note(id, author, data, content)
        repo.save(nota)
        return nota
    }

    /**
     * Încarcă o notiță după ID.
     *
     * @param id ID-ul notiței
     * @return Notița găsită
     * @throws NoSuchElementException dacă notița nu există
     */
    fun loadNote(id: String): Note {
        return repo.findById(id) ?: throw NoSuchElementException("Notița cu id=$id nu a fost găsită")
    }

    /**
     * Returnează lista tuturor notițelor, sortate descrescător după data creării.
     *
     * @return Lista notițelor sortate
     */
    fun listNotes(): List<Note> {
        return repo.findAll().sortedByDescending { it.createdAt }
    }

    /**
     * Șterge notița cu [id].
     *
     * @param id ID-ul notiței de șters
     */
    fun deleteNote(id: String) {
        repo.delete(id)
    }
}
