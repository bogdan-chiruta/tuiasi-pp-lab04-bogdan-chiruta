package ro.tuiasi.pp.lab04.notes

/**
 * Meniu CLI interactiv pentru gestionarea notițelor.
 *
 * Afișează un meniu text și procesează comenzile utilizatorului,
 * delegând operațiile business către [NoteManager].
 *
 * @property manager Manager-ul cu logica business
 */
class CliMenu(private val manager: NoteManager) {

    /**
     * Pornește bucla principală a meniului CLI.
     *
     * Meniu disponibil:
     * - 1: Listare notițe
     * - 2: Încărcare notiță după ID
     * - 3: Creare notiță nouă
     * - 4: Ștergere notiță
     * - 0: Ieșire
     */
    fun run() {
        while (true) {
            println("\n=== Manager Notițe ===")
            println("1. Listare notițe")
            println("2. Încărcare notiță")
            println("3. Creare notiță")
            println("4. Ștergere notiță")
            println("0. Ieșire")
            print("Alegeți opțiunea: ")

            when (readLine()?.trim()) {
                "1" -> listeazaNotite()
                "2" -> incarcaNota()
                "3" -> creeazaNota()
                "4" -> stergeNota()
                "0" -> { println("La revedere!"); break }
                else -> println("Opțiune invalidă. Încercați din nou.")
            }
        }
    }

    private fun listeazaNotite() {
        val notite = manager.listNotes()
        if (notite.isEmpty()) {
            println("Nu există notițe salvate.")
            return
        }
        println("\n--- Lista notițelor ---")
        notite.forEach { nota ->
            println("ID: ${nota.id} | Autor: ${nota.author} | Data: ${nota.createdAt}")
        }
    }

    private fun incarcaNota() {
        print("Introduceți ID-ul notiței: ")
        val id = readLine()?.trim() ?: return
        try {
            val nota = manager.loadNote(id)
            println("\n--- Conținut notiță ---")
            println("Autor: ${nota.author}")
            println("Data: ${nota.createdAt}")
            println("Conținut:\n${nota.content}")
        } catch (e: NoSuchElementException) {
            println("Eroare: ${e.message}")
        }
    }

    private fun creeazaNota() {
        print("Introduceți autorul: ")
        val autor = readLine()?.trim() ?: return
        print("Introduceți conținutul: ")
        val continut = readLine()?.trim() ?: return
        val nota = manager.createNote(autor, continut)
        println("Notiță creată cu succes! ID: ${nota.id}")
    }

    private fun stergeNota() {
        print("Introduceți ID-ul notiței de șters: ")
        val id = readLine()?.trim() ?: return
        try {
            manager.deleteNote(id)
            println("Notița cu ID=$id a fost ștearsă cu succes.")
        } catch (e: Exception) {
            println("Eroare la ștergere: ${e.message}")
        }
    }
}
