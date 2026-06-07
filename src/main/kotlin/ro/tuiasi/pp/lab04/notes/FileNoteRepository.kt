package ro.tuiasi.pp.lab04.notes

import java.nio.file.Path
import java.time.LocalDateTime

/**
 * Implementare [NoteRepository] cu persistare pe disk.
 *
 * Fiecare notiță este stocată ca un fișier text individual cu numele `{id}.txt`
 * în directorul specificat.
 *
 * Formatul fișierului:
 * ```
 * AUTHOR: <autor>
 * CREATED_AT: <data ISO-8601>
 * CONTENT:
 * <conținut multiliniar>
 * ```
 *
 * @property dir Directorul în care se salvează fișierele de notițe
 */
class FileNoteRepository(private val dir: Path) : NoteRepository {

    /**
     * Salvează notița în fișierul `{id}.txt` din [dir].
     *
     * Dacă directorul nu există, îl creează automat.
     */
    override fun save(note: Note) {
        dir.toFile().mkdirs()
        val cale = dir.resolve("${note.id}.txt")
        val continut = "AUTHOR: ${note.author}\n" +
                "CREATED_AT: ${note.createdAt}\n" +
                "CONTENT:\n" +
                note.content
        cale.toFile().writeText(continut)
    }

    /**
     * Încarcă notița cu [id] din fișierul `{id}.txt`.
     *
     * @return Notița deserializată, sau `null` dacă fișierul nu există
     */
    override fun findById(id: String): Note? {
        val cale = dir.resolve("$id.txt")
        if (!cale.toFile().exists()) return null

        val linii = cale.toFile().readLines()
        val autor = linii[0].removePrefix("AUTHOR: ")
        val data = LocalDateTime.parse(linii[1].removePrefix("CREATED_AT: "))
        val continut = linii.drop(3).joinToString("\n")

        return Note(id, autor, data, continut)
    }

    /**
     * Returnează toate notițele din [dir], citind toate fișierele `*.txt`.
     */
    override fun findAll(): List<Note> {
        return dir.toFile()
            .listFiles { f -> f.extension == "txt" }
            ?.mapNotNull { f -> findById(f.nameWithoutExtension) }
            ?: emptyList()
    }

    /**
     * Șterge fișierul `{id}.txt` din [dir].
     */
    override fun delete(id: String) {
        dir.resolve("$id.txt").toFile().delete()
    }
}
